package controller;

import model.Table;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TableController {
    private List<Table> tables;
    private List<Table> linkedTables;
    private static final String FILE_PATH = "data/table.txt";

    public TableController() {
        this.tables = new ArrayList<>();
        this.linkedTables = new ArrayList<>();
        loadTablesFromFile(); // Charger les tables enregistrées
    }

    // Ajouter une table individuelle
    public void addTable(int tableNumber) {
        tables.add(new Table(tableNumber));
        saveTablesToFile();
    }

    // Supprimer une table individuelle
    public boolean removeTable(int tableNumber) {
        boolean removed = tables.removeIf(table -> table.getTableNumber() == tableNumber);
        if (removed) {
            saveTablesToFile();
        }
        return removed;
    }

    // Lier plusieurs tables pour une grande réservation
    public Table linkTables(List<Integer> tableNumbers) {
        Table linkedTable = new Table(Integer.parseInt(tableNumbers.get(0) + "99")); // Numéro temporaire unique
        linkedTables.add(linkedTable);
        return linkedTable;
    }

    // Dissocier une table fusionnée après paiement
    public void unlinkTable(int tempTableNumber) {
        linkedTables.removeIf(table -> table.getTableNumber() == tempTableNumber);
    }

    public List<Table> getTables() {
        return tables;
    }

    public List<Table> getLinkedTables() {
        return linkedTables;
    }

    // Sauvegarde des tables dans un fichier
    private void saveTablesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Table table : tables) {
                writer.write(table.getTableNumber() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Chargement des tables depuis un fichier
    private void loadTablesFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // Si le fichier n'existe pas encore, on ne fait rien
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int tableNumber = Integer.parseInt(line);
                tables.add(new Table(tableNumber));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
