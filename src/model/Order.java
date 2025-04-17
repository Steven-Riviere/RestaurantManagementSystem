package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private int tableNumber;
    private List<String> items;
    private String status;
    private LocalDateTime createdAt;
    private boolean paid;

    public Order(int tableNumber) {
        this.orderId = UUID.randomUUID().toString();
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.status = "En cours";
        this.createdAt = LocalDateTime.now();
        this.paid = false;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<String> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isPaid() {
        return paid;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void markAsPaid() {
        this.paid = true;
        this.status = "Payée";
    }
}
