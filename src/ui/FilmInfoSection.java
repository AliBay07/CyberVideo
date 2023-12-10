package ui;

//import facade.ui.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import beans.*;
import java.awt.*;

public class FilmInfoSection extends JPanel {
    private Controller controller;
    private Film film;
    private JButton showMore;
    private JButton rent;
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int FRAME_WIDTH = (int) dimension.getWidth();

    public FilmInfoSection(Film f, Controller controller){
        this.controller = controller;
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
        ImageIcon image = new ImageIcon(film.getPath());
        if(image.getIconHeight() > this.getHeight())
            this.add(new JLabel("Affiche du film",JLabel.CENTER),c);
        else
            this.add(new JLabel(new ImageIcon(film.getPath()),JLabel.CENTER),c); //Trouver le moyen d'avoir l'image plutot que son lien

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
        this.add(new JLabel(film.getAuthors().get(0).getFirstName() + " " + film.getAuthors().get(0).getLastName()), c); //Il faut demander à ce que ce soit un réalisateur et pas une liste !

        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.weightx = 0.5;
        String actors = "";
        for(int i=0; i < film.getActors().size() && i < 2; i++){
            actors += film.getActors().get(i).getFirstName() +" " + film.getActors().get(i).getLastName() + ", ";
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
        if(controller.getCurrentAccount() == null){ 
            rent.setVisible(false);
        }
    }

    public JButton getRentButton(){
        return rent;
    }
    
    public JButton getShowMoreButton(){
        return showMore;
    }

}
