package repositories;

import data.PostgresDB;
import java.sql.*;

public class OrderRepository {
    private final PostgresDB db;

    public OrderRepository(PostgresDB db) {
        this.db = db;
    }

    // Метод создает пустой заказ и возвращает его ID
    public int createOrder(double totalAmount) {
        try {
            Connection con = db.getConnection();
            String sql = "INSERT INTO orders (order_date, total_amount) VALUES (CURRENT_TIMESTAMP, ?) RETURNING id";
            PreparedStatement st = con.prepareStatement(sql);
            st.setDouble(1, totalAmount);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("Order Error: " + e.getMessage());
        }
        return -1;
    }

    // Метод добавляет детали (связь M:M)
    public void addOrderDetails(int orderId, int menuId, int quantity) {
        try {
            Connection con = db.getConnection();
            String sql = "INSERT INTO order_details (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, orderId);
            st.setInt(2, menuId);
            st.setInt(3, quantity);
            st.execute();
        } catch (Exception e) {
            System.out.println("Details Error: " + e.getMessage());
        }
    }
}