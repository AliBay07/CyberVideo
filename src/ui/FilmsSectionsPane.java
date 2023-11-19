package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FilmsSectionsPane extends JPanel {
    private boolean research = false; //Il faudra changer ça par des enum ou autre pour faire la transition d'états !!!
    private JPanel researchElements;
    private JPanel showListsSections;
    private JPanel showListsSectionsInterior;
    private ArrayList<FilmSection> filmsSections;
    private JScrollPane sectionsScrollPane;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public FilmsSectionsPane(ArrayList<FilmSection> sections) {
        filmsSections = sections;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        //Panel avec éléments pour lancer une recherche spécifique
        this.setResearchPanel();

        //Panel d'affichage des catégories proposées aux utilisateurs
        this.setListsSectionPanel();
        this.add(researchElements);
        this.add(showListsSections);
    }

    private void setResearchPanel(){
        researchElements = new JPanel();
        researchElements.setPreferredSize(new Dimension(FRAME_WIDTH, 80)); //Mettre des constantes aussi !
        researchElements.setLayout(new FlowLayout(FlowLayout.CENTER, 70, this.getHeight()/6));
        JButton advancedResearch = new JButton("Recherche avancée");
        advancedResearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdvancedResearchPopUp popUp = new AdvancedResearchPopUp();
                //!!!! Catgories qui vont être récupérées depuis la facade !
                String[] availableCategories = new String[10];
                for(int i=0; i<10; i++){
                    availableCategories[i] = "Categorie " + i;
                }
                popUp.showPopUp(availableCategories);
                popUp.getValidationButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        research = true;
                    }
                });
            }
        });
        JPanel researchByText = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 30));
        JTextField researchBar = new JTextField("Rechercher un film", 30);
        ImageIcon researchIcon = new ImageIcon("");
        JButton launchResearch = new JButton("loupe",researchIcon);
        researchByText.add(researchBar);
        researchByText.add(launchResearch);
        researchElements.add(advancedResearch);
        researchElements.add(researchByText);
    }

    private void setListsSectionPanel(){
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
    }

    public boolean getValidationResearchButtonSelection(){
        return research;
    }

}
