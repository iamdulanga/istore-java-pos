package controller;

import model.Product;
import model.Sale;
import model.SaleItem;
import services.ProductService;
import services.SaleService;
import services.InvoiceService;
import view.CashierView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for Cashier operations.
 * Coordinates between CashierView and services.
 */
public class CashierController {
    private final CashierView view;
    private final ProductService productService;
    private final SaleService saleService;

    public CashierController(CashierView view) {
        this.view = view;
        this.productService = new ProductService();
        this.saleService = new SaleService();
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
     * Retrieves a product by ID.
     */
    public Product getProductById(int itemId) {
        try {
            return productService.getProductById(itemId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while retrieving product: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Processes a sale transaction.
     */
    public boolean processSale(double total, double payment, double balance, List<SaleItem> saleItems) {
        try {
            Sale sale = new Sale(0, total, payment, balance);
            int saleId = saleService.createSale(sale, saleItems);
            return saleId > 0;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while processing the sale: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Generates an invoice for the sale.
     */
    public void generateInvoice(JTable tblInv, JTextField txtTot, JTextField txtPay, JTextField txtBal) {
        try {
            InvoiceService.generateInvoice(tblInv, txtTot, txtPay, txtBal);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "An error occurred while generating invoice: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
