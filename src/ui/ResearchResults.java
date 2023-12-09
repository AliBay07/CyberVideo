package ui;

import beans.*;
//import facade.ui.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ResearchResults extends BasePage {
    Section choice = Section.NOTHING;
    private HashMap<String, ArrayList<String>> criteria;
    //private ArrayList<String> criteria;
    private ArrayList<Film> filmsResults;
    private NavigationBar navbar;
    private JPanel mainPage;
    private JPanel researchElements;
    private JPanel listResults;
    private JPanel listResultsInterior;
    private ArrayList<FilmInfoSection> filmsSections;
    private JScrollPane filmsScrollPane;
    private Film selectedFilm;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();
    public static int FRAME_HEIGHT = (int) dimension.getHeight();

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==navbar.getLeftComponent()){
                controller.traite(ResearchResults.this, Keyword.MAINPAGE); 
            }
            for(int i=0; i<filmsSections.size(); i++){
                if(e.getSource()==filmsSections.get(i).getRentButton()) {
                    controller.traite(ResearchResults.this, Keyword.RENT);
                    selectedFilm = filmsResults.get(i);
                }
                else if(e.getSource()==filmsSections.get(i).getShowMoreButton()){
                    controller.traite(ResearchResults.this, Keyword.SHOWFILMDETAILS);
                    selectedFilm = filmsResults.get(i);
                }
                    
            }
            System.out.println("Message bien reçu !");
        }
    };

    public ResearchResults(JFrame f, Section s, HashMap<String, ArrayList<String>> criterias){
        super(f);
        //Création des listes ayant le contenu des critères/films
        choice = s;
        criteria = criterias;
        filmsResults = new ArrayList<Film>(); //Ici faire un appel à la BD (ou en paramètre et gestion dans le main ?)
        //Génération de films pour tester
        for(int i=1; i<6; i++){
            genereFilm(i);
        }
        //Vérifier qu'il y a des films dans la liste !!
        filmsSections = new ArrayList<FilmInfoSection>();
        for(int i=0; i< filmsResults.size(); i++){
            filmsSections.add(new FilmInfoSection(filmsResults.get(i)));
            filmsSections.get(i).getRentButton().addActionListener(actionListener);
            filmsSections.get(i).getShowMoreButton().addActionListener(actionListener);
        }
        selectedFilm = null;
        //Création des éléments visuels de l'interface
        this.setLayout(new BorderLayout());
		this.setPreferredSize(f.getSize());
		this.setLocation(0,0);
    }

    /*private void setResearchPanel(){
        researchElements = new JPanel();
        researchElements.setPreferredSize(new Dimension(FRAME_WIDTH, 40)); //Mettre des constantes aussi !
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
        researchElements.setVisible(true);
    }*/

    private void initResultsPanel(){
        listResults = new JPanel(new BorderLayout());
        listResults.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT/3)); //Mettre des constantes aussi !
        listResultsInterior = new JPanel();
        listResultsInterior.setLayout(new BoxLayout(listResultsInterior, BoxLayout.Y_AXIS));
        
        for(FilmInfoSection fs : filmsSections) {
            listResultsInterior.add(Box.createRigidArea(new Dimension(0,30)));
            listResultsInterior.add(fs);
        }
        listResultsInterior.add(Box.createRigidArea(new Dimension(0,30)));

        //Création du panel scrollable et insertion dans la page d'affichage des films
        filmsScrollPane = new JScrollPane(listResultsInterior, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel title = this.setTitle();
        title.setHorizontalAlignment(JLabel.CENTER);
        listResults.add(title, BorderLayout.NORTH);
        listResults.add(filmsScrollPane, BorderLayout.CENTER);
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
                rightMenuDialog.setLocationRelativeTo(ResearchResults.this);
                rightMenuDialog.setVisible(true);
            }
        };
        return openRightMenu;
    }

    private void initNavbar(){
        JButton backButton = new JButton("<--");
        backButton.addActionListener(actionListener);
        JButton connexionButton;
        if(controller.getCurrentAccount() == null){
            connexionButton = new JButton("Connexion");
            //Faire un actionListener pour relier au controlleur
            //connexionButton.addActionListener(this.setRightMenu(null));
            navbar = new NavigationBar("Résultats de la recherche", backButton, connexionButton);
        }
        else {
            connexionButton = new JButton(controller.getCurrentAccount().getUser().getFirstName());
            if(controller.getCurrentAccount() instanceof NormalAccount){
                String[] tabFonctions = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "S'abonner", "Se deconnecter"};
                connexionButton.addActionListener(this.setRightMenu(tabFonctions));
            }
            else {
                String[] tabFonctions = {"Modifier mes données personnelles", "Modifier mes cartes bancaires", "Commander une carte abonné", "Arreter mon abonnement", "Se deconnecter"};
                connexionButton.addActionListener(this.setRightMenu(tabFonctions));
            }
            navbar = new NavigationBar("Résultats de la recherche", backButton, connexionButton);
        }
    }

    public void showResearchResults(){
        //A changer ! Mettre un bouton retour et titre : Résultats de la recherche + indice de connexion à droite
        this.initNavbar();
        mainPage = new JPanel();
        mainPage.setLayout(new BoxLayout(mainPage, BoxLayout.Y_AXIS));
        //mainPage.setLayout(new GridLayout(2,1));
        
        //mainPage.setLayout(new BoxLayout(mainPage, BoxLayout.Y_AXIS));
        mainPage.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT-50));

        //Panel avec éléments pour lancer une recherche spécifique
        //this.setResearchPanel();

        //Panel d'affichage des films résultats aux utilisateurs
        this.initResultsPanel();

        //mainPage.add(researchElements);
        mainPage.add(Box.createRigidArea(new Dimension(0,20)));
        mainPage.add(listResults);

        this.add(navbar, BorderLayout.NORTH);
        this.add(mainPage, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JLabel setTitle() {
        JLabel title = new JLabel();
        if(choice == Section.TOP10W)
            title.setText("Top 10 des films de la semaine");
        else if(choice == Section.TOP10M)
            title.setText("Top 10 des films du mois");
        else if(choice == Section.BLURAY)
            title.setText("Blu-Ray disponibles");
        else if(choice == Section.ALL)
            title.setText("Tous les films");
        else if(choice == Section.CATEGORY && criteria.size()>0)
            title.setText(criteria.get("categorie").get(0));
        else if(choice == Section.ADVANCED && criteria.size()>0)
            title.setText("Recherche par critères");
        return title;
    }

    private void genereFilm(long id){
        Author Nicolas = new Author();
        Nicolas.setFirstName("Nicolas");
        Nicolas.setId(1L);
        ArrayList<Actor> actors = new ArrayList<>();
        Actor a1 = new Actor();
        a1.setFirstName("Skander");
        a1.setId(2L);
        actors.add(a1);
        Actor a2 = new Actor();
        a2.setFirstName("Jiawei");
        a2.setId(3L);
        actors.add(a2);
        Actor a3 = new Actor();
        a3.setFirstName("Noémie");
        a3.setId(4L);
        actors.add(a3);
        Actor a4 = new Actor();
        a4.setFirstName("Ali");
        a4.setId(5L);
        actors.add(a4);
        Actor a5 = new Actor();
        a5.setFirstName("Nizar");
        a5.setId(6L);
        actors.add(a5);
        ArrayList<Category> categories = new ArrayList<>();
        Category c1 = new Category();
        c1.setCategoryName("Action");
        c1.setId(1L);
        categories.add(c1);
        Category c2 = new Category();
        c2.setCategoryName("Aventure");
        c2.setId(1L);
        categories.add(c2);
        Category c3 = new Category();
        c3.setCategoryName("Thriller");
        c3.setId(1L);
        categories.add(c3);        
        //ImageIcon imageDuTitanic = new ImageIcon("src/ui/Images/imageDuTitanic3.jpg");
        int duration = 120;
        String description = "En 1997, l'épave du Titanic est l'objet d'une exploration fiévreuse," +
                "menée par des chercheurs de trésor en quête d'un diamant bleu qui se trouvait à bord." +
                "Frappée par un reportage télévisé," +
                "l'une des rescapées du naufrage, âgée de 102 ans, Rose DeWitt," +
                "se rend sur place et évoque ses souvenirs. 1912." +
                "Fiancée à un industriel arrogant," +
                "Rose croise sur le bateau un artiste sans le sou.";

        Film film = new Film();
        film.setActors(actors);
        ArrayList<Author> autorList = new ArrayList<Author>();
        autorList.add(Nicolas);
        film.setAuthors(autorList); //Il faut changer ça, ce n'est pas une liste !
        film.setCategories(categories);
        film.setName("Titanic");
        film.setId(id);
        film.setDescription(description);
        film.setDuration(duration);
        film.setPath("src/ui/Images/qr-code.png");
        filmsResults.add(film);
    }
}
