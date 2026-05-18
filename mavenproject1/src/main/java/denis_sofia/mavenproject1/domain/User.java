package denis_sofia.mavenproject1.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    private List<Account> accounts;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}