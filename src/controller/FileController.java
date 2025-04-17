package controller;

import model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileController {

    private static final String ORDER_FILE = "data/order.txt";

    // Vérifie si un fichier existe, sinon le crée
    private static void ensureFileExists(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Crée le dossier si nécessaire
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier : " + filename);
                e.printStackTrace();
            }
        }
    }

    // Sauvegarde une liste d'objets dans un fichier
    private static <T> void saveToFile(String filename, List<T> data) {
        ensureFileExists(filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du fichier " + filename);
            e.printStackTrace();
        }
    }

    // Charge une liste d'objets depuis un fichier
    private static <T> List<T> loadFromFile(String filename) {
        ensureFileExists(filename);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement du fichier " + filename);
            return new ArrayList<>(); // Retourne une liste vide en cas d'erreur
        }
    }

    // Sauvegarde les commandes
    public static void saveOrders(List<Order> orders) {
        saveToFile(ORDER_FILE, orders);
    }

    // Charge les commandes
    public static List<Order> loadOrders() {
        return loadFromFile(ORDER_FILE);
    }
}
