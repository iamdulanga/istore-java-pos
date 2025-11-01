package services;

import dao.ProductDAO;
import dao.SaleDAO;
import model.Sale;
import model.SaleItem;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for Sale-related business logic.
 * Acts as an intermediary between Controllers and DAOs.
 */
public class SaleService {
    private final SaleDAO saleDAO;
    private final ProductDAO productDAO;

    public SaleService() {
        this.saleDAO = new SaleDAO();
        this.productDAO = new ProductDAO();
    }

    /**
     * Creates a new sale and updates product quantities.
     */
    public int createSale(Sale sale, List<SaleItem> saleItems) throws SQLException {
        // Validation
        if (sale.getTotal() < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        if (sale.getPayment() < 0) {
            throw new IllegalArgumentException("Payment cannot be negative");
        }
        if (saleItems == null || saleItems.isEmpty()) {
            throw new IllegalArgumentException("Sale must have at least one item");
        }

        // Create the sale
        int saleId = saleDAO.createSale(sale, saleItems);

        // Update product quantities
        for (SaleItem item : saleItems) {
            int itemId = Integer.parseInt(item.getItemId());
            int quantity = Integer.parseInt(item.getQuantity());
            productDAO.updateProductQuantity(itemId, quantity);
        }

        return saleId;
    }

    /**
     * Retrieves a sale by ID.
     */
    public Sale getSaleById(int salesId) throws SQLException {
        return saleDAO.getSaleById(salesId);
    }
}
