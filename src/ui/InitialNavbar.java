package ui;

import javax.swing.*;
import java.awt.*;

public class InitialNavbar extends JPanel {
    private JButton leftMenu;
    private JLabel mainPageName;
    private JButton signIn;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public InitialNavbar(){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); // Utiliser une constante pour la taille de la fenêtre est une bonne idée

        // Redimensionnement de l'icône pour le bouton leftMenu
        ImageIcon menuIcon = new ImageIcon("src/ui/Images/menu.png");
        int iconWidth = 30; // Largeur souhaitée
        int iconHeight = 30; // Hauteur souhaitée
        Image scaledMenuImage = menuIcon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
        menuIcon = new ImageIcon(scaledMenuImage);
        leftMenu = new JButton("Menu", menuIcon);

        mainPageName = new JLabel("Cybervideo");
        mainPageName.setHorizontalAlignment(SwingConstants.CENTER);

        signIn = new JButton("Connexion"); // Adapter pour afficher le nom d'utilisateur si un utilisateur est connecté

        // Ajout des composants au panel
        this.add(leftMenu, BorderLayout.WEST);
        this.add(mainPageName, BorderLayout.CENTER);
        this.add(signIn, BorderLayout.EAST);

    }

    public JButton getSignInButton(){
        return signIn;
    }
    
}
