package ui;

import facade.ui.FacadeIHM;
import javax.swing.*;

import dao.tools.Session;

import java.awt.*;
import java.sql.SQLException;

/**
 * Application
 */
public class SysAL2000 extends JFrame {

    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();
    public static int FRAME_HEIGHT = (int) dimension.getHeight();
    public static int DIALOG_WIDTH = (int) (dimension.getWidth()/3)*2;
    public static int DIALOG_HEIGHT = (int) (dimension.getHeight()/3)*2;
    public static final int NAVBAR_HEIGHT = 40;


    private static SysAL2000 instance;
    private Controller controller;

    private SysAL2000() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
            	Session session = new Session(false);
            	try {
					session.open();
					getInstance().controller.showMainPage();
				} catch (SQLException e) {
					try {
						session.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
            }
        });
    }
}
