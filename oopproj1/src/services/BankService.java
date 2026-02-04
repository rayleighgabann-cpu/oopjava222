package service;

import model.Account;
import model.BankTx;
import repository.AccountRepo;
import repository.TxRepo;
import util.TxFactory;
import util.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BankService {
    private final AccountRepo accRepo = new AccountRepo();
    private final TxRepo txRepo = new TxRepo();

    public void deposit(int accId, BigDecimal amount) {
        Validator.validateAmount(amount);
        accRepo.updateBalance(accId, amount);
        txRepo.save(0, accId, TxFactory.createDeposit(amount));
    }

    public void withdraw(int accId, BigDecimal amount, String pin) {
        Validator.validateAmount(amount);
        Validator.validatePin(pin);

        Account acc = accRepo.findById(accId);
        if (!acc.getPin().equals(pin)) throw new SecurityException("Wrong PIN");
        if (acc.getBalance().compareTo(amount) < 0) throw new IllegalStateException("No funds");

        accRepo.updateBalance(accId, amount.negate());
        txRepo.save(accId, 0, TxFactory.createWithdraw(amount));
    }

    public void transfer(int fromId, int toId, BigDecimal amount, String pin) {
        Validator.validateAmount(amount);
        Validator.validatePin(pin);

        Account from = accRepo.findById(fromId);
        if (!from.getPin().equals(pin)) throw new SecurityException("Wrong PIN");
        if (from.getBalance().compareTo(amount) < 0) throw new IllegalStateException("No funds");

        accRepo.findById(toId);

        accRepo.updateBalance(fromId, amount.negate());
        accRepo.updateBalance(toId, amount);
        txRepo.save(fromId, toId, TxFactory.createTransfer(amount));
    }

    public List<BankTx> getLargeTransactions(BigDecimal limit) {
        return txRepo.findAllJoined().stream()
                .filter(t -> t.getAmount().compareTo(limit) > 0)
                .collect(Collectors.toList());
    }

    public List<BankTx> getAllTransactions() {
        return txRepo.findAllJoined();
    }

    public List<Account> getAllAccounts() {
        return accRepo.findAll();
    }
}