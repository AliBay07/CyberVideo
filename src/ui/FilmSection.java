package ui;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class FilmSection extends JPanel {
    private ArrayList<String> listFilms; //A remplacer par une arraylist de films certainement !
    private JLabel title;
    private JPanel filmsPane;
    /*private JButton leftArrow = new JButton("<-");
    private JButton rightArrow = new JButton("->");*/
    private JPanel filmsPaneInterior = new JPanel();
    private JScrollPane showFilmsPane;
    private JButton showMoreFilms;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public FilmSection(ArrayList<String> films, String titleName, boolean moreFilms){
        listFilms = films;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        title = new JLabel(titleName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        filmsPane = new JPanel(new BorderLayout());
        filmsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        filmsPane.setPreferredSize(new Dimension(FRAME_WIDTH, 200));
        for (int i=0; i<10; i++){ //listFilms.size()
            //Il faut récupérer les infos depuis la BDD et mettre l'affiche du film avec son nom
            JButton film = new JButton("nomFilm "+(i+1));
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
}
