package denis_sofia.mavenproject1.domain;

import denis_sofia.mavenproject1.enums.AccountType;

public class Debit extends Account {

    public Debit(String id, String ownerName) {
        super(id, ownerName, 0);
    }

    public AccountType getAccountType() {
        return AccountType.DEBIT;
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