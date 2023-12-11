package ui;

import beans.Account;
import beans.Actor;
import beans.Film;
import beans.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Affichage_Film extends BasePage {
    private Affichage_Film affichage_film = this;
    private Film film;

    private Account account;
    private BackNavigationBar backNavigationBar = new BackNavigationBar("Détails du film");

    public Affichage_Film(JFrame frame) {
        super(frame);
    }

    public Affichage_Film(JFrame frame, Film film,Controller controller) {
        super(frame,controller);
        this.film = film;
        this.account = controller.currentAccount;
        controller.setCurrentBlueRay(null);
        controller.setCurrentFilm(film);
    }

    public Affichage_Film(JFrame frame, BlueRay blueRay, Controller controller) {
        super(frame,controller);
        controller.setCurrentBlueRay(blueRay);
        controller.setCurrentFilm(blueRay.getFilm());
        this.account = controller.currentAccount;
        this.film = null;
    }

    public BasePage afficher()
    {
        if(film != null)
        {
            afficher_film(film, frame, controller);
            return this;
        }
        else
        {
            afficher_film2(controller.getCurrentBlueRay(), frame, controller);
            return this;
        }
    }
    public BasePage afficher_film(Film film, JFrame frame, Controller controller) {
        NavigationBar navbar = initNavigationBar();
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        Payment payement = new Payment();

        // Créer un JSplitPane pour diviser la fenêtre en deux parties de taille égale
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Les deux parties auront la même taille
        splitPane.setEnabled(false); // Empêche l'utilisateur de changer la taille

        // Partie supérieure du JSplitPane
        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(film.getPath());
        JLabel imageLabel = new JLabel(imageIcon);
        topPanel.add(imageLabel, BorderLayout.WEST);


        //zones avec les données du film
        JLabel jLabelTitle = new JLabel("Name : "+film.getName());
        JLabel jLabelAuthor = new JLabel("Author :"+film.getAuthors().get(0));

        // Liste d'acteurs
        JPanel actorsPanel = new JPanel(new GridLayout(film.getActors().size(), 1));
        for (Actor actor : film.getActors()) {
            if(film.getActors().get(0) == actor)
            {
                JLabel jLabelActor = new JLabel("Actor: " + actor.getFirstName());
                actorsPanel.add(jLabelActor);
            }
            else {
                JLabel jLabelActor = new JLabel("           " + actor.getLastName());
                actorsPanel.add(jLabelActor);
            }
        }
        //JScrollPane pour contenir la liste des acteurs
        JScrollPane actorsScrollPane = new JScrollPane(actorsPanel);

        JLabel jLabelDuration = new JLabel("Duration : "+film.getDuration()+" mn");

        //Liste Categories
        JPanel categoryPanel = new JPanel(new GridLayout(film.getCategories().size(),1));
        for(Category categorie : film.getCategories()) {
            if(film.getCategories().get(0) == categorie)
            {
                JLabel jLabelCategory = new JLabel("Category : " + categorie.getCategoryName());
                categoryPanel.add(jLabelCategory);
            }
            else {
                JLabel jLabelCategory = new JLabel("                   " + categorie.getCategoryName());
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
        JButton qrCode = new JButton(ScaleImage.scaleImageIcon(iconQrCode,20,20));

        if(account == null)
        {
            qrCode.setVisible(false);
        }

        buttonPanel.add(qrCode);
        descriptionTextArea.add(buttonPanel);

        splitPane.setBottomComponent(descriptionScrollPane);

        this.add(splitPane, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(navbar, BorderLayout.NORTH);


        qrCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payement.afficherPaiement(account,frame,film,controller,affichage_film);
            }
        });


        this.setVisible(true);
        return this;
    }


    public BasePage afficher_film2(BlueRay blueRay, JFrame frame, Controller controller) {
        NavigationBar navbar = initNavigationBar();
        this.setLayout(new BorderLayout());
        this.setSize(SysAL2000.FRAME_WIDTH, SysAL2000.FRAME_HEIGHT);
        Payment payement = new Payment();

        // Créer un JSplitPane pour diviser la fenêtre en deux parties de taille égale
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Les deux parties auront la même taille
        splitPane.setEnabled(false); // Empêche l'utilisateur de changer la taille

        // Partie supérieure du JSplitPane
        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(blueRay.getFilm().getPath());
        JLabel imageLabel = new JLabel(imageIcon);
        topPanel.add(imageLabel, BorderLayout.WEST);


        //zones avec les données du film
        JLabel jLabelTitle = new JLabel("Name : "+blueRay.getFilm().getName());
        JLabel jLabelAuthor = new JLabel("Author :"+blueRay.getFilm().getAuthors().get(0));

        // Liste d'acteurs
        JPanel actorsPanel = new JPanel(new GridLayout(blueRay.getFilm().getActors().size(), 1));
        for (Actor actor : blueRay.getFilm().getActors()) {
            if(blueRay.getFilm().getActors().get(0) == actor)
            {
                JLabel jLabelActor = new JLabel("Actor: " + actor.getFirstName());
                actorsPanel.add(jLabelActor);
            }
            else {
                JLabel jLabelActor = new JLabel("           " + actor.getLastName());
                actorsPanel.add(jLabelActor);
            }
        }
        //JScrollPane pour contenir la liste des acteurs
        JScrollPane actorsScrollPane = new JScrollPane(actorsPanel);

        JLabel jLabelDuration = new JLabel("Duration : "+blueRay.getFilm().getDuration()+" mn");

        //Liste Categories
        JPanel categoryPanel = new JPanel(new GridLayout(blueRay.getFilm().getCategories().size(),1));
        for(Category categorie : blueRay.getFilm().getCategories()) {
            if(blueRay.getFilm().getCategories().get(0) == categorie)
            {
                JLabel jLabelCategory = new JLabel("Category : " + categorie.getCategoryName());
                categoryPanel.add(jLabelCategory);
            }
            else {
                JLabel jLabelCategory = new JLabel("                   " + categorie.getCategoryName());
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
        JTextArea descriptionTextArea = new JTextArea(blueRay.getFilm().getDescription());
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setCaretPosition(0);
        descriptionTextArea.setEditable(false);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

        //ajout des boutons pour choisir le format
        JPanel buttonPanel = new JPanel();
        ImageIcon iconBlueRay = new ImageIcon("src/ui/Images/cd.png");
        JButton blueRaybutton = new JButton(ScaleImage.scaleImageIcon(iconBlueRay,20,20));

        if(account == null)
        {
            blueRaybutton.setVisible(false);
        }

        buttonPanel.add(blueRaybutton);
        descriptionTextArea.add(buttonPanel);

        splitPane.setBottomComponent(descriptionScrollPane);

        this.add(splitPane, BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(navbar,BorderLayout.NORTH);

        this.setVisible(true);

        blueRaybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payement.afficherPaiement(account,frame, blueRay.getFilm(), controller,affichage_film);
            }
        });

        return this;
    }

    private NavigationBar initNavigationBar() {
        BackNavigationBar navbar = new BackNavigationBar("Détails films");
        navbar.getLeftBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                dispose();
            }
        });
        return navbar;
    }

}