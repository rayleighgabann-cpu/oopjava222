package view;

import controller.MainController;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MainController ctrl = new MainController();

        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (ctrl.login(user, pass)) {
            System.out.println("Welcome " + ctrl.getCurrentUser().getUsername() + " [" + ctrl.getCurrentUser().getRole() + "]");

            boolean running = true;
            while(running) {
                if (ctrl.isAdmin()) {
                    System.out.println("\n1. Deposit\n2. Reports\n0. Exit");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        System.out.print("Acc ID: "); int id = sc.nextInt();
                        System.out.print("Amount: "); BigDecimal amt = sc.nextBigDecimal();
                        ctrl.processDeposit(id, amt);
                    } else if (choice == 2) {
                        ctrl.showReports();
                    } else {
                        running = false;
                    }
                } else {
                    System.out.println("\n1. Withdraw\n2. Transfer\n0. Exit");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        System.out.print("Acc ID: "); int id = sc.nextInt();
                        System.out.print("Amount: "); BigDecimal amt = sc.nextBigDecimal();
                        System.out.print("PIN: "); String pin = sc.next();
                        ctrl.processWithdraw(id, amt, pin);
                    } else if (choice == 2) {
                        System.out.print("From ID: "); int from = sc.nextInt();
                        System.out.print("To ID: "); int to = sc.nextInt();
                        System.out.print("Amount: "); BigDecimal amt = sc.nextBigDecimal();
                        System.out.print("PIN: "); String pin = sc.next();
                        ctrl.processTransfer(from, to, amt, pin);
                    } else {
                        running = false;
                    }
                }
            }
        } else {
            System.out.println("Login failed");
        }
        sc.close();
    }
}