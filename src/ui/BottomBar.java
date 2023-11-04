package ui;

import javax.swing.*;
import java.awt.*;

public class BottomBar extends JPanel {
    private JButton suscribe;
    private JButton returnBluRay;
    private JButton help;

    public BottomBar() {
        this.setPreferredSize(new Dimension(1280,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        suscribe = new JButton("S'abonner");
        returnBluRay = new JButton("Rendre un blu-ray");
        help = new JButton("Aide");
        this.add(suscribe);
        this.add(returnBluRay);
        this.add(help);
    }
}
