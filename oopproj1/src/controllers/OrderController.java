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

    // ГЛАВНЫЙ МЕТОД БИЗНЕС-ЛОГИКИ
    public String createOrder(Map<Integer, Integer> basket) {
        double total = 0;

        // 1. Считаем общую сумму ДО записи в базу
        for (Map.Entry<Integer, Integer> entry : basket.entrySet()) {
            int menuId = entry.getKey();
            int quantity = entry.getValue();
            MenuItem item = menuRepo.getMenuById(menuId);
            if (item != null) {
                total += item.getPrice() * quantity;
            }
        }

        // 2. Создаем чек в базе
        int orderId = orderRepo.createOrder(total);

        // 3. Сохраняем детали
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
        // Просто выводим список
        for (MenuItem item : menuRepo.getAllMenus()) {
            System.out.println(item.toString());
        }
    }
}