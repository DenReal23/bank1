package denis_sofia.mavenproject1.domain;

import denis_sofia.mavenproject1.enums.AccountType;

public class Credit extends Account {

    public Credit(String id, String ownerName) {
        super(id, ownerName, 20000);
    }

    public AccountType getAccountType() {
        return AccountType.CREDIT;
    }

    public void deposit(long amount) {
        balance = balance + amount;
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
            return true;
        }
        return false;
    }
}