import java.util.Scanner;

public class Avtomat {
    private Account account;
    private Scanner scanner;

    public Avtomat(Account account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Check balance");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Exit");
            System.out.print("> ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.println("Your balance: " + account.getBalance());
                    break;
                case "2":
                    System.out.print("Enter amount to deposit: ");
                    long depositAmount = Long.parseLong(scanner.nextLine());
                    if (depositAmount > 0) {
                        account.deposit(depositAmount);
                    }
                    break;
                case "3":
                    System.out.print("How much money to withdraw?\n> ");
                    long withdrawAmount = Long.parseLong(scanner.nextLine());
                    if (withdrawAmount > 0 && account.withdraw(withdrawAmount)) {
                        dispenseMoney(withdrawAmount);
                    } else if (withdrawAmount <= 0) {
                        System.out.println("Invalid amount");
                    } else {
                        System.out.println("Not enough money");
                    }
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
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