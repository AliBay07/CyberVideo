package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Basket extends JDialog {
    private ArrayList<String> filmsList;
    private JPanel filmsPanel = new JPanel();
    private JPanel titresTableau = new JPanel(new BorderLayout());
    //Code dans mon Test.java pour dimensionner la JFrame et les JDialog
    public static Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static int DIALOG_WIDTH = (int) (dimension.getWidth()/3)*2;
    public static int DIALOG_HEIGHT = (int) (dimension.getHeight()/3)*2;

    public Basket(ArrayList<String> films){
        filmsPanel.setLayout(new BoxLayout(filmsPanel, BoxLayout.Y_AXIS));
        filmsList = films;
    }

    public void showTabTitles(){
        titresTableau.add(new JLabel("Numéro"), BorderLayout.WEST);
        titresTableau.add(new JLabel("Nom du film"), BorderLayout.CENTER);
        ((JLabel) titresTableau.getComponent(1)).setHorizontalAlignment(JLabel.CENTER);
        titresTableau.add(new JLabel("Supprimer"), BorderLayout.EAST);
        this.add(titresTableau);
    }

    public void afficheFilmDansPanier(){
        JButton bin = new JButton("Poubelle");
        ActionListener delete = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = ((Component) e.getSource()).getParent();
                JLabel num = (JLabel) (parent.getComponent(0));
                filmsList.remove(Integer.getInteger(num.getText()));
                Container pParent = parent.getParent();
                pParent.remove(parent);
                Basket.this.repaint();
            }
        };
        bin.addActionListener(delete);
        for(int i=0; i<filmsList.size(); i++){
            JPanel filmPanel = new JPanel(new BorderLayout());
            filmPanel.setSize(Test.DIALOG_WIDTH, Test.DIALOG_HEIGHT/5);
            filmPanel.setAlignmentX((float) 0.5);
            filmPanel.add(new JLabel(Integer.toString(i+1)), BorderLayout.WEST);
            ((JLabel) filmPanel.getComponent(0)).setHorizontalAlignment(JLabel.CENTER);
            filmPanel.add(new JLabel(filmsList.get(i)), BorderLayout.CENTER);
            ((JLabel) filmPanel.getComponent(1)).setHorizontalAlignment(JLabel.CENTER);
            filmPanel.add(new JButton("Poubelle"), BorderLayout.EAST);
            ((JButton) filmPanel.getComponent(2)).addActionListener(delete);
            this.filmsPanel.add(filmPanel);
        }
        this.add(filmsPanel);
    }

    public void showBasket() {
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(Test.DIALOG_WIDTH, Test.DIALOG_HEIGHT);
		this.setLocationRelativeTo(null);
        this.showTabTitles();
        this.afficheFilmDansPanier();
        this.setVisible(true);
    }
}
