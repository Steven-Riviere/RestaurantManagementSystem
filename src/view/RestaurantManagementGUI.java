package view;

import controller.RestaurantController;
import controller.TableController;
import controller.ProductController; // Ajout du ProductController
import javax.swing.*;
import java.awt.*;

public class RestaurantManagementGUI {
    private JFrame frame;
    private RestaurantController restaurantController;
    private TableController tableController;
    private ProductController productController; // Ajout du ProductController
    private JPanel mainPanel;
    private JButton openButton, closeButton, tablesButton, productsButton, orderButton;

    public RestaurantManagementGUI(RestaurantController restaurantController) {
        this.restaurantController = restaurantController;
        this.tableController = new TableController();
        this.productController = new ProductController(); // Initialisation du ProductController
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestion du Restaurant");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        openButton = new JButton("Ouvrir le Restaurant");
        closeButton = new JButton("Fermer le Restaurant");

        openButton.addActionListener(e -> openRestaurantUI());
        closeButton.addActionListener(e -> closeRestaurantUI());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(openButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
    }

    private void openRestaurantUI() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel sectionPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        tablesButton = new JButton("Gérer les Tables");
        productsButton = new JButton("Gérer les Produits");
        orderButton = new JButton("Passer une Commande");

        // Ouvrir la gestion des tables
        tablesButton.addActionListener(e -> new TableManagementGUI(tableController).show());

        // Ouvrir la gestion des produits
        productsButton.addActionListener(e -> new ProductManagementGUI());

        sectionPanel.add(tablesButton);
        sectionPanel.add(productsButton);
        sectionPanel.add(orderButton);

        mainPanel.add(sectionPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void closeRestaurantUI() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(openButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void show() {
        frame.setVisible(true);
    }
}
