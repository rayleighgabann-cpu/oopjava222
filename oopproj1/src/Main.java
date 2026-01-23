import controllers.OrderController;
import data.PostgresDB;
import repositories.MenuRepository;
import repositories.OrderRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PostgresDB db = new PostgresDB();
        MenuRepository menuRepo = new MenuRepository(db);
        OrderRepository orderRepo = new OrderRepository(db);
        OrderController controller = new OrderController(menuRepo, orderRepo);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- CAFE SYSTEM ---");
            System.out.println("1. Show Menu");
            System.out.println("2. Make an Order");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();

            if (choice == 1) {
                controller.showMenu();
            } else if (choice == 2) {
                // Логика сбора корзины в консоли
                Map<Integer, Integer> basket = new HashMap<>();
                while(true) {
                    System.out.println("Enter Menu ID (or 0 to finish):");
                    int id = scanner.nextInt();
                    if (id == 0) break;
                    System.out.println("Enter Quantity:");
                    int q = scanner.nextInt();
                    basket.put(id, q);
                }
                // Отправляем корзину в контроллер
                System.out.println(controller.createOrder(basket));
            } else if (choice == 0) {
                break;
            }
        }
    }
}