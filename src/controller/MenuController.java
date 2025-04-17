package controller;

import model.Menu;
import model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuController {
    private List<Menu> menuList;
    private int menuCounter;
    private static final String FILE_PATH = "data/menus.txt";

    public MenuController() {
        this.menuList = new ArrayList<>();
        loadMenusFromFile();
    }

    // ➤ Ajouter un menu
    public void addMenu(Menu menu) {
        menu.setMenuId(menuCounter++);
        menuList.add(menu);
        saveMenusToFile();
    }

    // ➤ Supprimer un menu par ID
    public boolean removeMenu(int menuId) {
        boolean removed = menuList.removeIf(menu -> menu.getMenuId() == menuId);
        if (removed) {
            saveMenusToFile();
        }
        return removed;
    }

    // ➤ Supprimer un menu par NOM
    public boolean removeMenuByName(String menuName) {
        boolean removed = menuList.removeIf(menu -> menu.getName().equalsIgnoreCase(menuName));
        if (removed) {
            saveMenusToFile();
        }
        return removed;
    }

    // ➤ Modifier un menu par ID
    public boolean updateMenu(int menuId, String newName) {
        for (Menu menu : menuList) {
            if (menu.getMenuId() == menuId) {
                menu.setName(newName);
                saveMenusToFile();
                return true;
            }
        }
        return false;
    }

    // ➤ Modifier un menu par NOM
    public boolean updateMenuByName(String oldMenuName, String newName) {
        for (Menu menu : menuList) {
            if (menu.getName().equalsIgnoreCase(oldMenuName)) {
                menu.setName(newName);
                saveMenusToFile();
                return true;
            }
        }
        return false;
    }

    // ➤ Récupérer tous les menus
    public List<Menu> getAllMenus() {
        return menuList;
    }

    // ➤ Ajouter un produit à un menu
    public boolean addProductToMenu(int menuId, Product product) {
        for (Menu menu : menuList) {
            if (menu.getMenuId() == menuId) {
                menu.addProduct(product);
                saveMenusToFile();
                return true;
            }
        }
        return false;
    }

    // ➤ Supprimer un produit d'un menu
    public boolean removeProductFromMenu(int menuId, String productName) {
        for (Menu menu : menuList) {
            if (menu.getMenuId() == menuId) {
                menu.removeProduct(productName);
                saveMenusToFile();
                return true;
            }
        }
        return false;
    }

    // ➤ Sauvegarder les menus dans un fichier
    private void saveMenusToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Menu menu : menuList) {
                writer.write(menu.getMenuId() + "," + menu.getName() + "," + menu.getProducts().size() + "\n");
                for (Product product : menu.getProducts()) {
                    writer.write(product.getName() + "," + product.getPrice() + "," + product.getCategory() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ➤ Charger les menus depuis un fichier
    private void loadMenusFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] menuData = line.split(",");
                if (menuData.length >= 3) {
                    int menuId = Integer.parseInt(menuData[0]);
                    String name = menuData[1];
                    int productCount = Integer.parseInt(menuData[2]);
                    Menu menu = new Menu(name);
                    menu.setMenuId(menuId);

                    for (int i = 0; i < productCount; i++) {
                        line = reader.readLine();
                        if (line != null) {
                            String[] productData = line.split(",");
                            if (productData.length == 3) {
                                String prodName = productData[0];
                                double price = Double.parseDouble(productData[1]);
                                String category = productData[2];
                                menu.addProduct(new Product(prodName, price, category));
                            }
                        }
                    }
                    menuList.add(menu);
                    menuCounter = Math.max(menuCounter, menuId + 1);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
