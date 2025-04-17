package view;

import javax.swing.*;
import java.awt.*;
import controller.ProductController;
import controller.MenuController;
import model.Product;
import model.Menu;
import java.util.List;

public class ProductManagementGUI extends JFrame {
    private JFrame frame;
    private ProductController productController;
    private MenuController menuController;
    private JTextField priceField;
    private JComboBox<String> categoryDropdown, productDropdown, menuDropdown;
    private JButton backButton;
    private JTextArea productDisplay;

    public ProductManagementGUI() {
        productController = new ProductController();
        menuController = new MenuController();

        setTitle("Gestion des Produits");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Produit
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Produit:"), gbc);
        gbc.gridx = 1;
        productDropdown = new JComboBox<>();
        updateProductDropdown();
        productDropdown.setEditable(true);
        inputPanel.add(productDropdown, gbc);

        // Prix
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Prix:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField();
        inputPanel.add(priceField, gbc);

        // Catégorie
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Catégorie:"), gbc);
        gbc.gridx = 1;
        categoryDropdown = new JComboBox<>(new String[]{"Boisson", "Plat", "Dessert"});
        inputPanel.add(categoryDropdown, gbc);

        // Ajouter au menu (optionnel)
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Ajouter au menu:"), gbc);
        gbc.gridx = 1;
        menuDropdown = new JComboBox<>();
        updateMenuDropdown();
        inputPanel.add(menuDropdown, gbc);

        // Organisation des boutons
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JButton addButton = new JButton("Ajouter");
        JButton updateButton = new JButton("Modifier");
        JButton assignToMenuButton = new JButton("Ajouter au Menu");
        JButton manageMenusButton = new JButton("Gérer les Menus");
        JButton deleteButton = new JButton("Supprimer");
        backButton = new JButton("Retour");

        // Bouton supprimer en rouge 🔴
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);

        // Ajout des boutons dans le panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(assignToMenuButton);
        buttonPanel.add(manageMenusButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // Zone d'affichage des produits
        productDisplay = new JTextArea();
        productDisplay.setEditable(false);
        add(new JScrollPane(productDisplay), BorderLayout.CENTER);

        // Actions des boutons
        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        assignToMenuButton.addActionListener(e -> assignProductToMenu());
        manageMenusButton.addActionListener(e -> new MenuManagementGUI(menuController));
        backButton.addActionListener(e -> dispose());

        displayProducts();
        setVisible(true);
    }

    private void addProduct() {
        String name = (String) productDropdown.getSelectedItem();
        String category = (String) categoryDropdown.getSelectedItem();
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        productController.addProduct(new Product(name, price, category));
        updateProductDropdown();
        displayProducts();
    }

    private void updateProduct() {
        String name = (String) productDropdown.getSelectedItem();
        String category = (String) categoryDropdown.getSelectedItem();
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        productController.updateProduct(name, category, price);
        displayProducts();
    }

    private void deleteProduct() {
        String name = (String) productDropdown.getSelectedItem();
        productController.deleteProduct(name);
        updateProductDropdown();
        displayProducts();
    }

    private void assignProductToMenu() {
        String selectedMenu = (String) menuDropdown.getSelectedItem();
        if (selectedMenu == null || selectedMenu.equals("Aucun")) {
            JOptionPane.showMessageDialog(this, "Le produit est enregistré sans menu.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String productName = (String) productDropdown.getSelectedItem();
        Product product = productController.getProductByName(productName);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Produit introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int menuId = Integer.parseInt(selectedMenu.split(" ")[1]);
        boolean success = menuController.addProductToMenu(menuId, product);
        if (success) {
            JOptionPane.showMessageDialog(this, "Produit ajouté au menu !");
        } else {
            JOptionPane.showMessageDialog(this, "Échec de l'ajout au menu.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProductDropdown() {
        productDropdown.removeAllItems();
        List<Product> products = productController.getProducts();
        for (Product product : products) {
            productDropdown.addItem(product.getName());
        }
    }

    private void updateMenuDropdown() {
        menuDropdown.removeAllItems();
        menuDropdown.addItem("Aucun"); // ✅ Ajout de l'option "Aucun"
        List<Menu> menus = menuController.getAllMenus();
        for (Menu menu : menus) {
            menuDropdown.addItem("Menu " + menu.getMenuId());
        }
    }

    private void displayProducts() {
        List<Product> products = productController.getProducts();
        productDisplay.setText("");
        for (Product product : products) {
            productDisplay.append(product.toString() + "\n");
        }
    }
}
