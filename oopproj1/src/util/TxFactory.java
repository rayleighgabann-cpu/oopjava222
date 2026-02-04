package util;

import model.BankTx;
import java.math.BigDecimal;

public class TxFactory {
    public static BankTx createDeposit(BigDecimal amount) {
        return new BankTx("DEPOSIT", amount, "Salary");
    }

    public static BankTx createWithdraw(BigDecimal amount) {
        return new BankTx("WITHDRAWAL", amount, "General");
    }

    public static BankTx createTransfer(BigDecimal amount) {
        return new BankTx("TRANSFER", amount, "Transfer");
    }
}