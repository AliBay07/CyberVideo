package ui;

import javax.swing.*;

/**
 * Application
 */
public class SysAL2000 extends JFrame {

    private static SysAL2000 instance;

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int NAVBAR_HEIGHT = 40;

    public static int DIALOG_WIDTH = 500;
    public static int DIALOG_HEIGHT = 400;

    private SysAL2000() {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static SysAL2000 getInstance() {
        if(instance==null){
            instance = new SysAL2000();
        }
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getInstance().showSignupPage();
            }
        });
    }

    public void showMainPage() {
        InitialMainPage page = new InitialMainPage(this);
        page.setVisible(true);
        this.add(page);
    }

    public void showLoginPage() {
        LoginPage page = new LoginPage(this);
        page.setVisible(true);
        this.add(page);
    }

    public void showSignupPage() {
        SignupPage page = new SignupPage(this);
        page.setVisible(true);
        this.add(page);
    }

}
