package Model;

import Database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    
    public static String getUserType(String username, String password) {
        String query = "SELECT * FROM accounts WHERE username=? AND password=?";
        //connect with database controller for db connection
        try (Connection con = DatabaseConnector.connect(); PreparedStatement pst = con.prepareStatement(query)) {

            //set username & password
            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()){
                    return rs.getString("acctype");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Database Error : " + ex.getMessage(), ex);
        }
        return null;
    }
    
    
}
