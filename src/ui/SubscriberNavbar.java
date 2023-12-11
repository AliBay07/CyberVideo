package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import beans.*;

public class SubscriberNavbar extends JPanel {
    private JButton leftMenu;
    private JLabel currentLocations;
    private JLabel currentMoney;
    private JButton basket;
    private JButton rightMenu;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public SubscriberNavbar(Account account, int nbReservations){
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        this.setLayout(new GridLayout(1, 5));
        //this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        ImageIcon menuIcon = new ImageIcon("src/ui/Images/menu.png");
        int iconWidth = 30; // Largeur souhaitée
        int iconHeight = 30; // Hauteur souhaitée
        Image scaledMenuImage = menuIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
        menuIcon = new ImageIcon(scaledMenuImage);

        leftMenu = new JButton("Menu", menuIcon);

        currentLocations = new JLabel(nbReservations + " locations en cours", JLabel.CENTER); // Obtenir le nombre de locations de la BD

        float amount = 0;
        if(account instanceof SubscriberAccount){
            List<SubscriberCard> listCard = ((SubscriberAccount)account).getSubscriberCards();
            if(listCard!=null && !listCard.isEmpty()){
                amount = listCard.get(0).getAmount();
            }
        }
        currentMoney = new JLabel(amount + " euros restants", JLabel.CENTER); // Obtenir l'information sur l'argent depuis la DAO

// Redimensionnement de l'icône pour le bouton basket
        ImageIcon basketIcon = new ImageIcon("src/ui/Images/panier.png");
        int iconWidth_2 = 30; // Largeur souhaitée
        int iconHeight_2 = 30; // Hauteur souhaitée
        Image scaledBasketImage = basketIcon.getImage().getScaledInstance(iconWidth_2, iconHeight_2, java.awt.Image.SCALE_SMOOTH);
        basketIcon = new ImageIcon(scaledBasketImage);
        basket = new JButton("Panier", basketIcon);

        rightMenu = new JButton(account.getUser().getFirstName()); // Gérer l'affichage du nom d'utilisateur

// Ajout des composants au panel
        this.add(leftMenu);
        this.add(currentLocations);
        this.add(currentMoney);
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
