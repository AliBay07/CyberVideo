package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdvancedResearchPopUp extends JDialog {
    private DefaultListModel<String> categories;
    private DefaultListModel<String> autors;
    private DefaultListModel<String> actors;
    private String[] availableCategories;
    private NavigationBar navbar;
    private JPanel categoriesChoice;
    private JPanel autorsChoice;
    private JPanel actorsChoice;
    private JButton validation;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int DIALOG_WIDTH = (int) (dimension.getWidth()/3)*2;
    public static int DIALOG_HEIGHT = (int) (dimension.getHeight()/3)*2;

    public AdvancedResearchPopUp(){
        //Initialisation des attributs de la pop-up
        categories = new DefaultListModel<String>();
        autors = new DefaultListModel<String>();
        actors = new DefaultListModel<String>();
        categoriesChoice = new JPanel();
        autorsChoice = new JPanel();
        actorsChoice = new JPanel();
        GridLayout gl = new GridLayout(3,1);
        categoriesChoice.setLayout(gl);
        autorsChoice.setLayout(gl);
        actorsChoice.setLayout(gl);
        categoriesChoice.setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT/5));
        autorsChoice.setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT/5));
        actorsChoice.setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT/5));
    }

    //Crée la navbar de la pop-uo
    private void setNavbar(){
        JButton backBtn = new JButton("<--");
        JButton helpBtn = new JButton("Aide");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                AdvancedResearchPopUp.this.setVisible(false);
            }
        });
        navbar = new NavigationBar("Recherche avancée", backBtn, helpBtn);
        navbar.setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT/8));
        this.add(navbar);
    }

    //Crée le panel dédié au choix de la catégorie
    private void setCategoriesChoicePanel(String[] availableCategories){
        //Panel dédié à la liste déroulante et le bouton d'ajout
        JPanel chooseCategorie = new JPanel(new BorderLayout());
        JComboBox<String> categoriesChoiceBox = new JComboBox<String>(availableCategories);
        JButton addButton = new JButton("Ajouter");
        chooseCategorie.add(categoriesChoiceBox, BorderLayout.CENTER);
        chooseCategorie.add(addButton, BorderLayout.EAST);
        //Panel dédié à l'affichage des catégories selectionnées et son bouton d'ajout
        JPanel chosenCategoriesPanel = new JPanel(new BorderLayout());
        JList<String> chosenCategories = new JList<String>(categories);
        chosenCategories.setVisibleRowCount(1);
        chosenCategories.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        chosenCategories.setBackground(Color.LIGHT_GRAY);
        JButton deleteSelection = new JButton("Supprimer");
        if(categories.size() == 0)
            deleteSelection.setEnabled(false);
        chosenCategoriesPanel.add(chosenCategories, BorderLayout.CENTER);
        chosenCategoriesPanel.add(deleteSelection, BorderLayout.EAST);
        //Ajout de tous les éléments au panel d'affichage du choix de catégorie
        categoriesChoice.add(new JLabel("Choisir par catégorie"));
        categoriesChoice.add(chooseCategorie);
        categoriesChoice.add(chosenCategoriesPanel);
        addButton.addActionListener(this.addFilteredCategory(categoriesChoiceBox, chosenCategories));
        deleteSelection.addActionListener(this.deleteFilter(chosenCategories));
        //Ajout du panel dans le JDialog principal
        this.add(categoriesChoice);
    }

    //Crée le panel dédié au choix des réalisateurs
    private void setAutorsChoicePanel(){
        //Panel dédié au champ texte et le bouton d'ajout
        JPanel chooseAutor = new JPanel(new BorderLayout());
        JTextField autorField = new JTextField();
        JButton addButton = new JButton("Ajouter");
        chooseAutor.add(autorField, BorderLayout.CENTER);
        chooseAutor.add(addButton, BorderLayout.EAST);
        //Panel dédié à l'affichage des réalisateurs selectionnés et son bouton d'ajout 
        JPanel chosenAutorsPanel = new JPanel(new BorderLayout());
        JList<String> chosenAutors = new JList<String>(autors);
        chosenAutors.setVisibleRowCount(1);
        chosenAutors.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        chosenAutors.setBackground(Color.LIGHT_GRAY);
        JButton deleteSelection = new JButton("Supprimer");
        if(autors.size() == 0)
            deleteSelection.setEnabled(false);
        chosenAutorsPanel.add(chosenAutors, BorderLayout.CENTER);
        chosenAutorsPanel.add(deleteSelection, BorderLayout.EAST);
        //Ajout de tous les éléments au panel d'affichage du choix de réalisateur
        autorsChoice.add(new JLabel("Choisir par réalisateurs"));
        autorsChoice.add(chooseAutor);
        autorsChoice.add(chosenAutorsPanel);
        addButton.addActionListener(this.addFilter(chooseAutor, chosenAutors));
        deleteSelection.addActionListener(this.deleteFilter(chosenAutors));
        this.add(autorsChoice);
    }

    //Crée le panel dédié au choix des acteurs
    private void setActorsChoicePanel(){
        //Panel dédié au champ texte et le bouton d'ajout
        JTextField actorField = new JTextField();
        JPanel chooseActor = new JPanel(new BorderLayout());
        JButton addButton = new JButton("Ajouter");
        chooseActor.add(actorField, BorderLayout.CENTER);
        chooseActor.add(addButton, BorderLayout.EAST);
        //Panel dédié à l'affichage des acteurs selectionnés et son bouton d'ajout 
        JPanel chosenActorsPanel = new JPanel(new BorderLayout());
        JList<String> chosenActors = new JList<String>(actors);
        chosenActors.setVisibleRowCount(1);
        chosenActors.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        chosenActors.setBackground(Color.LIGHT_GRAY);
        JButton deleteSelection = new JButton("Supprimer");
        if(autors.size() == 0)
            deleteSelection.setEnabled(false);
        chosenActorsPanel.add(chosenActors, BorderLayout.CENTER);
        chosenActorsPanel.add(deleteSelection, BorderLayout.EAST);
        //Ajout de tous les éléments au panel d'affichage du choix d'acteurs
        actorsChoice.add(new JLabel("Choisir par acteurs"));
        actorsChoice.add(chooseActor);
        actorsChoice.add(chosenActorsPanel);
        addButton.addActionListener(this.addFilter(chooseActor, chosenActors));
        deleteSelection.addActionListener(this.deleteFilter(chosenActors));
        this.add(actorsChoice);
    }

    //Crée le bouton pour valider la selection
    private void setValidationButton() {
        validation = new JButton("Valider la sélection");
        this.add(validation);
    }

    //Ajoute tous les panels à la pop-up, la paramètre et l'affiche à l'écran
    public void showPopUp(String[] avCategories){
        availableCategories = avCategories;
        this.setLayout(new GridLayout(5, 1));
        this.setNavbar();
        this.setCategoriesChoicePanel(availableCategories);
        this.setAutorsChoicePanel();
        this.setActorsChoicePanel();
        this.setValidationButton();
        this.setSize(Test.DIALOG_WIDTH, Test.DIALOG_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //Crée le listener permettant d'ajouter un filtre de recherche pour un réalisateur ou acteur
    public ActionListener addFilter(JPanel buttons, JList<String> results){
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtEntry = ((JTextField) buttons.getComponent(0)).getText();
                if(!txtEntry.equals("") && txtEntry != null){
                    if(buttons.getParent().equals(autorsChoice)){
                        if(!autors.contains(txtEntry))
                            autors.addElement(txtEntry);
                        if(autors.size()>0)
                            results.getParent().getComponent(1).setEnabled(true);
                    }
                    else if((buttons.getParent()).equals(actorsChoice)){
                        if(!actors.contains(txtEntry))
                            actors.addElement(txtEntry);
                        if(actors.size()>0)
                            results.getParent().getComponent(1).setEnabled(true);
                    }
                    ((JTextField) buttons.getComponent(0)).setText("");
                    results.repaint();
                }
            }
        };
        return a;
    }

    //Crée le listener permettant d'ajouter une nouvelle catégorie comme filtre de recherche
    public ActionListener addFilteredCategory(JComboBox<String> comboBox,JList<String> resultsList){
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtEntry = comboBox.getSelectedItem().toString();
                if(!categories.contains(txtEntry))
                    categories.addElement(txtEntry);
                if(categories.size()>0)
                    resultsList.getParent().getComponent(1).setEnabled(true);
                resultsList.repaint();
            }
        };
        return a;
    }

    public ActionListener deleteFilter(JList<String> results){
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(results.getParent().getParent() == categoriesChoice)
                    categories.remove(results.getSelectedIndex());
                else if(results.getParent().getParent() == actorsChoice)
                    actors.remove(results.getSelectedIndex());
                else if(results.getParent().getParent() == autorsChoice)
                    autors.remove(results.getSelectedIndex());
                results.repaint();
            }
        };
        return a;
    }

    public JButton getValidationButton(){
        return validation;
    }
}