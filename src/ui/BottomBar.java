package ui;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JPanel {
    private JButton suscribe;
    private JButton returnBluRay;
    private JButton help;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public BottomBar() {
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40));
        this.setLayout(new GridLayout(1, 3));
        //Trouver un moyen de spécifier la bottombar en fonction de l'état de connexion
        suscribe = new JButton("S'abonner");
        returnBluRay = new JButton("Rendre un blu-ray");
        help = new JButton("Aide");
        this.add(suscribe);
        this.add(returnBluRay);
        this.add(help);
    }
}
