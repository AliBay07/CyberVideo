package ui;

import facade.ui.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class FilmInfoSection extends JPanel {
    private Film film;
    private JButton showMore;
    private JButton rent;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public FilmInfoSection(Film f){
        film = f;
        showMore = new JButton("En savoir plus");
        rent = new JButton("Louer");
        this.setPreferredSize(new Dimension(FRAME_WIDTH, 200));
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();

        //Ajout de l'affiche du film
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.2;
        c.gridheight = 4;
        this.add(new JLabel(film.getImageIcon()),c);

        //Ajout des informations du film
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        //c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0.5;
        this.add(new JLabel(film.getName()), c);

        c.fill = GridBagConstraints.NONE;
        //c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        this.add(new JLabel(film.getAuthor().getName()), c);
        /*String infos = "Réalisateur : " + film.getAuthor().getName() + "\n";
        infos += "Acteurs : ";
        for(int i=0; i < film.getActors().size() && i < 2; i++){
            infos += film.getActors().get(i).getName() + ", ";
        }
        infos = infos.substring(0, infos.length()-2);
        this.add(new JTextArea(infos),c);*/

        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.weightx = 0.5;
        String actors = "";
        for(int i=0; i < film.getActors().size() && i < 2; i++){
            actors += film.getActors().get(i).getName() + ", ";
        }
        actors = actors.substring(0, actors.length()-2);
        this.add(new JLabel(actors), c);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.5;
        this.add(new JLabel(Integer.toString(film.getDuration()) + " min"), c);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 0;
        //c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.weightx = 0.3;
        c.gridheight = 2;
        this.add(showMore, c);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 2;
        //c.anchor = GridBagConstraints.LAST_LINE_END;
        c.weightx = 0.3;
        c.gridheight = 2;
        this.add(rent, c);
    }
}
