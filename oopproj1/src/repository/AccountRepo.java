package repository;

import config.DbConnection;
import model.Account;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountRepo {
    public Account findById(int id) {
        try {
            PreparedStatement ps = DbConnection.get().prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) { e.printStackTrace(); }
        throw new RuntimeException("Account not found");
    }

    public List<Account> findAll() {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet rs = DbConnection.get().createStatement().executeQuery("SELECT * FROM accounts");
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void updateBalance(int id, BigDecimal amount) {
        try {
            PreparedStatement ps = DbConnection.get().prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
            ps.setBigDecimal(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Account map(ResultSet rs) throws Exception {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setNumber(rs.getString("number"));
        a.setOwner(rs.getString("owner"));
        a.setBalance(rs.getBigDecimal("balance"));
        a.setPin(rs.getString("pin"));
        a.setStatus(rs.getString("status"));
        return a;
    }
}