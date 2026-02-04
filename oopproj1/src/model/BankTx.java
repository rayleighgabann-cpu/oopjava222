package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankTx {
    private int id;
    private String type;
    private BigDecimal amount;
    private String category;
    private String fromOwner;
    private String toOwner;
    private LocalDateTime date;

    public BankTx(String type, BigDecimal amount, String category) {
        this.type = type;
        this.amount = amount;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getFromOwner() { return fromOwner; }
    public void setFromOwner(String fromOwner) { this.fromOwner = fromOwner; }
    public String getToOwner() { return toOwner; }
    public void setToOwner(String toOwner) { this.toOwner = toOwner; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s -> %s | Cat: %s | %s",
                date, type, fromOwner, toOwner, category, amount);
    }
}