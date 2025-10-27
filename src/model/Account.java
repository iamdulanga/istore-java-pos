package model;

/**
 * Domain model representing a User Account.
 * Pure POJO (Plain Old Java Object) with no business logic.
 */
public class Account {
    private int userId;
    private String username;
    private String password;
    private String accountType;

    public Account() {
    }

    public Account(int userId, String username, String password, String accountType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
