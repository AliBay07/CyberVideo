package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Basket extends JDialog {
    public Basket(ArrayList<String> films){
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(1280, 720);
		setLocation(0,0);

        JPanel titresTableau = new JPanel(new BorderLayout());
        titresTableau.add(new JLabel("Numéro"), BorderLayout.WEST);
        JLabel filmName = new JLabel("Nom du film");
        filmName.setAlignmentX((float) 0.5);
        titresTableau.add(new JLabel("Nom du film"), BorderLayout.CENTER);
        titresTableau.add(new JLabel("Supprimer"), BorderLayout.EAST);
        this.add(titresTableau);

        ActionListener delete = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = ((Component) e.getSource()).getParent();
                System.out.println(parent.getParent().getName());
                JLabel num = (JLabel) (parent.getComponent(0));
                films.remove(Integer.getInteger(num.getText()));
                Container pParent = parent.getParent();
                pParent.remove(parent);
                Basket.this.afficheFilmDansPanier(films, titresTableau);
            }
        };
        for(int i=0; i<films.size(); i++){
            JPanel filmPanel = new JPanel(new BorderLayout());
            filmPanel.setSize(this.getWidth(), 60);
            filmPanel.add(new JLabel(Integer.toString(i)), BorderLayout.WEST);
            filmPanel.add(new JLabel(films.get(i)), BorderLayout.CENTER);
            JButton bin = new JButton("Supprimer");
            bin.addActionListener(delete);
            filmPanel.add(bin, BorderLayout.EAST);
            this.add(filmPanel);
        }

        this.setVisible(true);
    }

    public void afficheFilmDansPanier(ArrayList<String> films, JPanel filmPanel){
        JButton bin = new JButton("Poubelle");
        for(int i=0; i<films.size(); i++){
            filmPanel.setSize(this.getWidth(), 60);
            filmPanel.add(new JLabel(Integer.toString(i)), BorderLayout.WEST);
            filmPanel.add(new JLabel(films.get(i)), BorderLayout.CENTER);
            filmPanel.add(bin, BorderLayout.EAST);
        }
    }
}
