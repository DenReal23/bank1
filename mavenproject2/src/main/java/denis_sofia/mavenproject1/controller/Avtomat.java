package denis_sofia.mavenproject1.controller;

import denis_sofia.mavenproject1.domain.User;
import denis_sofia.mavenproject1.enums.AccountType;
import denis_sofia.mavenproject1.enums.Nominal;
import denis_sofia.mavenproject1.service.AccountService;
import denis_sofia.mavenproject1.service.UserService;
import java.util.List;
import java.util.Scanner;

public class Avtomat {
    private UserService userService;
    private AccountService accountService;
    private Scanner scanner;
    private User currentUser;

    public Avtomat(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            System.out.print("> ");
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
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void authUser() throws Exception {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        currentUser = userService.login(login, password);
        System.out.println("Welcome " + currentUser.getLogin());
    }

    private void registerUser() throws Exception {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        userService.register(login, password);
        System.out.println("Registration successful");
    }

    private void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Check balance");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Open account");
            System.out.println("5. Transfer money");
            System.out.println("6. Logout");
            System.out.print("> ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        long total = accountService.getBalance(currentUser.getAccounts());
                        System.out.println("Total available: " + total);
                        break;
                    case "2":
                        System.out.print("Account ID: ");
                        String depId = scanner.nextLine();
                        System.out.print("Amount: ");
                        long depAmt = Long.parseLong(scanner.nextLine());
                        accountService.deposit(depId, depAmt, currentUser.getAccounts());
                        System.out.println("Deposited successfully");
                        break;
                    case "3":
                        System.out.print("Account ID: ");
                        String withId = scanner.nextLine();
                        System.out.print("Amount: ");
                        long withAmt = Long.parseLong(scanner.nextLine());
                        accountService.withdraw(withId, withAmt, currentUser.getAccounts());
                        dispenseMoney(withAmt);
                        System.out.println("Withdrawn successfully");
                        break;
                    case "4":
                        System.out.println("1. Debit");
                        System.out.println("2. Credit");
                        System.out.print("> ");
                        String accType = scanner.nextLine();
                        if (accType.equals("1")) {
                            accountService.open(currentUser, AccountType.DEBIT);
                        } else {
                            accountService.open(currentUser, AccountType.CREDIT);
                        }
                        System.out.println("Account created");
                        break;
                    case "5":
                        System.out.print("From ID: ");
                        String fromId = scanner.nextLine();
                        System.out.print("To ID: ");
                        String toId = scanner.nextLine();
                        System.out.print("Amount: ");
                        long trAmt = Long.parseLong(scanner.nextLine());
                        accountService.transfer(fromId, toId, trAmt, currentUser.getAccounts());
                        System.out.println("Transferred successfully");
                        break;
                    case "6":
                        running = false;
                        currentUser = null;
                        System.out.println("Logged out");
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void dispenseMoney(long amount) {
        long remaining = amount;
        for (Nominal d : Nominal.values()) {
            int count = 0;
            while (remaining >= d.getValue()) {
                remaining = remaining - d.getValue();
                count++;
            }
            if (count > 0) {
                System.out.println(d.getValue() + " x " + count);
            }
        }
    }
}