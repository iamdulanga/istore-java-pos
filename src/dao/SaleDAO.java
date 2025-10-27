package dao;

import model.Sale;
import model.SaleItem;
import utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Data Access Object for Sale entity.
 * Handles all database operations related to sales.
 */
public class SaleDAO {

    /**
     * Creates a new sale transaction with its items.
     * Returns the generated sale ID.
     */
    public int createSale(Sale sale, List<SaleItem> saleItems) throws SQLException {
        String insertSalesSQL = "INSERT INTO sales (total, payment, balance) VALUES (?, ?, ?)";
        String insertSaleItemsSQL = "INSERT INTO saleitems (salesId, itemId, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.connect()) {
            conn.setAutoCommit(false);
            
            try {
                int generatedSaleId = -1;
                
                // Insert sale
                try (PreparedStatement salesStmt = conn.prepareStatement(insertSalesSQL, Statement.RETURN_GENERATED_KEYS)) {
                    salesStmt.setDouble(1, sale.getTotal());
                    salesStmt.setDouble(2, sale.getPayment());
                    salesStmt.setDouble(3, sale.getBalance());
                    salesStmt.executeUpdate();
                    
                    try (ResultSet generatedKeys = salesStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedSaleId = generatedKeys.getInt(1);
                        }
                    }
                }
                
                // Insert sale items
                if (generatedSaleId != -1) {
                    try (PreparedStatement itemsStmt = conn.prepareStatement(insertSaleItemsSQL)) {
                        for (SaleItem item : saleItems) {
                            itemsStmt.setInt(1, generatedSaleId);
                            itemsStmt.setString(2, item.getItemId());
                            itemsStmt.setString(3, item.getQuantity());
                            itemsStmt.setString(4, item.getPrice());
                            itemsStmt.addBatch();
                        }
                        itemsStmt.executeBatch();
                    }
                }
                
                conn.commit();
                return generatedSaleId;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    /**
     * Retrieves a sale by its ID.
     */
    public Sale getSaleById(int salesId) throws SQLException {
        String query = "SELECT * FROM sales WHERE salesId = ?";
        
        try (Connection con = DatabaseConnector.connect();
             PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, salesId);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Sale(
                        rs.getInt("salesId"),
                        rs.getDouble("total"),
                        rs.getDouble("payment"),
                        rs.getDouble("balance")
                    );
                }
            }
        }
        return null;
    }
}
