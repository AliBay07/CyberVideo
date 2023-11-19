package ui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NormalMainPage extends JPanel {
    private NormalNavbar navbar;
    private FilmsSectionsPane filmsSectionsPane;
    private BottomBar bottomBar;
    //Code dans mon Test.java
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int DIALOG_WIDTH = (int) (dimension.getWidth()/4);
    public static int DIALOG_HEIGHT = (int) (dimension.getHeight()*2)/3;

    public NormalMainPage(JFrame frame){
        this.setLayout(new BorderLayout());
		this.setSize(frame.getSize());
		this.setLocation(0,0);

        //-------- BARRE DE NAVIGATION --------
        //Creation de la barre de navigation
        navbar = new NormalNavbar(0);
        //Creation du menu gauche
        this.setLeftMenu(frame);
        //Creation du menu droit
        this.setRightMenu(frame);
        //Apparition de la pop-up du panier
        ArrayList<String> films = new ArrayList<String>();
        films.add("Titanic");
        films.add("Transformers");
        navbar.getBasket().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Basket b = new Basket(films);
                b.showBasket();
                b.setVisible(true);
            }
        });

        //-------- PAGE PRINCIPALE D AFFICHAGE DES FILMS --------
        //Creation de la page principale d'affichage des films sous forme de sections
        ArrayList<FilmSection> filmsSections = new ArrayList<FilmSection>();//Il faut trouver la liste des sections à mettre et des films correspondants dans la BD
        filmsSections.add(new FilmSection(new ArrayList<String>(), "Top 10 de la semaine", false));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Top 10 du mois", false));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Blu-ray disponibles", true));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Par catégorie", true));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Tous les films", true));
        filmsSectionsPane = new FilmsSectionsPane(filmsSections);

        //-------- BARRE DES BOUTONS EN BAS DE PAGE --------
        bottomBar = new BottomBar();
        
        //Ajout de tous les éléments à la fenêtre
        this.add(navbar, BorderLayout.NORTH);
        this.add(filmsSectionsPane, BorderLayout.CENTER);
        this.add(bottomBar, BorderLayout.SOUTH);
    }

    private void setLeftMenu(JFrame frame){
        String[] tabFonctions1 = {"Voir mon historique de location", "Modifier mes préférences de location", "Filtrer les catégories", "Demander l'ajout d'un nouveau film"};
        JList<String> leftMenu = new JList<String>(tabFonctions1);
        leftMenu.setFont(new Font("serif", Font.PLAIN, 15));
        leftMenu.setPreferredSize(new Dimension(7*DIALOG_WIDTH/8, 7*DIALOG_HEIGHT/8));
        leftMenu.setLayoutOrientation(JList.VERTICAL);
        ActionListener openLeftMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog leftMenuDialog = new JDialog(frame, "Menu gauche", true);
                leftMenuDialog.setSize(DIALOG_WIDTH,DIALOG_HEIGHT);
                leftMenuDialog.setLayout(new FlowLayout());
                leftMenuDialog.add(leftMenu);
                JButton closeButton = new JButton("Close");
                leftMenuDialog.add(closeButton);
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        leftMenuDialog.dispose();
                    }
                });
                leftMenuDialog.setLocationRelativeTo(NormalMainPage.this);
                leftMenuDialog.setVisible(true);
            }
        };
        navbar.getLeftMenu().addActionListener(openLeftMenu);
    }

    private void setRightMenu(JFrame frame){
        String[] tabFonctions2 = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "Commander une carte abonné", "Arreter mon abonnement", "Se deconnecter"};
        JList<String> rightMenu = new JList<String>(tabFonctions2);
        rightMenu.setPreferredSize(new Dimension(7*DIALOG_WIDTH/8, 7*DIALOG_HEIGHT/8));
        rightMenu.setLayoutOrientation(JList.VERTICAL);
        ActionListener openRightMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog rightMenuDialog = new JDialog(frame, "Menu gauche", true);
                rightMenuDialog.setSize(DIALOG_WIDTH,DIALOG_HEIGHT);
                rightMenu.setFont(new Font("serif", Font.PLAIN, 15));
                rightMenuDialog.setLayout(new FlowLayout());
                rightMenuDialog.add(rightMenu);
                JButton closeButton = new JButton("Close");
                rightMenuDialog.add(closeButton);
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rightMenuDialog.dispose();
                    }
                });
                rightMenuDialog.setLocationRelativeTo(NormalMainPage.this);
                rightMenuDialog.setVisible(true);
            }
        };
        navbar.getRightMenu().addActionListener(openRightMenu);
    }

    public NormalNavbar getNavbar() {
        return navbar;
    }

    public BottomBar getBottomBar(){
        return bottomBar;
    }

    public FilmsSectionsPane getFilmsSectionsPane(){
        return filmsSectionsPane;
    }
    
}
