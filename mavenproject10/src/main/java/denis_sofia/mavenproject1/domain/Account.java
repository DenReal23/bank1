package denis_sofia.mavenproject1.domain;

import denis_sofia.mavenproject1.enums.AccountType;

public abstract class Account {
    private String id;
    protected long balance;
    protected String ownerName;

    public Account(String id, String ownerName, long initialBalance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public abstract AccountType getAccountType();
    public abstract void deposit(long amount);
    public abstract boolean withdraw(long amount);
}