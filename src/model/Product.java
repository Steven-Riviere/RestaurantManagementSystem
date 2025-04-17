package model;

// Représente un produit dans le menu (plat, boisson, dessert)
public class Product {
    private String name;
    private double price;
    private String category; // Exemple : "Boisson", "Plat", "Dessert"

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " - " + category + " : " + price + "€";
    }
}
