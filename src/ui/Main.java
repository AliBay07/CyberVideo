package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principale de test
 */
public class Main {
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();
    public static int FRAME_HEIGHT = (int) dimension.getHeight();

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SubscriberMainPage smp = new SubscriberMainPage(frame);
        smp.setVisible(true);
        frame.add(smp);
        frame.setVisible(true);

        /*InitialMainPage imp = new InitialMainPage(frame);
        imp.setVisible(true);
        frame.add(imp);
        frame.setVisible(true);*/
    }
    
}
