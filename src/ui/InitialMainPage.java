package ui;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;


public class InitialMainPage {

    public static void main(String[] args) {
        JFrame externalFrame = new JFrame();
        externalFrame.setSize(1280, 720);
		externalFrame.setLocationRelativeTo(null);
		externalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JDesktopPane desktop = new JDesktopPane();

        JInternalFrame frame = new JInternalFrame("InitialMainPage", true, false, true);
        frame.setLayout(new BorderLayout());
		frame.setSize(1280, 720);
		frame.setLocation(0,0);

        //-------- BARRE DE NAVIGATION --------
        //Creation de la barre de navigation
        InitialNavbar navbar = new InitialNavbar();


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
        frame.add(navbar, BorderLayout.NORTH);
        frame.add(filmsSectionsPane, BorderLayout.CENTER);
        frame.add(bottomBar, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.show();
        desktop.add(frame);
        externalFrame.add(desktop);
        externalFrame.setVisible(true);
    }
    
}
