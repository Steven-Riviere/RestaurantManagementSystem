package controller;

import model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private List<Product> productList;
    private static final String FILE_PATH = "data/products.txt";

    public ProductController() {
        this.productList = new ArrayList<>();
        loadProductsFromFile();
    }

    // Ajouter un produit
    public void addProduct(Product product) {
        productList.add(product);
        saveProductsToFile();
    }

    // Modifier un produit existant
    public boolean updateProduct(String name, String category, double price) {
        for (Product product : productList) {
            if (product.getName().equalsIgnoreCase(name)) {
                product.setCategory(category);
                product.setPrice(price);
                saveProductsToFile();
                return true;
            }
        }
        return false;
    }

    // Supprimer un produit
    public boolean deleteProduct(String name) {
        boolean removed = productList.removeIf(product -> product.getName().equalsIgnoreCase(name));
        if (removed) {
            saveProductsToFile();
        }
        return removed;
    }

    // Récupérer un produit par son nom
    public Product getProductByName(String name) {
        for (Product product : productList) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null; // Si aucun produit trouvé
    }

    // Récupérer la liste complète des produits
    public List<Product> getProducts() {
        return productList;
    }

    // Sauvegarder les produits dans le fichier
    private void saveProductsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : productList) {
                writer.write(product.getName() + "," + product.getCategory() + "," + product.getPrice() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Charger les produits depuis le fichier
    private void loadProductsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0];
                    String category = data[1];
                    double price = Double.parseDouble(data[2]);
                    productList.add(new Product(name, price, category));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
