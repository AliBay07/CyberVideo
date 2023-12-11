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
        this.setPreferredSize(new Dimension(FRAME_WIDTH,40)); //Mettre une constante pour avoir la taille de la fenetre ?
        leftMenu = new JButton("Menu", new ImageIcon("src/ui/Images/menu.png"));
        mainPageName = new JLabel("Cybervideo");
        mainPageName.setHorizontalAlignment(SwingConstants.CENTER);
        signIn = new JButton("Connexion"); //Faire une version nomUtilisateur avec une constante d'environnement pour vérifier si un utilisateur est connecté
        this.add(leftMenu, BorderLayout.WEST);
        this.add(mainPageName, BorderLayout.CENTER);
        this.add(signIn, BorderLayout.EAST);
    }

    public JButton getSignInButton(){
        return signIn;
    }
    
}
