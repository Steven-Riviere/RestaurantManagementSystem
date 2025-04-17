package view;

import model.Order;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private Scanner scanner;

    public OrderView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayOrderDetails(Order order) {
        if (order == null) {
            System.out.println("Aucune commande en cours.");
            return;
        }
        System.out.println("Commande pour la table : " + order.getTableNumber());

        List<String> items = order.getItems(); // Récupérer les items
        if (items.isEmpty()) {
            System.out.println("Aucun article dans la commande.");
        } else {
            for (String item : items) {
                System.out.println("- " + item);
            }
        }

        System.out.println("Statut : " + order.getStatus());
        System.out.println("Payée : " + (order.isPaid() ? "Oui" : "Non"));
    }

    public int getTableNumberInput() {
        System.out.print("Entrez le numéro de table : ");
        return scanner.nextInt();
    }

    public boolean confirmPayment() {
        System.out.print("Confirmer le paiement ? (oui/non) : ");
        String input = scanner.next();
        return input.equalsIgnoreCase("oui");
    }
}
