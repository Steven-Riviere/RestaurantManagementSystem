package view;

import controller.TableController;
import javax.swing.*;
import java.awt.*;

public class TableManagementGUI {
    private JFrame frame;
    private TableController tableController;
    private JPanel mainPanel;
    private JButton addTableButton, removeTableButton, backButton;
    private JList<String> tableList;
    private DefaultListModel<String> tableListModel;

    public TableManagementGUI(TableController tableController) {
        this.tableController = tableController;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestion des Tables");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        tableListModel = new DefaultListModel<>();
        tableList = new JList<>(tableListModel);
        updateTableList();

        JScrollPane scrollPane = new JScrollPane(tableList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addTableButton = new JButton("Ajouter Table");
        removeTableButton = new JButton("Supprimer Table");
        backButton = new JButton("Retour");

        buttonPanel.add(addTableButton);
        buttonPanel.add(removeTableButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addTableButton.addActionListener(e -> addTable());
        removeTableButton.addActionListener(e -> removeTable());
        backButton.addActionListener(e -> frame.dispose());

        frame.add(mainPanel, BorderLayout.CENTER);
    }

    private void addTable() {
        String tableNumberStr = JOptionPane.showInputDialog(frame, "Numéro de la table :");
        if (tableNumberStr != null && !tableNumberStr.isEmpty()) {
            try {
                int tableNumber = Integer.parseInt(tableNumberStr);
                tableController.addTable(tableNumber);
                updateTableList();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un numéro valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeTable() {
        String selectedTable = tableList.getSelectedValue();
        if (selectedTable != null) {
            int tableNumber = Integer.parseInt(selectedTable.split(" ")[1]);
            tableController.removeTable(tableNumber);
            updateTableList();
        } else {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une table à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableList() {
        tableListModel.clear();
        for (model.Table table : tableController.getTables()) {
            tableListModel.addElement("Table " + table.getTableNumber());
        }
    }

    public void show() {
        frame.setVisible(true);
    }
}
