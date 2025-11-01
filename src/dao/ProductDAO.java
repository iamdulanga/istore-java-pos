package dao;

import model.Product;
import utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Product entity.
 * Handles all database operations related to products.
 */
public class ProductDAO {

    /**
     * Retrieves all products from the database.
     */
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("ItemId"),
                    rs.getString("Name"),
                    rs.getString("Category"),
                    rs.getInt("Qty"),
                    rs.getDouble("Price")
                );
                products.add(product);
            }
        }
        return products;
    }

    /**
     * Searches for products by keyword (searches in name, id, and category).
     */
    public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ? OR itemid LIKE ? OR category LIKE ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            String searchPattern = "%" + keyword + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                        rs.getInt("ItemId"),
                        rs.getString("Name"),
                        rs.getString("Category"),
                        rs.getInt("Qty"),
                        rs.getDouble("Price")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }

    /**
     * Retrieves a product by its ID.
     */
    public Product getProductById(int itemId) throws SQLException {
        String query = "SELECT * FROM products WHERE itemid = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, itemId);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("ItemId"),
                        rs.getString("Name"),
                        rs.getString("Category"),
                        rs.getInt("Qty"),
                        rs.getDouble("Price")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Adds a new product to the database.
     */
    public boolean addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products(itemid, name, category, qty, price) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, product.getItemId());
            pst.setString(2, product.getName());
            pst.setString(3, product.getCategory());
            pst.setInt(4, product.getQuantity());
            pst.setDouble(5, product.getPrice());
            
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing product in the database.
     */
    public boolean updateProduct(Product product, int oldItemId) throws SQLException {
        String query = "UPDATE products SET itemid=?, name=?, category=?, qty=?, price=? WHERE itemid=?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, product.getItemId());
            pst.setString(2, product.getName());
            pst.setString(3, product.getCategory());
            pst.setInt(4, product.getQuantity());
            pst.setDouble(5, product.getPrice());
            pst.setInt(6, oldItemId);
            
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a product from the database.
     */
    public boolean deleteProduct(int itemId) throws SQLException {
        String query = "DELETE FROM products WHERE itemid=?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, itemId);
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Checks if a product with the given ID exists.
     */
    public boolean isItemIdExists(int itemId) throws SQLException {
        String query = "SELECT COUNT(*) FROM products WHERE itemid = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, itemId);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a product with the given ID exists (excluding a specific ID).
     */
    public boolean isItemIdExists(int itemId, int excludeId) throws SQLException {
        String query = "SELECT COUNT(*) FROM products WHERE itemid = ? AND itemid != ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, itemId);
            pst.setInt(2, excludeId);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a product with the given name exists.
     */
    public boolean isProductNameExists(String name) throws SQLException {
        String query = "SELECT COUNT(*) FROM products WHERE name = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, name);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Updates product quantity after a sale.
     */
    public boolean updateProductQuantity(int itemId, int quantitySold) throws SQLException {
        String query = "UPDATE products SET qty = qty - ? WHERE itemid = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, quantitySold);
            pst.setInt(2, itemId);
            
            return pst.executeUpdate() > 0;
        }
    }
}
