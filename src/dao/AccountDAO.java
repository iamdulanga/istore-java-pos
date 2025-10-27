package dao;

import model.Account;
import utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for Account entity.
 * Handles all database operations related to user accounts.
 */
public class AccountDAO {

    /**
     * Authenticates a user and returns their account type.
     */
    public String authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM accounts WHERE username=? AND password=?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, username);
            pst.setString(2, password);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("acctype");
                }
            }
        }
        return null;
    }

    /**
     * Creates a new user account.
     */
    public boolean createAccount(String username, String password, String accountType) throws SQLException {
        String query = "INSERT INTO accounts (username, password, acctype) VALUES (?, ?, ?)";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, accountType);
            
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Checks if a username already exists in the database.
     */
    public boolean isUsernameExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM accounts WHERE username = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, username);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves an account by username.
     */
    public Account getAccountByUsername(String username) throws SQLException {
        String query = "SELECT * FROM accounts WHERE username = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, username);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                        rs.getInt("userid"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("acctype")
                    );
                }
            }
        }
        return null;
    }
}
