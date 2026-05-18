package denis_sofia.mavenproject1.controller;

import denis_sofia.mavenproject1.domain.User;
import denis_sofia.mavenproject1.enums.AccountType;
import denis_sofia.mavenproject1.enums.Nominal;
import denis_sofia.mavenproject1.service.AccountService;
import denis_sofia.mavenproject1.service.UserService;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

@Component
public class Avtomat {
    private final UserService userService;
    private final AccountService accountService;
    private final Scanner scanner;
    private User currentUser;

    public Avtomat(UserService userService, AccountService accountService, Scanner scanner) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("           HELLO!              ");
            System.out.println("========================================");
            System.out.println("  1. Sign in");
            System.out.println("  2. Sign up");
            System.out.println("  3. Exit");
            System.out.println("----------------------------------------");
            System.out.print("  Select option > ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        authUser();
                        if (currentUser != null) {
                            showMainMenu();
                        }
                        break;
                    case "2":
                        registerUser();
                        break;
                    case "3":
                        System.out.println("  Goodbye!");
                        return;
                    default:
                        System.out.println("  Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }

    private void authUser() throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("           AUTHENTICATION               ");
        System.out.println("----------------------------------------");
        System.out.print("  Login: ");
        String login = scanner.nextLine();
        System.out.print("  Password: ");
        String password = scanner.nextLine();
        currentUser = userService.login(login, password);
        System.out.println("  Welcome, " + currentUser.getLogin() + "!");
    }

    private void registerUser() throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("           REGISTRATION                 ");
        System.out.println("----------------------------------------");
        System.out.print("  New Login: ");
        String login = scanner.nextLine();
        System.out.print("  New Password: ");
        String password = scanner.nextLine();
        userService.register(login, password);
        System.out.println("  Registration successful! You can now sign in.");
    }

    private void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("        MAIN ACCOUNT MENU               ");
            System.out.println("========================================");
            System.out.println("  1. Check balance");
            System.out.println("  2. Deposit money");
            System.out.println("  3. Withdraw money");
            System.out.println("  4. Open account");
            System.out.println("  5. Transfer money");
            System.out.println("  6. Logout");
            System.out.println("----------------------------------------");
            System.out.print("  Select option > ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        long total = accountService.getBalance(currentUser.getAccounts());
                        System.out.println("  ----------------------------------------");
                        System.out.println("  Total available balance: " + total);
                        System.out.println("  ----------------------------------------");
                        break;
                    case "2":
                        System.out.print("  Enter Account ID: ");
                        String depId = scanner.nextLine();
                        System.out.print("  Enter amount: ");
                        long depAmt = Long.parseLong(scanner.nextLine());
                        accountService.deposit(depId, depAmt, currentUser.getAccounts());
                        System.out.println("  Deposit successful.");
                        break;
                    case "3":
                        System.out.print("  Enter Account ID: ");
                        String withId = scanner.nextLine();
                        System.out.print("  Enter amount: ");
                        long withAmt = Long.parseLong(scanner.nextLine());
                        accountService.withdraw(withId, withAmt, currentUser.getAccounts());
                        dispenseMoney(withAmt);
                        System.out.println("  Withdrawal successful.");
                        break;
                    case "4":
                        System.out.println("  1. Debit Account (starts at 0)");
                        System.out.println("  2. Credit Account (starts at 20000)");
                        System.out.print("  Select account type > ");
                        String accType = scanner.nextLine();
                        if (accType.equals("1")) {
                            accountService.open(currentUser, AccountType.DEBIT);
                        } else {
                            accountService.open(currentUser, AccountType.CREDIT);
                        }
                        System.out.println("  New account created successfully.");
                        break;
                    case "5":
                        System.out.print("  From Account ID: ");
                        String fromId = scanner.nextLine();
                        System.out.print("  To Account ID: ");
                        String toId = scanner.nextLine();
                        System.out.print("  Amount: ");
                        long trAmt = Long.parseLong(scanner.nextLine());
                        accountService.transfer(fromId, toId, trAmt, currentUser.getAccounts());
                        System.out.println("  Transfer successful.");
                        break;
                    case "6":
                        running = false;
                        currentUser = null;
                        System.out.println("  Logged out successfully.");
                        break;
                    default:
                        System.out.println("  Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }

    public void dispenseMoney(long amount) {
        System.out.println("  Dispensing banknotes:");
        System.out.println("  ----------------------------------------");
        long remaining = amount;
        for (Nominal d : Nominal.values()) {
            int count = 0;
            while (remaining >= d.getValue()) {
                remaining = remaining - d.getValue();
                count++;
            }
            if (count > 0) {
                System.out.println("    " + d.getValue() + " x " + count);
            }
        }
        System.out.println("  ----------------------------------------");
    }
}