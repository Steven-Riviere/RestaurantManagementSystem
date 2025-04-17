package controller;

import model.Menu;
import model.Order;
import model.OrderManager;
import model.Table;
import java.util.ArrayList;
import java.util.List;

public class RestaurantController {
    private final MenuController menuController;
    private final OrderController orderController;
    private final TableController tableController;
    private boolean isOpen;

    public RestaurantController() {
        this.menuController = new MenuController();
        this.orderController = new OrderController(new OrderManager());
        this.tableController = new TableController();
        this.isOpen = false; // Par défaut, le restaurant est fermé
    }

    // ➤ Ouvrir et fermer le restaurant
    public void openRestaurant() {
        if (!isOpen) {
            isOpen = true;
            System.out.println("Le restaurant est maintenant ouvert.");
        }
    }

    public void closeRestaurant() {
        if (isOpen) {
            isOpen = false;
            System.out.println("Le restaurant est maintenant fermé.");
        }
    }

    public boolean isRestaurantOpen() {
        return isOpen;
    }

    // ➤ Gestion des menus
    public void addMenu(String menuName) {
        menuController.addMenu(new Menu(menuName));
    }

    public boolean removeMenu(String menuName) {
        return menuController.removeMenuByName(menuName);
    }

    public boolean updateMenu(String oldMenuName, String newMenuName) {
        return menuController.updateMenuByName(oldMenuName, newMenuName);
    }

    public List<String> getAllMenuNames() {
        List<String> menuNames = new ArrayList<>();
        for (Menu menu : menuController.getAllMenus()) {
            menuNames.add(menu.getName());
        }
        return menuNames;
    }

    // ➤ Gestion des tables
    public void addTable(int number) {
        tableController.addTable(number);
    }

    public boolean removeTable(int number) {
        return tableController.removeTable(number);
    }

    public List<Table> getAllTables() {
        return tableController.getTables();
    }

    // ➤ Gestion des commandes
    public void addOrder(Order order) {
        if (isOpen) {
            orderController.addOrder(order);
        }
    }

    public void removeOrder(String orderId) {
        orderController.removeOrder(orderId);
    }

    public void updateOrder(String orderId, Order updatedOrder) {
        orderController.updateOrder(orderId, updatedOrder);
    }

    public List<Order> getAllOrders() {
        return orderController.getAllOrders();
    }
}
