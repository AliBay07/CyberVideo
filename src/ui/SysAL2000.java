package ui;

import javax.swing.*;

/**
 * Application
 */
public class SysAL2000 {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    public static final int NAVBAR_HEIGHT = 40;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new InitialMainPage(SCREEN_WIDTH, SCREEN_HEIGHT).setVisible(true);
            }
        });
    }

    public static void showLoginPage() {
        new LoginPage(SCREEN_WIDTH, SCREEN_HEIGHT).setVisible(true);
    }

    public static void showSignupPage() {
        new SignupPage(SCREEN_WIDTH, SCREEN_HEIGHT).setVisible(true);
    }

}
