package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FilmsSectionsPane extends JPanel {
    private JPanel researchElements;
    private JPanel showListsSections;
    private JPanel showListsSectionsInterior;
    private ArrayList<FilmSection> filmsSections;
    private JScrollPane sectionsScrollPane;

    public FilmsSectionsPane(ArrayList<FilmSection> sections) {
        filmsSections = sections;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //Panel avec éléments pour lancer une recherche spécifique
        researchElements = new JPanel();
        researchElements.setPreferredSize(new Dimension(1280, 80)); //Mettre des constantes aussi !
        researchElements.setLayout(new FlowLayout(FlowLayout.CENTER, 70, this.getHeight()/6));
        JButton advancedResearch = new JButton("Recherche avancée");
        JPanel researchByText = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 30));
        JTextField researchBar = new JTextField("Rechercher un film", 30);
        ImageIcon researchIcon = new ImageIcon("");
        JButton launchResearch = new JButton("loupe",researchIcon);
        researchByText.add(researchBar);
        researchByText.add(launchResearch);
        researchElements.add(advancedResearch);
        researchElements.add(researchByText);

        //Panel d'affichage des catégories proposées aux utilisateurs
        showListsSections = new JPanel(new BorderLayout());
        showListsSectionsInterior = new JPanel();
        showListsSectionsInterior.setLayout(new BoxLayout(showListsSectionsInterior, BoxLayout.Y_AXIS));
        
        for(FilmSection s : filmsSections) {
            showListsSectionsInterior.add(Box.createRigidArea(new Dimension(0,30)));
            showListsSectionsInterior.add(s);
        }
        showListsSectionsInterior.add(Box.createRigidArea(new Dimension(0,30)));

        //Création du panel scrollable et insertion dans la page d'affichage des catégories
        sectionsScrollPane = new JScrollPane(showListsSectionsInterior, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        showListsSections.add(sectionsScrollPane, BorderLayout.CENTER);
        this.add(researchElements);
        this.add(showListsSections);
    }

}
