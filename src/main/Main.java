package main;

import controller.LoginController;
import view.LoginView;

public class Main {

    public static void main(String[] args) {
        
        LoginView login = new LoginView();
        login.setVisible(true);

        LoginController logincontroller = new LoginController(login);
        logincontroller.initializeController();
        
    }
}
