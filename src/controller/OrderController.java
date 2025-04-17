// Gère les commandes (ajout, suppression, modification)

package controller;

import model.Order;
import model.OrderManager;
import java.util.List;

public class OrderController {
    private OrderManager orderManager;

    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    // Ajouter une commande
    public void addOrder(Order order) {
        orderManager.addOrder(order);
    }

    // Supprimer une commande (Changement : String orderId)
    public void removeOrder(String orderId) {
        orderManager.removeOrder(orderId);
    }

    // Modifier une commande (Changement : String orderId)
    public void updateOrder(String orderId, Order updatedOrder) {
        orderManager.updateOrder(orderId, updatedOrder);
    }

    // Récupérer toutes les commandes
    public List<Order> getAllOrders() {
        return orderManager.getAllOrders();
    }
}

