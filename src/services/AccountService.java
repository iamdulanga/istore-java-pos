package services;

import dao.AccountDAO;
import model.Account;

import java.sql.SQLException;

/**
 * Service class for Account-related business logic.
 * Acts as an intermediary between Controllers and DAOs.
 */
public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /**
     * Authenticates a user and returns their account type.
     */
    public String authenticateUser(String username, String password) throws SQLException {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return accountDAO.authenticateUser(username, password);
    }

    /**
     * Creates a new account with validation.
     */
    public boolean createAccount(String username, String password, String accountType) throws SQLException {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be empty");
        }

        // Check if username already exists
        if (accountDAO.isUsernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        return accountDAO.createAccount(username, password, accountType);
    }

    /**
     * Retrieves an account by username.
     */
    public Account getAccountByUsername(String username) throws SQLException {
        return accountDAO.getAccountByUsername(username);
    }
}
