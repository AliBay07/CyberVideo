package ui;

import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import beans.*;
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
                if(e.getSource()==filmsSectionsPane.getFilmSections().get(4).getMoreFilmsButton()){
                    controller.traite(MainPage.this, Keyword.SHOWCATEGORIES); 
                    //Il faut changer ça car on veut avoir le nom de la catégorie selectionnée !
                }
                else if(e.getSource()==filmsSectionsPane.getFilmSections().get(3).getMoreFilmsButton()){
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

//    private ActionListener setRightMenu(String[] tabFonctions, ActionListener actionListener){
////        JList<String> rightMenu = new JList<String>(tabFonctions);
////        rightMenu.setPreferredSize(new Dimension(7*SysAL2000.DIALOG_WIDTH/8, 7*SysAL2000.DIALOG_HEIGHT/8));
////        rightMenu.setLayoutOrientation(JList.VERTICAL);
////        rightMenu.setFont(new Font("serif", Font.PLAIN, 15));
//
//        //
//        JPanel menuPane = new JPanel();
//        menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
//        for(String optionName : tabFonctions){
//            JButton optionBtn = new JButton(optionName);
//            optionBtn.setActionCommand(optionName);
//            optionBtn.addActionListener(actionListener);
//            optionBtn.setAlignmentX(CENTER_ALIGNMENT);
//            // interval
//            menuPane.add(Box.createRigidArea(new Dimension(10, 10)));
//            menuPane.add(optionBtn);
//        }
//
//        ActionListener openRightMenu = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JDialog rightMenuDialog = new JDialog(frame, "Menu droit", true);
//                rightMenuDialog.setSize(SysAL2000.DIALOG_WIDTH/3,SysAL2000.DIALOG_HEIGHT/3);
//                rightMenuDialog.setLayout(new BorderLayout());
//                rightMenuDialog.add(menuPane, BorderLayout.CENTER);
//                JButton closeButton = new JButton("Close");
//                rightMenuDialog.add(closeButton, BorderLayout.SOUTH);
//                closeButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        rightMenuDialog.dispose();
//                    }
//                });
//                rightMenuDialog.setLocationRelativeTo(null);
//                rightMenuDialog.setVisible(true);
//            }
//        };
//
//        return openRightMenu;
//    }

//    private ActionListener setLeftMenu(){
//        String[] tabFonctions1 = {"Voir mon historique de location", "Modifier mes préférences de location", "Filtrer les catégories", "Demander l'ajout d'un nouveau film"};
//        JList<String> leftMenu = new JList<String>(tabFonctions1);
//        leftMenu.setFont(new Font("serif", Font.PLAIN, 15));
//        leftMenu.setPreferredSize(new Dimension(7*SysAL2000.DIALOG_WIDTH/8, 7*SysAL2000.DIALOG_HEIGHT/8));
//        leftMenu.setLayoutOrientation(JList.VERTICAL);
//        ActionListener openLeftMenu = new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JDialog leftMenuDialog = new JDialog(frame, "Menu gauche", true);
//                leftMenuDialog.setSize(SysAL2000.DIALOG_WIDTH,SysAL2000.DIALOG_HEIGHT);
//                leftMenuDialog.setLayout(new FlowLayout());
//                leftMenuDialog.add(leftMenu);
//                JButton closeButton = new JButton("Close");
//                leftMenuDialog.add(closeButton);
//                closeButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        leftMenuDialog.dispose();
//                    }
//                });
//                leftMenuDialog.setLocationRelativeTo(MainPage.this);
//                leftMenuDialog.setVisible(true);
//            }
//        };
//        return openLeftMenu;
//    }

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
            navbar = new NormalNavbar(controller.getCurrentAccount(), controller.getFacadeIHM().getCurrentReservationsByAccount().size());//controller.getCurrentAccount(). Il faut une méthode pour obtenir le nb de locations en cours
            ((NormalNavbar) navbar).getRightMenu().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // show menu dialog
                    JDialog menuDialog = createMenuDroitNormal();
                    menuDialog.setVisible(true);
                }
            });
            ((NormalNavbar) navbar).getBasket().addActionListener(setBasketListener());
        } else {
            navbar = new SubscriberNavbar(controller.getCurrentAccount(), controller.getFacadeIHM().getCurrentReservationsByAccount().size());//Obtenir ou calculer le nbLocations en cours et le montant sur la carte abonné
            ((SubscriberNavbar) navbar).getLeftMenu().addActionListener(/*setLeftMenu()*/new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // show menu dialog
                    JDialog menuDialog = createMenuGaucheSubscribe();
                    menuDialog.setVisible(true);
                }
            });
            ((SubscriberNavbar) navbar).getRightMenu().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // show menu dialog
                    JDialog menuDialog = createMenuDroitSubscribe();
                    menuDialog.setVisible(true);
                }
            });
            ((SubscriberNavbar) navbar).getBasket().addActionListener(setBasketListener());
        }
    }

    private void initFilmsSectionsPane(){
        ArrayList<FilmSection> filmsSections = new ArrayList<FilmSection>(); 
        ArrayList<Film> allfilms = controller.getFacadeIHM().getAllFilms();
        
        ArrayList<Film> topFilmsMonth = controller.getFacadeIHM().getTopFilmsOfTheMonth();
        ArrayList<Film> topFilmsWeek = controller.getFacadeIHM().getTopFilmsOfTheWeek();
        ArrayList<BlueRay> dispoBluray = controller.getFacadeIHM().getAvailableBlueRays();
        System.out.println("num bluray:"+dispoBluray.size());
        
        if(controller.getCurrentAccount() == null){
            filmsSections.add(new FilmSection(this.frame, controller, topFilmsWeek, "Top 10 de la semaine", false));
            filmsSections.add(new FilmSection(this.frame, controller, topFilmsMonth,"Top 10 du mois", false));
            filmsSections.add(new FilmSection(this.frame, controller, dispoBluray,"Blu-ray disponibles"));
            filmsSections.add(new FilmSection(this.frame, controller, allfilms,"Tous les films", true)); // maybe remove later
        }
        else{
            filmsSections.add(new FilmSection(this.frame, controller, topFilmsWeek, "Top 10 de la semaine", false));
            filmsSections.add(new FilmSection(this.frame, controller, topFilmsMonth,"Top 10 du mois", false));
            filmsSections.add(new FilmSection(this.frame, controller, dispoBluray,"Blu-ray disponibles"));
            filmsSections.add(new FilmSection(this.frame, controller, allfilms,"Tous les films", true));
            filmsSections.add(new FilmSection(this.frame, controller, new ArrayList<Film>(),"Par catégorie", true));
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

    // menu droit pour normal
    private JDialog createMenuDroitNormal() {
        String[] tabFonctions = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "S'abonner", "Se deconnecter"};
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //@TODO les actions de menu (comme ci-dessus)
                // action de menu
                String cmd = e.getActionCommand();
                if(cmd.equals(tabFonctions[0])){ // Modifier mes données personnelles
                    controller.traite(MainPage.this, Keyword.SHOWACCOUNTINFOPAGE);
                }else
                if(cmd.equals(tabFonctions[1])){ // "Modifier mes cartes bancaires"
                    //@TODO
                }else
                if(cmd.equals(tabFonctions[3])){ // S'abonner
                    //@TODO
                    controller.traite(MainPage.this, Keyword.SUBSCRIBE);
                }else
                if(cmd.equals(tabFonctions[4])){ // Se deconnecter
                    controller.traite(MainPage.this, Keyword.LOGOUT);
                }
            }
        };
        return createMenuDialog("Menu droit", tabFonctions, actionListener, SysAL2000.DIALOG_WIDTH/3, SysAL2000.DIALOG_HEIGHT/3);
    }

    // menu gauche pour subscribe
    private JDialog createMenuGaucheSubscribe() {
        String[] tabFonctions = {"Voir mon historique de location", "Modifier mes préférences de location", "Filtrer les catégories", "Demander l'ajout d'un nouveau film"};
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //@TODO les actions de menu (comme ci-dessus)
                // action de menu
                String cmd = e.getActionCommand();
                if(cmd.equals(tabFonctions[0])){ // Voir mon historique de location
                    controller.traite(MainPage.this, Keyword.SHOWACCOUNTINFOPAGE);
                }else
                if(cmd.equals(tabFonctions[1])){ // Modifier mes préférences de location
                    //@TODO
                }else
                if(cmd.equals(tabFonctions[2])){ // Filtrer les catégories
                    //@TODO
                }else
                if(cmd.equals(tabFonctions[3])){ // Demander l'ajout d'un nouveau film
                    //@TODO
                }
            }
        };
        return createMenuDialog("Menu gauche", tabFonctions, actionListener, SysAL2000.DIALOG_WIDTH/3, SysAL2000.DIALOG_HEIGHT/3);
    }

    // menu droit pour subscribe
    private JDialog createMenuDroitSubscribe() {
        String[] tabFonctions = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "Commander une carte abonné", "Arreter mon abonnement", "Se deconnecter"};
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //@TODO les actions de menu (comme ci-dessus)
                // action de menu
                String cmd = e.getActionCommand();
                if(cmd.equals(tabFonctions[0])){ // Modifier mes données personnelles
                    controller.traite(MainPage.this, Keyword.SHOWACCOUNTINFOPAGE);
                }else
                if(cmd.equals(tabFonctions[1])){ // "Modifier mes cartes bancaires"
                    //@TODO
                }else
                if(cmd.equals(tabFonctions[2])){ // Commander une carte abonné
                    //@TODO
                }else
                if(cmd.equals(tabFonctions[3])){ // Arreter mon abonnement
                    //@TODO
                }else
                if(cmd.equals(tabFonctions[4])){ // Arreter mon abonnement
                    controller.traite(MainPage.this, Keyword.LOGOUT);
                }
            }
        };
        return createMenuDialog("Menu droit", tabFonctions, actionListener, SysAL2000.DIALOG_WIDTH/3, SysAL2000.DIALOG_HEIGHT/3);
    }

    private JDialog createMenuDialog(String title, String[] tabFonctions, ActionListener actionListener, int w, int h){
        JDialog menuDialog = new JDialog(frame, title, true);
        menuDialog.setSize(/*SysAL2000.DIALOG_WIDTH/3*/w,/*SysAL2000.DIALOG_HEIGHT/3*/h);
        menuDialog.setLayout(new BorderLayout());
        //
        ActionListener closeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDialog.dispose();
            }
        };

        // contenu de menu
        JPanel menuPane = new JPanel();
        menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
        for(String optionName : tabFonctions){
            JButton optionBtn = new JButton(optionName);
            optionBtn.setActionCommand(optionName);
            optionBtn.addActionListener(actionListener);  // pour effectuer l'action
            optionBtn.addActionListener(closeListener);  // pour quand on click, dialog close
            optionBtn.setAlignmentX(CENTER_ALIGNMENT);
            // interval
            menuPane.add(Box.createRigidArea(new Dimension(10, 10)));
            menuPane.add(optionBtn);
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(closeListener);

        menuDialog.add(menuPane, BorderLayout.CENTER);
        menuDialog.add(closeButton, BorderLayout.SOUTH);
        menuDialog.setLocationRelativeTo(frame);
        return menuDialog;
    }

}