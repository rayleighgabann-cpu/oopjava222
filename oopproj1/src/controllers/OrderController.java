package controllers;

import entities.MenuItem;
import repositories.MenuRepository;
import repositories.OrderRepository;
import java.util.Map;

public class OrderController {
    private final MenuRepository menuRepo;
    private final OrderRepository orderRepo;

    public OrderController(MenuRepository menuRepo, OrderRepository orderRepo) {
        this.menuRepo = menuRepo;
        this.orderRepo = orderRepo;
    }

    public String createOrder(Map<Integer, Integer> basket) {
        double total = 0;

        for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
            int menuId = entry.getKey();
            int quantity = entry.getValue();
            MenuItem item = menuRepo.getMenuById(menuId);
            if (item != null) {
                total += item.getPrice() * quantity;
            }
        }

        int orderId = orderRepo.createOrder(total);

        if (orderId != -1) {
            for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
                orderRepo.addOrderDetails(orderId, entry.getKey(), entry.getValue());
            }
            return "Order created successfully! Total: " + total;
        } else {
            return "Failed to create order.";
        }
    }

    public void showMenu() {
        for (MenuItem item : menuRepo.getAllMenus()) {
            System.out.println(item.toString());
        }
    }
}