package controller;

import model.User;
import repository.UserRepo;
import service.BankService;
import java.math.BigDecimal;

public class MainController {
    private final BankService service = new BankService();
    private final UserRepo userRepo = new UserRepo();
    private User currentUser;

    public boolean login(String user, String pass) {
        var optionalUser = userRepo.login(user, pass);
        optionalUser.ifPresent(u -> currentUser = u);
        return optionalUser.isPresent();
    }

    public void processDeposit(int id, BigDecimal amount) {
        if (!isAdmin()) throw new SecurityException("Admin only!");
        service.deposit(id, amount);
        System.out.println("Deposit successful");
    }

    public void processWithdraw(int id, BigDecimal amount, String pin) {
        service.withdraw(id, amount, pin);
        System.out.println("Withdraw successful");
    }

    public void processTransfer(int from, int to, BigDecimal amount, String pin) {
        service.transfer(from, to, amount, pin);
        System.out.println("Transfer successful");
    }

    public void showReports() {
        System.out.println("--- Full Report (JOINED) ---");
        service.getAllTransactions().forEach(System.out::println);

        System.out.println("\n--- Large Tx (>5000) (LAMBDA) ---");
        service.getLargeTransactions(new BigDecimal("5000")).forEach(System.out::println);
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    public User getCurrentUser() { return currentUser; }
}