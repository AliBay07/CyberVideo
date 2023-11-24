package ui;

import facade.ui.FacadeIHM;

import javax.swing.*;

/**
 * Application
 */
public class SysAL2000 extends JFrame {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int NAVBAR_HEIGHT = 40;

    public static int DIALOG_WIDTH = 500;
    public static int DIALOG_HEIGHT = 400;

    private static SysAL2000 instance;
    private Controller controller;

    private SysAL2000() {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        //
        controller = new Controller(this, new FacadeIHM());
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
                getInstance().controller.showMainPage();
            }
        });
    }
}
