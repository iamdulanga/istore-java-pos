package Model;

import Database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AccountCreateModel {

    public static boolean createAccount(String username, String password, String acctype) {
        String query = "INSERT INTO accounts (username, password, acctype) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnector.connect(); PreparedStatement pst = con.prepareStatement(query)) {

            //run the username validation method
            if (isUsernameExists(con, username)) {
                JOptionPane.showMessageDialog(null, "Username Already Exists!\nPlease Use A Different One!");
            } else {
                
                //set the variables
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, acctype);

                int rowsAffected = pst.executeUpdate();
                return rowsAffected > 0;
            
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error: " + ex.getMessage(), ex);
        }
        return false;
    }

    //username validation method -> same name cannot be user twice
    private static boolean isUsernameExists(Connection con, String username) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM accounts WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(checkQuery)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
