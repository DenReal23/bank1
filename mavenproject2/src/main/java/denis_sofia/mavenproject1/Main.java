package denis_sofia.mavenproject1;

import denis_sofia.mavenproject1.controller.Avtomat;
import denis_sofia.mavenproject1.repository.UserBase;
import denis_sofia.mavenproject1.service.AccountService;
import denis_sofia.mavenproject1.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserBase userBase = new UserBase();
        UserService userService = new UserService(userBase);
        AccountService accountService = new AccountService();
        Avtomat avtomat = new Avtomat(userService, accountService);
        avtomat.start();
    }
}