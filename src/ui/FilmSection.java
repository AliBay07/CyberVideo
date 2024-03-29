package ui;
import beans.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilmSection extends BasePage {
    private ArrayList<Film> listFilms; //A remplacer par une arraylist de films certainement !
    private ArrayList<BlueRay> listBluray; //A remplacer par une arraylist de films certainement !
    private JLabel title;
    private JPanel filmsPane;
    private JPanel filmsPaneInterior = new JPanel();
    private JScrollPane showFilmsPane;
    private JButton showMoreFilms;
    private Film selectedFilm;
    private BlueRay selectedBluray;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public FilmSection(JFrame f, Controller c, ArrayList<BlueRay> films, String titleName) {
        super(f, c);
        boolean moreFilms = true;
        listBluray = films;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        title = new JLabel(titleName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        filmsPane = new JPanel(new BorderLayout());
        filmsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        filmsPane.setPreferredSize(new Dimension(FRAME_WIDTH, 200));
        for (int i=0; i<listBluray.size(); i++){ //listFilms.size()
            //Il faut récupérer les infos depuis la BDD et mettre l'affiche du film avec son nom
        	ImageIcon originalIcon = new ImageIcon(listBluray.get(i).getFilm().getPath());
        	int width = 100;
        	int height = 100;
        	ImageIcon icon = new ImageIcon(originalIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
            JButton film = new JButton(listBluray.get(i).getFilm().getName(), icon);
            film.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int j=0; j<listBluray.size(); j++){
                        if(listBluray.get(j).getFilm().getName().equals(film.getText())){
                            controller.setCurrentBlueRay(listBluray.get(j));
                            controller.traite(FilmSection.this, Keyword.SHOWFILMDETAILS);
                        }
                    }
                }
            });
            filmsPaneInterior.add(film);
        }
        showFilmsPane = new JScrollPane(filmsPaneInterior, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //filmsPane.add(leftArrow, BorderLayout.WEST);
        filmsPane.add(showFilmsPane, BorderLayout.CENTER);
        //filmsPane.add(rightArrow, BorderLayout.EAST);
        this.add(title);
        this.add(filmsPane);
        if(moreFilms){
            showMoreFilms = new JButton("Voir tous les films");
            showMoreFilms.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createRigidArea(new Dimension(0,10)));
            this.add(showMoreFilms);
        }
    }

    public FilmSection(JFrame f, Controller c, ArrayList<Film> films, String titleName, boolean moreFilms){
        super(f, c);
        listFilms = films;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        title = new JLabel(titleName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        filmsPane = new JPanel(new BorderLayout());
        filmsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        filmsPane.setPreferredSize(new Dimension(FRAME_WIDTH, 200));
        for (int i=0; i<listFilms.size(); i++){ //listFilms.size()
            //Il faut récupérer les infos depuis la BDD et mettre l'affiche du film avec son nom
        	ImageIcon originalIcon = new ImageIcon(listFilms.get(i).getPath());
        	int width = 100;
        	int height = 100;
        	ImageIcon icon = new ImageIcon(originalIcon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
            JButton film = new JButton(listFilms.get(i).getName(), icon);
            film.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int j=0; j<listFilms.size(); j++){
                        if(listFilms.get(j).getName().equals(film.getText())){
                            controller.setCurrentFilm(listFilms.get(j));
                            controller.traite(FilmSection.this, Keyword.SHOWFILMDETAILS);
                        }
                    }
                }
            });
            filmsPaneInterior.add(film);
        }
        showFilmsPane = new JScrollPane(filmsPaneInterior, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,  
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //filmsPane.add(leftArrow, BorderLayout.WEST);
        filmsPane.add(showFilmsPane, BorderLayout.CENTER);
        //filmsPane.add(rightArrow, BorderLayout.EAST);
        this.add(title);
        this.add(filmsPane);
        if(moreFilms){
            showMoreFilms = new JButton("Voir tous les films");
            showMoreFilms.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createRigidArea(new Dimension(0,10)));
            this.add(showMoreFilms);
        }
    }

    public JButton getMoreFilmsButton(){
        return showMoreFilms;
    }

    public Film getSelectedFilm(){
        return selectedFilm;
    }

    public BlueRay getSelectedBlueRay(){
        return selectedBluray;
    }
}
