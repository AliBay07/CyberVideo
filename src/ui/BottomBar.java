package ui;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JPanel {
    private JButton subscribe;
    private JButton returnBluRay;
    private JButton help;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public BottomBar(boolean subscriber) {
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40));
        this.setLayout(new GridLayout(1, 3));
        //Trouver un moyen de spécifier la bottombar en fonction de l'état de connexion
        if(subscriber){
            subscribe = new JButton("Recharger ma carte");
        }
        else{
            subscribe = new JButton("S'abonner");
        }
        returnBluRay = new JButton("Rendre un blu-ray");
        help = new JButton("Aide");
        this.add(subscribe);
        this.add(returnBluRay);
        this.add(help);
    }
}
