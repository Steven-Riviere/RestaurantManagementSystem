package model;

import java.util.ArrayList;
import java.util.List;

public class TableManager {
    private List<Table> tables;
    private List<Table> linkedTables;

    public TableManager() {
        this.tables = new ArrayList<>();
        this.linkedTables = new ArrayList<>();
    }

    // Ajouter une table individuelle
    public void addTable(int tableNumber) {
        tables.add(new Table(tableNumber));
    }

    // Lier plusieurs tables pour une grande réservation
    public Table linkTables(List<Integer> tableNumbers) {
        StringBuilder linkedTableName = new StringBuilder();
        for (int num : tableNumbers) {
            linkedTableName.append(num).append("-");
        }
        linkedTableName.append("bis");

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
}
