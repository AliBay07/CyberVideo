package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NormalNavbar extends JPanel {
    private JButton leftMenu;
    private JLabel currentLocations;
    private JButton basket;
    private JButton rightMenu;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public NormalNavbar(int nbLocations){
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        this.setLayout(new GridLayout(1, 4));
        //this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        leftMenu = new JButton("trois barres");
        currentLocations = new JLabel(nbLocations + " locations en cours",JLabel.CENTER); //Avoir le nombre de location avec la BD
        basket = new JButton("Panier");
        rightMenu = new JButton("nomUser"); //Faire une version nomUtilisateur avec une constante d'environnement pour vérifier si un utilisateur est connecté
        this.add(leftMenu);
        this.add(currentLocations);
        this.add(basket);
        this.add(rightMenu);
    }

    public JButton getLeftMenu() {
        return leftMenu;
    }

    public JButton getRightMenu() {
        return rightMenu;
    }

    public JButton getBasket() {
        return basket;
    }
}
