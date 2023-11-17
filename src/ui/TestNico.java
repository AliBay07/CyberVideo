package ui;
import facade.ui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class TestNico {

    public static void main(String[] args) {
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
        categories.add(new Categorie(1L,"Thriller"));
        categories.add(new Categorie(1L,"Thriller"));
        categories.add(new Categorie(1L,"Thriller"));
        categories.add(new Categorie(1L,"Thriller"));
        categories.add(new Categorie(1L,"Thriller"));
        categories.add(new Categorie(1L,"Thriller"));
        categories.add(new Categorie(1L,"Thriller"));
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
        Affichage_Film affichageFilm = new Affichage_Film();

        Date date = new Date(10-12-2002);
        User nicolas = new User("Nicolas","Guegan", date);
        CreditCard creditCard = new CreditCard(1L,"BNP");
        Account account = new NormalAccount();
        SubscriberAccount account2 = new SubscriberAccount(1L,"password",nicolas,creditCard);

        SubscriptionCard subscriptionCard = new SubscriptionCard(1L,1000);
        account2.setSubscriptionCard(subscriptionCard);
        account2.setCreditCard(creditCard);

        //crée un JFrame qui va contenir le panel renvoyé par affichage film
        JFrame frame = new JFrame("Affichage film");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(affichageFilm.afficher_film(film,account,frame));
        frame.setVisible(true);
    }

}
