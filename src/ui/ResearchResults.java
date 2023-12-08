package ui;

import facade.ui.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ResearchResults extends BasePage {
    Section choice = Section.NOTHING;
    private HashMap<String, ArrayList<String>> criteria;
    //private ArrayList<String> criteria;
    private ArrayList<Film> filmsResults;
    private SubscriberNavbar navbar;
    private JPanel mainPage;
    private JPanel researchElements;
    private JPanel listResults;
    private JPanel listResultsInterior;
    private ArrayList<FilmInfoSection> filmsSections;
    private JScrollPane filmsScrollPane;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();
    public static int FRAME_HEIGHT = (int) dimension.getHeight();

    public ResearchResults(JFrame f, Section s, HashMap<String, ArrayList<String>> criterias){
        super(f);
        //Création des listes ayant le contenu des critères/films
        choice = s;
        criteria = criterias;
        filmsResults = new ArrayList<Film>(); //Ici faire un appel à la BD (ou en paramètre et gestion dans le main ?)
        //Génération de films pour tester
        for(int i=0; i<3; i++){
            genereFilm();
        }
        //Vérifier qu'il y a des films dans la liste !!
        filmsSections = new ArrayList<FilmInfoSection>();
        for(int i=0; i< filmsResults.size(); i++){
            filmsSections.add(new FilmInfoSection(filmsResults.get(i)));
        }

        //Création des éléments visuels de l'interface
        this.setLayout(new BorderLayout());
		this.setSize(f.getSize());
		this.setLocation(0,0);
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
    }

    private void setResultsPanel(){
        listResults = new JPanel(new BorderLayout());
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
        listResults.add(this.setTitle(), BorderLayout.NORTH);
        listResults.add(filmsScrollPane, BorderLayout.CENTER);
    }

    public void showResearchResults(){
        navbar = new SubscriberNavbar(0, 15);
        mainPage = new JPanel();
        mainPage.setLayout(new GridLayout(2,1));
        //mainPage.setLayout(new BoxLayout(mainPage, BoxLayout.Y_AXIS));
        mainPage.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT-50));

        //Panel avec éléments pour lancer une recherche spécifique
        this.setResearchPanel();

        //Panel d'affichage des films résultats aux utilisateurs
        this.setResultsPanel();

        mainPage.add(researchElements);
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
            title.setText("Films correspondants");
        return title;
    }

    private void genereFilm(){
        Author Nicolas = new Author(1L,"Nicolas");
        ArrayList<Actor> actors = new ArrayList<>();

        actors.add(new Actor(1L,"Nicolas"));
        actors.add(new Actor(2L,"Noémie"));
        actors.add(new Actor(3L,"Skander"));
        actors.add(new Actor(4L,"LI"));
        actors.add(new Actor(5L,"Ali"));
        actors.add(new Actor(6L,"Nizar"));
        actors.add(new Actor(6L,"Nizar"));
        actors.add(new Actor(6L,"Nizar"));
        actors.add(new Actor(6L,"Nizar"));
        actors.add(new Actor(6L,"Nizar"));
        actors.add(new Actor(6L,"Nizar"));
        actors.add(new Actor(6L,"Nizar"));

        ArrayList<Categorie> categories = new ArrayList<>();
        categories.add(new Categorie(1L,"Action"));
        categories.add(new Categorie(1L,"Aventure"));
        categories.add(new Categorie(1L,"Thriller"));
        
        ImageIcon imageDuTitanic = new ImageIcon("src/ui/Images/imageDuTitanic3.jpg");
        int duration = 120;
        String description = "En 1997, l'épave du Titanic est l'objet d'une exploration fiévreuse," +
                "menée par des chercheurs de trésor en quête d'un diamant bleu qui se trouvait à bord." +
                "Frappée par un reportage télévisé," +
                "l'une des rescapées du naufrage, âgée de 102 ans, Rose DeWitt," +
                "se rend sur place et évoque ses souvenirs. 1912." +
                "Fiancée à un industriel arrogant," +
                "Rose croise sur le bateau un artiste sans le sou.";

        Film film = new Film("Titanic",description,Nicolas,actors,categories,duration);
        film.setImageIcon(imageDuTitanic);
        filmsResults.add(film);
    }
}
