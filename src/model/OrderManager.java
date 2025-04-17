package model;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    // Ajouter une commande
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Supprimer une commande par son ID (Changement : String orderId)
    public void removeOrder(String orderId) {
        orders.removeIf(order -> order.getOrderId().equals(orderId));
    }

    // Modifier une commande existante
    public void updateOrder(String orderId, Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(orderId)) {
                orders.set(i, updatedOrder);
                return;
            }
        }
    }

    // Récupérer toutes les commandes
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
}
