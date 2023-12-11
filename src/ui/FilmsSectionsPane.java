package ui;

import javax.swing.*;

import beans.Category;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FilmsSectionsPane extends JPanel {
    private Controller controller;
    private HashMap<String, ArrayList<String>> criterias;
    private boolean research = false; //Il faudra changer ça par des enum ou autre pour faire la transition d'états !!!
    private JPanel researchElements;
    private JPanel showListsSections;
    private JPanel showListsSectionsInterior;
    private ArrayList<FilmSection> filmsSections;
    private JScrollPane sectionsScrollPane;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public FilmsSectionsPane(Controller c, ArrayList<FilmSection> sections) {
        controller = c;
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
                ArrayList<Category> categories = controller.getFacadeIHM().getAllCategories();
                String[] availableCategories = new String[categories.size()];
                for(int i=0; i<availableCategories.length; i++){
                    availableCategories[i] = categories.get(i).getCategoryName();
                }
                popUp.showPopUp(availableCategories);
                popUp.getValidationButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        criterias = popUp.getChosenCriterias();
                        popUp.setVisible(false);
                        popUp.dispose();
                        controller.traite(null, Keyword.SHOWADVANCEDRESEARCH);
                    }
                });
            }
        });
        JPanel researchByText = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 30));
        JTextField researchBar = new JTextField("Rechercher un film", 30);
        ImageIcon researchIcon = new ImageIcon("src/ui/Images/loupe.png");
    	int width = 50;
    	int height = 50;
    	researchIcon = new ImageIcon(researchIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        JButton launchResearch = new JButton(researchIcon);
        launchResearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                controller.setResearchedFilm(researchBar.getText());
                controller.traite(null, Keyword.SHOWTEXTRESEARCH);
            }
        });
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

    public ArrayList<FilmSection> getFilmSections() {
        return filmsSections;
    }

    public HashMap<String, ArrayList<String>> getCriterias(){
        return criterias;
    }

}
