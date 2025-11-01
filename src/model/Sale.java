package model;

/**
 * Domain model representing a Sale transaction.
 * Pure POJO (Plain Old Java Object) with no business logic.
 */
public class Sale {
    private int salesId;
    private double total;
    private double payment;
    private double balance;

    public Sale() {
    }

    public Sale(int salesId, double total, double payment, double balance) {
        this.salesId = salesId;
        this.total = total;
        this.payment = payment;
        this.balance = balance;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
