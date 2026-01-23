package repositories;

import data.PostgresDB;
import entities.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    private final PostgresDB db;

    public MenuRepository(PostgresDB db) {
        this.db = db;
    }

    public List<MenuItem> getAllMenus() {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, name, price FROM menu_items";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<MenuItem> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
            return foods;
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
            return null;
        }
        // В реальном проекте не забудь закрыть соединение в finally!
    }

    public MenuItem getMenuById(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, name, price FROM menu_items WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
            }
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }
}