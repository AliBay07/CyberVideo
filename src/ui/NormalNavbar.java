package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import beans.*;

public class NormalNavbar extends JPanel {
    private JButton leftMenu;
    private JLabel currentLocations;
    private JButton basket;
    private JButton rightMenu;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public NormalNavbar(Account account, int nbReservations){
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        this.setLayout(new GridLayout(1, 4));
        //this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        // Redimensionnement de l'icône pour le bouton leftMenu
        ImageIcon menuIcon = new ImageIcon("src/ui/Images/menu.png");
        int iconWidth = 30; // Largeur souhaitée
        int iconHeight = 30; // Hauteur souhaitée
        Image scaledMenuImage = menuIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
        menuIcon = new ImageIcon(scaledMenuImage);
        leftMenu = new JButton("Menu", menuIcon);

        currentLocations = new JLabel(nbReservations + " locations en cours", JLabel.CENTER); // Obtenir le nombre de locations de la BD

        // Redimensionnement de l'icône pour le bouton basket
        ImageIcon basketIcon = new ImageIcon("src/ui/Images/panier.png");
        Image scaledBasketImage = basketIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
        basketIcon = new ImageIcon(scaledBasketImage);
        basket = new JButton("Panier", basketIcon);

        rightMenu = new JButton(account.getUser().getFirstName()); // Utiliser une constante d'environnement pour vérifier si un utilisateur est connecté

        // Ajout des composants au panel
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
