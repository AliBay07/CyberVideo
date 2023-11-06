package ui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.*;

public class SubscriberMainPage extends JFrame {

    public static void main(String[] args) {

        JFrame externalFrame = new JFrame();
        externalFrame.setSize(1280, 720);
		externalFrame.setLocationRelativeTo(null);
		externalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel frame = new JPanel();
        frame.setLayout(new BorderLayout());
		frame.setSize(1280, 720);
		frame.setLocation(0,0);

        //-------- BARRE DE NAVIGATION --------
        //Creation de la barre de navigation
        SubscriberNavbar navbar = new SubscriberNavbar(0, 15);
        //Creation du menu gauche
        String[] tabFonctions1 = {"Voir mon historique de location", "Modifier mes préférences de location", "Filtrer les catégories", "Demander l'ajout d'un nouveau film"};
        JList<String> leftMenu = new JList<String>(tabFonctions1);
        leftMenu.setPreferredSize(new Dimension(200,400));
        leftMenu.setLayoutOrientation(JList.VERTICAL);
        ActionListener openLeftMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog leftMenuDialog = new JDialog(externalFrame, "Menu gauche", true);
                leftMenuDialog.setSize(250, 500);
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
                leftMenuDialog.setVisible(true);
            }
        };
        navbar.getLeftMenu().addActionListener(openLeftMenu);

        ActionListener openNewPanel = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel testPage = new SignupBasicInfoPane();
                frame.setVisible(false);
                testPage.setVisible(true);
                externalFrame.add(testPage);
            }
        };
        navbar.getRightMenu().addActionListener(openNewPanel);

        //-------- PAGE PRINCIPALE D AFFICHAGE DES FILMS --------
        //Creation de la page principale d'affichage des films sous forme de sections
        ArrayList<FilmSection> filmsSections = new ArrayList<FilmSection>();//Il faut trouver la liste des sections à mettre et des films correspondants dans la BD
        filmsSections.add(new FilmSection(new ArrayList<String>(), "Top 10 de la semaine", false));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Top 10 du mois", false));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Blu-ray disponibles", true));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Par catégorie", true));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Par réalisateur", true));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Par acteurs", true));
        FilmsSectionsPane filmsSectionsPane = new FilmsSectionsPane(filmsSections);


        //-------- BARRE DES BOUTONS EN BAS DE PAGE --------
        BottomBar bottomBar = new BottomBar();
        
        //Ajout de tous les éléments à la fenêtre
        frame.add(navbar, BorderLayout.NORTH);
        frame.add(filmsSectionsPane, BorderLayout.CENTER);
        frame.add(bottomBar, BorderLayout.SOUTH);

        frame.setVisible(true);
        externalFrame.add(frame);
        externalFrame.setVisible(true);
    }
}