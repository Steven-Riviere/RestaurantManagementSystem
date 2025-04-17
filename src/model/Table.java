// Représente une table dans le restaurant
package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableNumber;
    private List<Order> orders;
    private boolean isAvailable;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.orders = new ArrayList<>();
        this.isAvailable = true; // Par défaut, une nouvelle table est disponible
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void addOrder(Order order) {
        orders.add(order);
        this.isAvailable = false; // Une commande est en cours, donc la table est occupée
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        if (orders.isEmpty()) {
            this.isAvailable = true; // Plus de commandes, la table redevient disponible
        }
    }

    public void clearOrders() {
        orders.clear();
        this.isAvailable = true; // La table est libérée après le paiement
    }

    @Override
    public String toString() {
        return "Table " + tableNumber + " (Disponible: " + isAvailable + ")";
    }
}
