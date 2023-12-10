package ui;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import beans.*;
//import facade.ui.*;
import java.awt.*;

public class MainPage extends BasePage {
    private JPanel navbar;
    private ArrayList<String> filmsBasket; //A modifier en ArrayList de Film
    private FilmsSectionsPane filmsSectionsPane;
    private BottomBar bottomBar;

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==filmsSectionsPane.getFilmSections().get(0).getMoreFilmsButton()){
                controller.traite(MainPage.this, Keyword.SHOWTOP10W); 
            }
            else if(e.getSource()==filmsSectionsPane.getFilmSections().get(1).getMoreFilmsButton()){
                controller.traite(MainPage.this, Keyword.SHOWTOP10M);
            }
            else if(e.getSource()==filmsSectionsPane.getFilmSections().get(2).getMoreFilmsButton()){
                controller.traite(MainPage.this, Keyword.SHOWBLURAY);
            }
            if(controller.getCurrentAccount() != null){
                if(e.getSource()==filmsSectionsPane.getFilmSections().get(3).getMoreFilmsButton()){
                    controller.traite(MainPage.this, Keyword.SHOWCATEGORIES); 
                    //Il faut changer ça car on veut avoir le nom de la catégorie selectionnée !
                }
                else if(e.getSource()==filmsSectionsPane.getFilmSections().get(4).getMoreFilmsButton()){
                    controller.traite(MainPage.this, Keyword.SHOWALLFILMS);
                }
            }
            if(controller.getCurrentAccount() == null){
                if(e.getSource()==((InitialNavbar)navbar).getSignInButton()){
                    controller.traite(MainPage.this, Keyword.SHOWLOGINPAGE);
                }
            }
            if(e.getSource()==bottomBar.getSubscribeButton()){
                controller.traite(MainPage.this, Keyword.SUBSCRIBE);
            }
            else if(e.getSource()==bottomBar.getButtonBluRay()){
                controller.traite(MainPage.this, Keyword.SHOWRETURNBLURAYPAGE);
            }
        }
    };


    public MainPage(JFrame frame, Controller c) {
        super(frame);
        this.setLayout(new BorderLayout());
        this.setController(c);

        //Création de films aléatoires
        filmsBasket = new ArrayList<String>();
        filmsBasket.add("Titanic");
        filmsBasket.add("Transformers");

        initView();
    }

    private ActionListener setRightMenu(String[] tabFonctions){
        JList<String> rightMenu = new JList<String>(tabFonctions);
        rightMenu.setPreferredSize(new Dimension(7*SysAL2000.DIALOG_WIDTH/8, 7*SysAL2000.DIALOG_HEIGHT/8));
        rightMenu.setLayoutOrientation(JList.VERTICAL);
        rightMenu.setFont(new Font("serif", Font.PLAIN, 15));
        ActionListener openRightMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog rightMenuDialog = new JDialog(frame, "Menu gauche", true);
                rightMenuDialog.setSize(SysAL2000.DIALOG_WIDTH,SysAL2000.DIALOG_HEIGHT);
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
                rightMenuDialog.setLocationRelativeTo(MainPage.this);
                rightMenuDialog.setVisible(true);
            }
        };
        return openRightMenu;
    }

    private ActionListener setLeftMenu(){
        String[] tabFonctions1 = {"Voir mon historique de location", "Modifier mes préférences de location", "Filtrer les catégories", "Demander l'ajout d'un nouveau film"};
        JList<String> leftMenu = new JList<String>(tabFonctions1);
        leftMenu.setFont(new Font("serif", Font.PLAIN, 15));
        leftMenu.setPreferredSize(new Dimension(7*SysAL2000.DIALOG_WIDTH/8, 7*SysAL2000.DIALOG_HEIGHT/8));
        leftMenu.setLayoutOrientation(JList.VERTICAL);
        ActionListener openLeftMenu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog leftMenuDialog = new JDialog(frame, "Menu gauche", true);
                leftMenuDialog.setSize(SysAL2000.DIALOG_WIDTH,SysAL2000.DIALOG_HEIGHT);
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
                leftMenuDialog.setLocationRelativeTo(MainPage.this);
                leftMenuDialog.setVisible(true);
            }
        };
        return openLeftMenu;
    }

    private ActionListener setBasketListener(){
        ActionListener basketListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Basket b = new Basket(filmsBasket);
                b.showBasket();
                b.setVisible(true);
            }
        };
        return basketListener;
    }

    private void initNavbar(){
        if(controller.getCurrentAccount() == null){
            navbar = new InitialNavbar();
            ((InitialNavbar)navbar).getSignInButton().addActionListener(actionListener);
        }
        else if(controller.getCurrentAccount() instanceof NormalAccount){
            navbar = new NormalNavbar(1);//controller.getCurrentAccount(). Il faut une méthode pour obtenir le nb de locations en cours
            String[] tabFonctions1 = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "S'abonner", "Se deconnecter"};
            ((NormalNavbar) navbar).getRightMenu().addActionListener(setRightMenu(tabFonctions1));
            ((NormalNavbar) navbar).getBasket().addActionListener(setBasketListener());
            
        }
        else{
            navbar = new SubscriberNavbar(1, 15);//Obtenir ou calculer le nbLocations en cours et le montant sur la carte abonné
            ((SubscriberNavbar) navbar).getLeftMenu().addActionListener(setLeftMenu());
            String[] tabFonctions2 = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "Commander une carte abonné", "Arreter mon abonnement", "Se deconnecter"};
            ((SubscriberNavbar) navbar).getRightMenu().addActionListener(setRightMenu(tabFonctions2));
            ((SubscriberNavbar) navbar).getBasket().addActionListener(setBasketListener());
        }
    }

    private void initFilmsSectionsPane(){
        ArrayList<FilmSection> filmsSections = new ArrayList<FilmSection>(); 
        //Il faut avoir les infos sur les films via la BD et l'itérateur de la machine
        if(controller.getCurrentAccount() == null){
            filmsSections.add(new FilmSection(new ArrayList<String>(), "Top 10 de la semaine", false));
            filmsSections.add(new FilmSection(new ArrayList<String>(),"Top 10 du mois", false));
            filmsSections.add(new FilmSection(new ArrayList<String>(),"Blu-ray disponibles", true));
        }
        else{
            filmsSections.add(new FilmSection(new ArrayList<String>(), "Top 10 de la semaine", false));
            filmsSections.add(new FilmSection(new ArrayList<String>(),"Top 10 du mois", false));
            filmsSections.add(new FilmSection(new ArrayList<String>(),"Blu-ray disponibles", true));
            filmsSections.add(new FilmSection(new ArrayList<String>(),"Par catégorie", true));
            filmsSections.add(new FilmSection(new ArrayList<String>(),"Tous les films", true));
        }
        for(int i=2; i < filmsSections.size(); i++){
            filmsSections.get(i).getMoreFilmsButton().addActionListener(actionListener);
        }
        filmsSectionsPane = new FilmsSectionsPane(controller, filmsSections);
    }

    private void initBottomBar(){
        if(controller.getCurrentAccount() == null){
            bottomBar = new BottomBar(false);
            
        }
        else if(controller.getCurrentAccount() instanceof SubscriberAccount){
            bottomBar = new BottomBar(true);
            
        }
        else{
            bottomBar = new BottomBar(false);
        }
        bottomBar.getSubscribeButton().addActionListener(actionListener);
        bottomBar.getButtonBluRay().addActionListener(actionListener);
    }

    private void initView(){
        this.setLocation(0,0);
        this.setSize(frame.getSize());

        initNavbar();
        initFilmsSectionsPane();
        initBottomBar();

        this.add(navbar, BorderLayout.NORTH);
        this.add(filmsSectionsPane, BorderLayout.CENTER);
        this.add(bottomBar, BorderLayout.SOUTH);
    }

    public HashMap<String, ArrayList<String>> getChosenCriterias(){
        return filmsSectionsPane.getCriterias();
    }
}