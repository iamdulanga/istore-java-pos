package controller;

import model.Product;
import services.ProductService;
import view.ManagerView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for Manager operations.
 * Coordinates between ManagerView and ProductService.
 */
public class ManagerController {
    private final ManagerView view;
    private final ProductService productService;

    public ManagerController(ManagerView view) {
        this.view = view;
        this.productService = new ProductService();
    }

    /**
     * Loads all products from the database and displays them in the view.
     */
    public void loadProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            view.displayProducts(products);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while loading products: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Searches for products by keyword.
     */
    public void searchProducts(String keyword) {
        try {
            List<Product> products = productService.searchProducts(keyword);
            view.displayProducts(products);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while searching: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new product.
     */
    public void addProduct(int itemId, String name, String category, int quantity, double price) {
        try {
            Product product = new Product(itemId, name, category, quantity, price);
            boolean success = productService.addProduct(product);
            
            if (success) {
                JOptionPane.showMessageDialog(view, "Product Added Successfully!");
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(view, "Error! Try Again!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while adding the product: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates an existing product.
     */
    public void updateProduct(int newItemId, String name, String category, int quantity, double price, int oldItemId) {
        try {
            Product product = new Product(newItemId, name, category, quantity, price);
            boolean success = productService.updateProduct(product, oldItemId);
            
            if (success) {
                JOptionPane.showMessageDialog(view, "Product Updated Successfully!");
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(view, "Rows Aren't updated!\nPlease Check Inputs!");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while updating the product: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes a product.
     */
    public void deleteProduct(int itemId) {
        try {
            int confirm = JOptionPane.showConfirmDialog(view, 
                "Are you sure you want to delete this product?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = productService.deleteProduct(itemId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, "Product Deleted Successfully!");
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete product!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while deleting the product: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads a product by ID and populates the form fields.
     */
    public Product getProductById(int itemId) {
        try {
            return productService.getProductById(itemId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while retrieving product data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
