package denis_sofia.mavenproject1.service;

import org.springframework.stereotype.Service;
import denis_sofia.mavenproject1.domain.Account;
import denis_sofia.mavenproject1.domain.Credit;
import denis_sofia.mavenproject1.domain.Debit;
import denis_sofia.mavenproject1.domain.User;
import denis_sofia.mavenproject1.enums.AccountType;
import denis_sofia.mavenproject1.exception.AccountNotFoundException;
import denis_sofia.mavenproject1.exception.InvalidAmountException;
import denis_sofia.mavenproject1.exception.NotEnoughMoneyException;
import java.util.List;

@Service
public class AccountService {
    private static int idCounter = 1;

    public Account open(User user, AccountType type) {
        String id = "ACC" + idCounter;
        idCounter++;
        Account account = type == AccountType.DEBIT ? new Debit(id, user.getLogin()) : new Credit(id, user.getLogin());
        user.addAccount(account);
        return account;
    }

    public void deposit(String accountId, long amount, List<Account> accounts) throws InvalidAmountException, AccountNotFoundException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        findAccount(accountId, accounts).deposit(amount);
    }

    public void withdraw(String accountId, long amount, List<Account> accounts) throws InvalidAmountException, AccountNotFoundException, NotEnoughMoneyException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        Account acc = findAccount(accountId, accounts);
        if (!acc.withdraw(amount)) {
            throw new NotEnoughMoneyException("Not enough money");
        }
    }

    public long getBalance(List<? extends Account> accounts) {
        long total = 0;
        for (Account acc : accounts) {
            System.out.println("Account " + acc.getId() + " [" + acc.getAccountType() + "]: " + acc.getBalance());
            total = total + acc.getBalance();
        }
        return total;
    }

    public void transfer(String fromId, String toId, long amount, List<Account> accounts) throws Exception {
        withdraw(fromId, amount, accounts);
        deposit(toId, amount, accounts);
    }

    private Account findAccount(String id, List<Account> accounts) throws AccountNotFoundException {
        for (Account acc : accounts) {
            if (acc.getId().equals(id)) {
                return acc;
            }
        }
        throw new AccountNotFoundException("Account not found");
    }
}