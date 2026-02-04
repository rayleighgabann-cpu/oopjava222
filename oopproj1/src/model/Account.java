package model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String number;
    private String owner;
    private BigDecimal balance;
    private String pin;
    private String status;

    public Account() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return number + " | " + owner + " | " + balance + " | " + status;
    }
}