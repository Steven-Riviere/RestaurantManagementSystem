package view;

import controller.RestaurantController;

public class Main {
    public static void main(String[] args) {
        RestaurantController restaurantController = new RestaurantController();

        // Lancer l'interface graphique dans le thread de l'UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            RestaurantManagementGUI gui = new RestaurantManagementGUI(restaurantController);
            gui.show();
        });
    }
}
