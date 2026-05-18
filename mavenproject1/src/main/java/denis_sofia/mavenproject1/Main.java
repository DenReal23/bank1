public class Main {
    public static void main(String[] args) {
        Account account = new Account("User", 0);
        Avtomat avtomat = new Avtomat(account);
        avtomat.showMenu();
    }
}