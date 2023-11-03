package ui;

import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

import java.awt.*;


public class InitialMainPage {

    public static void main(String[] args) {
        JFrame frame = new JFrame("InitialMainPage");
        frame.setLayout(new BorderLayout());
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-------- BARRE DE NAVIGATION --------
        //Creation de la barre de navigation
        JPanel navbar = new JPanel();
        navbar.setLayout(new BorderLayout());
        JButton leftMenu = new JButton("trois barres");
        JLabel mainPageName = new JLabel("Cybervideo");
        mainPageName.setHorizontalAlignment(SwingConstants.CENTER);
        JButton signIn = new JButton("Connexion");
        navbar.add(leftMenu, BorderLayout.WEST);
        navbar.add(mainPageName, BorderLayout.CENTER);
        navbar.add(signIn, BorderLayout.EAST);


        //-------- PAGE PRINCIPALE D AFFICHAGE DES FILMS --------
        //Creation de la page principale d'affichage des films
        JPanel listFilm = new JPanel();
        listFilm.setLayout(new BoxLayout(listFilm,BoxLayout.Y_AXIS));

        //Panel avec éléments pour lancer une recherche spécifique
        JPanel researchElements = new JPanel();
        JButton advancedResearch = new JButton("Recherche avancée");
        JTextField researchBar = new JTextField("Rechercher un film");
        ImageIcon researchIcon = new ImageIcon("");
        JButton launchResearch = new JButton("loupe",researchIcon);
        researchElements.add(advancedResearch);
        researchElements.add(researchBar);
        researchElements.add(launchResearch);

        //Panel d'affichage des catégories proposées aux utilisateurs
        JPanel showListsFilms = new JPanel(new BorderLayout());
        JPanel showListsFilmsInterior = new JPanel();
        showListsFilmsInterior.setLayout(new BoxLayout(showListsFilmsInterior, BoxLayout.Y_AXIS));
        
        //Panel pour le Top 10 de la semaine
        JPanel top10WeekPanel = new JPanel();
        top10WeekPanel.setLayout(new BoxLayout(top10WeekPanel, BoxLayout.Y_AXIS));
        JLabel top10WeekTitle = new JLabel("Top 10 de la semaine");
        top10WeekTitle.setHorizontalAlignment(SwingConstants.LEFT);
        JPanel top10WeekFilms = new JPanel(new BorderLayout());
        JButton leftArrowWeek = new JButton("<--");
        JPanel top10WeekFilmsInterior = new JPanel();
        for (int i=0; i<10; i++){
            //Il faut récupérer les infos depuis la BDD
            JButton film = new JButton("nomFilm "+(i+1));
            top10WeekFilmsInterior.add(film);
        }
        JButton rightArrowWeek = new JButton("-->");
        JScrollPane showTop10Week = new JScrollPane(top10WeekFilmsInterior);
        top10WeekFilms.add(leftArrowWeek, BorderLayout.WEST);
        top10WeekFilms.add(showTop10Week, BorderLayout.CENTER);
        top10WeekFilms.add(rightArrowWeek, BorderLayout.EAST);
        top10WeekPanel.add(top10WeekTitle);
        top10WeekPanel.add(top10WeekFilms);
        showListsFilmsInterior.add(top10WeekPanel);
        
        //Panel pour le Top 10 du mois
        JPanel top10MonthPanel = new JPanel();
        top10MonthPanel.setLayout(new BoxLayout(top10MonthPanel, BoxLayout.Y_AXIS));
        JLabel top10MonthTitle = new JLabel("Top 10 du mois");
        top10MonthTitle.setHorizontalAlignment(SwingConstants.LEFT);
        JPanel top10MonthFilms = new JPanel(new BorderLayout());
        JButton leftArrow = new JButton("<--");
        JPanel top10MonthFilmsInterior = new JPanel();
        for (int i=0; i<10; i++){
            //Il faut récupérer les infos depuis la BDD
            JButton film = new JButton("nomFilm "+(i+1));
            top10MonthFilmsInterior.add(film);
        }
        JButton rightArrow = new JButton("-->");
        JScrollPane showTop10Month = new JScrollPane(top10MonthFilmsInterior);
        top10MonthFilms.add(leftArrow, BorderLayout.WEST);
        top10MonthFilms.add(showTop10Month, BorderLayout.CENTER);
        top10MonthFilms.add(rightArrow, BorderLayout.EAST);
        top10MonthPanel.add(top10MonthTitle);
        top10MonthPanel.add(top10MonthFilms);
        showListsFilmsInterior.add(Box.createRigidArea(new Dimension(0,10)));
        showListsFilmsInterior.add(top10MonthPanel);

        //Panel pour les Blu-ray disponibles
        JPanel bluRayPanel = new JPanel();
        bluRayPanel.setLayout(new BoxLayout(bluRayPanel, BoxLayout.Y_AXIS));
        JLabel bluRayTitle = new JLabel("Blu-ray disponibles");
        bluRayTitle.setHorizontalAlignment(SwingConstants.LEFT);
        JPanel bluRayFilms = new JPanel(new BorderLayout());
        JButton leftArrowBluRay = new JButton("<--");
        JPanel bluRayFilmsInterior = new JPanel();
        for (int i=0; i<10; i++){
            //Il faut récupérer les infos depuis la BDD
            JButton film = new JButton("nomFilm "+(i+1));
            bluRayFilmsInterior.add(film);
        }
        JButton rightArrowBluRay = new JButton("-->");
        JScrollPane showBluRay = new JScrollPane(bluRayFilmsInterior);
        bluRayFilms.add(leftArrowBluRay, BorderLayout.WEST);
        bluRayFilms.add(showBluRay, BorderLayout.CENTER);
        bluRayFilms.add(rightArrowBluRay, BorderLayout.EAST);
        bluRayPanel.add(bluRayTitle);
        bluRayPanel.add(bluRayFilms);
        showListsFilmsInterior.add(Box.createRigidArea(new Dimension(0,10)));
        showListsFilmsInterior.add(bluRayPanel);

        //Création du panel scrollable et insertion dans la page d'affichage des catégories
        JScrollPane showFilms = new JScrollPane(showListsFilmsInterior);
        showListsFilms.add(showListsFilmsInterior, BorderLayout.CENTER);
        showListsFilms.add(showFilms.createVerticalScrollBar(),BorderLayout.EAST);
        listFilm.add(researchElements);
        listFilm.add(showListsFilms);


        //-------- BARRE DES BOUTONS EN BAS DE PAGE --------
        JPanel bottomBar = new JPanel();
        JButton suscribe = new JButton("S'abonner");
        JButton returnBluRay = new JButton("Rendre un blu-ray");
        JButton help = new JButton("Aide");
        bottomBar.add(suscribe);
        bottomBar.add(returnBluRay);
        bottomBar.add(help);
        
        //Ajout de tous les éléments à la fenêtre
        frame.add(navbar, BorderLayout.NORTH);
        frame.add(listFilm, BorderLayout.CENTER);
        frame.add(bottomBar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
}
