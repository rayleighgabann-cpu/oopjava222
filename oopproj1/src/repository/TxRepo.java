package repository;

import config.DbConnection;
import model.BankTx;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TxRepo {
    public void save(int fromId, int toId, BankTx tx) {
        try {
            String sql = "INSERT INTO transactions (from_acc_id, to_acc_id, amount, type, category_id) VALUES (?, ?, ?, ?, (SELECT id FROM categories WHERE name = ?))";
            PreparedStatement ps = DbConnection.get().prepareStatement(sql);

            if (fromId == 0) ps.setNull(1, Types.INTEGER); else ps.setInt(1, fromId);
            if (toId == 0) ps.setNull(2, Types.INTEGER); else ps.setInt(2, toId);

            ps.setBigDecimal(3, tx.getAmount());
            ps.setString(4, tx.getType());
            ps.setString(5, tx.getCategory());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BankTx> findAllJoined() {
        List<BankTx> list = new ArrayList<>();
        String sql = """
            SELECT t.id, t.type, t.amount, t.created_at, 
                   c.name as category_name,
                   COALESCE(a1.owner, 'ATM') as sender,
                   COALESCE(a2.owner, 'ATM') as receiver
            FROM transactions t
            LEFT JOIN accounts a1 ON t.from_acc_id = a1.id
            LEFT JOIN accounts a2 ON t.to_acc_id = a2.id
            JOIN categories c ON t.category_id = c.id
            ORDER BY t.created_at DESC
        """;
        try {
            ResultSet rs = DbConnection.get().createStatement().executeQuery(sql);
            while (rs.next()) {
                BankTx tx = new BankTx(rs.getString("type"), rs.getBigDecimal("amount"), rs.getString("category_name"));
                tx.setId(rs.getInt("id"));
                tx.setFromOwner(rs.getString("sender"));
                tx.setToOwner(rs.getString("receiver"));
                tx.setDate(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(tx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}