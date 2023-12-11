package ui;

import javax.swing.*;
import java.awt.*;
import beans.*;

public class SubscriberNavbar extends JPanel {
    private JButton leftMenu;
    private JLabel currentLocations;
    private JLabel currentMoney;
    private JButton basket;
    private JButton rightMenu;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public SubscriberNavbar(Account account){
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        this.setLayout(new GridLayout(1, 5));
        //this.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 0));
        leftMenu = new JButton("trois barres");
        currentLocations = new JLabel(3-account.getNbAllowedReservation() + " locations en cours",JLabel.CENTER); //Avoir le nombre de location avec la BD
        currentMoney = new JLabel(((SubscriberAccount)account).getSubscriberCards().get(0).getAmount() + " euros restants", JLabel.CENTER); //Il faut avoir l'information sur l'argent depuis la DAO !
        basket = new JButton("Panier");
        rightMenu = new JButton(account.getUser().getFirstName());
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
