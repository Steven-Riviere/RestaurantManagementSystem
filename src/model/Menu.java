package model;

import java.util.ArrayList;
import java.util.List;

// Représente un menu avec une liste de produits
public class Menu {
    private int menuId;
    private String name;
    private List<Product> products;

    // Constructeur avec nom
    public Menu(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String name) {
        products.removeIf(product -> product.getName().equalsIgnoreCase(name));
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public String toString() {
        return "Menu " + menuId + " - " + name;
    }
}
