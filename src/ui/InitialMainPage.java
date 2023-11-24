package ui;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;

/**
 * Classe définissant la page d'accueil initiale
 */
public class InitialMainPage extends BasePage {

    public InitialMainPage(JFrame frame) {
        super(frame);
        this.setLayout(new BorderLayout());
		this.setSize(frame.getSize());
		this.setLocation(0,0);

        //-------- BARRE DE NAVIGATION --------
        //Creation de la barre de navigation
        InitialNavbar navbar = new InitialNavbar();
        navbar.getSignInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller!=null){
                    controller.showLoginPage();
                }
            }
        });


        //-------- PAGE PRINCIPALE D AFFICHAGE DES FILMS --------
        //Creation de la page principale d'affichage des films sous forme de sections
        ArrayList<FilmSection> filmsSections = new ArrayList<FilmSection>();//Il faut trouver la liste des sections à mettre et des films correspondants dans la BD
        filmsSections.add(new FilmSection(new ArrayList<String>(), "Top 10 de la semaine", false));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Top 10 du mois", false));
        filmsSections.add(new FilmSection(new ArrayList<String>(),"Blu-ray disponibles", true));
        FilmsSectionsPane filmsSectionsPane = new FilmsSectionsPane(filmsSections);


        //-------- BARRE DES BOUTONS EN BAS DE PAGE --------
        BottomBar bottomBar = new BottomBar();
        
        //Ajout de tous les éléments à la fenêtre
        this.add(navbar, BorderLayout.NORTH);
        this.add(filmsSectionsPane, BorderLayout.CENTER);
        this.add(bottomBar, BorderLayout.SOUTH);
    }    
}
