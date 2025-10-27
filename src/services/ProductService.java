package services;

import dao.ProductDAO;
import model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for Product-related business logic.
 * Acts as an intermediary between Controllers and DAOs.
 */
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    /**
     * Retrieves all products.
     */
    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    /**
     * Searches for products by keyword.
     */
    public List<Product> searchProducts(String keyword) throws SQLException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }
        return productDAO.searchProducts(keyword);
    }

    /**
     * Retrieves a product by ID.
     */
    public Product getProductById(int itemId) throws SQLException {
        return productDAO.getProductById(itemId);
    }

    /**
     * Adds a new product with validation.
     */
    public boolean addProduct(Product product) throws SQLException {
        // Validation
        if (product.getItemId() <= 0) {
            throw new IllegalArgumentException("Item ID must be positive");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        // Check for duplicates
        if (productDAO.isItemIdExists(product.getItemId())) {
            throw new IllegalArgumentException("Item ID already exists");
        }
        if (productDAO.isProductNameExists(product.getName())) {
            throw new IllegalArgumentException("Product name already exists");
        }

        return productDAO.addProduct(product);
    }

    /**
     * Updates an existing product with validation.
     */
    public boolean updateProduct(Product product, int oldItemId) throws SQLException {
        // Validation
        if (product.getItemId() <= 0) {
            throw new IllegalArgumentException("Item ID must be positive");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        // Check if new ID conflicts with existing products (excluding the current one)
        if (product.getItemId() != oldItemId && productDAO.isItemIdExists(product.getItemId(), oldItemId)) {
            throw new IllegalArgumentException("Item ID already exists");
        }

        return productDAO.updateProduct(product, oldItemId);
    }

    /**
     * Deletes a product.
     */
    public boolean deleteProduct(int itemId) throws SQLException {
        return productDAO.deleteProduct(itemId);
    }

    /**
     * Updates product quantity after a sale.
     */
    public boolean updateProductQuantity(int itemId, int quantitySold) throws SQLException {
        return productDAO.updateProductQuantity(itemId, quantitySold);
    }
}
