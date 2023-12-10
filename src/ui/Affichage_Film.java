package ui;

import facade.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Affichage_Film extends BasePage {

    public Affichage_Film(JFrame frame) {
        super(frame);
    }

    public Affichage_Film(JFrame frame, Film film, Account account) {
        super(frame);
        afficher_film(film, account, frame);
    }

    public JPanel afficher_film(Film film, Account account, JFrame frame) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(800, 600);
        Payment payement = new Payment();

        // Créer un JSplitPane pour diviser la fenêtre en deux parties de taille égale
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Les deux parties auront la même taille
        splitPane.setEnabled(false); // Empêche l'utilisateur de changer la taille

        // Partie supérieure du JSplitPane
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(film.getImageIcon());
        topPanel.add(imageLabel, BorderLayout.WEST);


        //zones avec les données du film
        JLabel jLabelTitle = new JLabel("Name : "+film.getName());
        JLabel jLabelAuthor = new JLabel("Author :"+film.getAuthor().getName());

        // Liste d'acteurs
        JPanel actorsPanel = new JPanel(new GridLayout(film.getActors().size(), 1));
        for (Actor actor : film.getActors()) {
            if(film.getActors().get(0) == actor)
            {
                JLabel jLabelActor = new JLabel("Actor: " + actor.getName());
                actorsPanel.add(jLabelActor);
            }
            else {
                JLabel jLabelActor = new JLabel("           " + actor.getName());
                actorsPanel.add(jLabelActor);
            }
        }
        //JScrollPane pour contenir la liste des acteurs
        JScrollPane actorsScrollPane = new JScrollPane(actorsPanel);

        JLabel jLabelDuration = new JLabel("Duration : "+film.getDuration()+" mn");

        //Liste Categories
        JPanel categoryPanel = new JPanel(new GridLayout(film.getCategories().size(),1));
        for(Categorie categorie : film.getCategories()) {
            if(film.getCategories().get(0) == categorie)
            {
                JLabel jLabelCategory = new JLabel("Category : " + categorie.getName());
                categoryPanel.add(jLabelCategory);
            }
            else {
                JLabel jLabelCategory = new JLabel("                   " + categorie.getName());
                categoryPanel.add(jLabelCategory);
            }

        }
        //JScrollPane pour contenir la liste des Categories
        JScrollPane categoryScrollPane = new JScrollPane(categoryPanel);

        JPanel remainingSize = new JPanel(new GridLayout( 0,2));

        //Bordures adaptées à chaque composant
        categoryScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        actorsScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        jLabelTitle.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        jLabelAuthor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 150));

        //Ajout des composants au Panel
        remainingSize.add(actorsScrollPane);
        remainingSize.add(jLabelTitle);
        remainingSize.add(jLabelAuthor);
        remainingSize.add(actorsScrollPane);
        remainingSize.add(jLabelDuration);
        remainingSize.add(categoryScrollPane);

        //Ajout du panel avec l'image et du panel avec les composants dans la partie du haut
        JPanel topComponent = new JPanel(new BorderLayout());
        topComponent.add(topPanel, BorderLayout.WEST);
        topComponent.add(remainingSize, BorderLayout.EAST);
        splitPane.setTopComponent(topComponent);


        // Partie inférieure du JSplitPane
        JTextArea descriptionTextArea = new JTextArea(film.getDescription());
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setCaretPosition(0);
        descriptionTextArea.setEditable(false);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

        //ajout des boutons pour choisir le format
        JPanel buttonPanel = new JPanel();
        ImageIcon iconBlueRay = new ImageIcon("src/ui/Images/cd.png");
        ImageIcon iconQrCode = new ImageIcon("src/ui/Images/qr-code.png");
        JButton blueRay = new JButton(ScaleImage.scaleImageIcon(iconBlueRay,20,20));
        JButton qrCode = new JButton(ScaleImage.scaleImageIcon(iconQrCode,20,20));

        if(account == null)
        {
            blueRay.setVisible(false);
            qrCode.setVisible(false);
        }

        buttonPanel.add(blueRay);
        buttonPanel.add(qrCode);
        descriptionTextArea.add(buttonPanel);



        splitPane.setBottomComponent(descriptionScrollPane);

        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);


        blueRay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payement.afficherPaiement(account,frame,film);
            }
        });

        qrCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payement.afficherPaiement(account,frame,film);
            }
        });

        mainPanel.setVisible(true);

        return mainPanel;
    }
}
