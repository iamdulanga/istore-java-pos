package model;

/**
 * Domain model representing an item in a sale transaction.
 * Pure POJO (Plain Old Java Object) with no business logic.
 */
public class SaleItem {
    private int salesId;
    private String itemId;
    private String quantity;
    private String price;

    public SaleItem() {
    }

    public SaleItem(int salesId, String itemId, String quantity, String price) {
        this.salesId = salesId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
