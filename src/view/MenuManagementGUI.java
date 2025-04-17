package view;

import controller.MenuController;
import model.Menu;
import model.Product;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuManagementGUI extends JFrame {
    private MenuController menuController;
    private JTextField menuNameField;
    private JComboBox<String> menuDropdown;
    private JTextArea productDisplay;
    private JList<String> menuList;
    private DefaultListModel<String> menuListModel;

    public MenuManagementGUI(MenuController menuController) {
        this.menuController = menuController;

        setTitle("Gestion des Menus");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🎯 Panel principal pour la liste des menus et les produits liés
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Divise en 2 parties égales

        // 📜 Section gauche : Liste des menus
        menuListModel = new DefaultListModel<>();
        menuList = new JList<>(menuListModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayProductsForClickedMenu();
            }
        });

        JScrollPane menuScrollPane = new JScrollPane(menuList);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("📜 Liste des Menus"));
        menuScrollPane.setPreferredSize(new Dimension(350, 400));

        centerPanel.add(menuScrollPane);

        // 🍽 Section droite : Produits rattachés
        productDisplay = new JTextArea(15, 30);
        productDisplay.setEditable(false);
        productDisplay.setBorder(BorderFactory.createTitledBorder("🍽 Produits rattachés"));
        centerPanel.add(new JScrollPane(productDisplay));

        add(centerPanel, BorderLayout.CENTER); // Ajout des deux sections centrales

        // 🎯 Section du haut : Ajout / Gestion des menus
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("📝 Nom du menu:"), gbc);
        gbc.gridx = 1;
        menuNameField = new JTextField();
        inputPanel.add(menuNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("📌 Menus existants:"), gbc);
        gbc.gridx = 1;
        menuDropdown = new JComboBox<>();
        updateMenuDropdown();
        menuDropdown.addActionListener(e -> displayProductsForSelectedMenu());
        inputPanel.add(menuDropdown, gbc);

        // 🎯 Organisation des boutons
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton addMenuButton = new JButton("✅ Créer Menu");
        JButton updateMenuButton = new JButton("✏️ Modifier");
        JButton deleteMenuButton = new JButton("🗑️ Supprimer");

        deleteMenuButton.setBackground(new Color(220, 53, 69)); // 🔴 Rouge doux
        deleteMenuButton.setForeground(Color.WHITE);

        buttonPanel.add(addMenuButton);
        buttonPanel.add(updateMenuButton);
        buttonPanel.add(deleteMenuButton);
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // 🎯 Actions des boutons
        addMenuButton.addActionListener(e -> addMenu());
        updateMenuButton.addActionListener(e -> updateMenu());
        deleteMenuButton.addActionListener(e -> deleteMenu());

        updateMenuDropdown();
        updateMenuList();
        setVisible(true);
    }

    private void addMenu() {
        String name = menuNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom du menu ne peut pas être vide.", "❌ Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        menuController.addMenu(new Menu(name));
        updateMenuDropdown();
        updateMenuList();
    }

    private void updateMenu() {
        String selectedMenu = (String) menuDropdown.getSelectedItem();
        if (selectedMenu == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un menu.", "❌ Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newName = menuNameField.getText().trim();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nouveau nom ne peut pas être vide.", "❌ Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int menuId = Integer.parseInt(selectedMenu.split(" ")[1]);
        menuController.updateMenu(menuId, newName);
        updateMenuDropdown();
        updateMenuList();
    }

    private void deleteMenu() {
        String selectedMenu = (String) menuDropdown.getSelectedItem();
        if (selectedMenu == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un menu à supprimer.", "❌ Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int menuId = Integer.parseInt(selectedMenu.split(" ")[1]);
        menuController.removeMenu(menuId);
        updateMenuDropdown();
        updateMenuList();
        productDisplay.setText("");
    }

    private void updateMenuDropdown() {
        menuDropdown.removeAllItems();
        List<Menu> menus = menuController.getAllMenus();
        for (Menu menu : menus) {
            menuDropdown.addItem(menu.toString());
        }
    }

    private void updateMenuList() {
        menuListModel.clear();
        List<Menu> menus = menuController.getAllMenus();
        for (Menu menu : menus) {
            menuListModel.addElement(menu.toString());
        }
    }

    private void displayProductsForSelectedMenu() {
        String selectedMenu = (String) menuDropdown.getSelectedItem();
        if (selectedMenu == null) {
            productDisplay.setText("");
            return;
        }

        int menuId = Integer.parseInt(selectedMenu.split(" ")[1]);
        List<Product> products = menuController.getAllMenus()
                .stream()
                .filter(menu -> menu.getMenuId() == menuId)
                .findFirst()
                .map(Menu::getProducts)
                .orElse(new java.util.ArrayList<>());

        productDisplay.setText("");
        for (Product product : products) {
            productDisplay.append(product.toString() + "\n");
        }
    }

    private void displayProductsForClickedMenu() {
        String selectedMenu = menuList.getSelectedValue();
        if (selectedMenu == null) {
            productDisplay.setText("");
            return;
        }

        int menuId = Integer.parseInt(selectedMenu.split(" ")[1]);
        List<Product> products = menuController.getAllMenus()
                .stream()
                .filter(menu -> menu.getMenuId() == menuId)
                .findFirst()
                .map(Menu::getProducts)
                .orElse(new java.util.ArrayList<>());

        productDisplay.setText("");
        for (Product product : products) {
            productDisplay.append(product.toString() + "\n");
        }
    }
}
