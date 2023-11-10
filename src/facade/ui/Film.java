package facade.ui;

import ui.ScaleImage;

import javax.swing.*;
import java.util.ArrayList;

public class Film {
    private String name;
    private String description;
    private Author author;
    private ArrayList<Actor> actors;
    private ArrayList<Categorie> categories;
    private ImageIcon imageIcon;
    private int duration;

    public Film(){}
    public Film(String name, String description, Author author, ArrayList<Actor> actors, ArrayList<Categorie> categories, int duration) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.actors = actors;
        this.categories = categories;
        this.duration = duration;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setAuthor(Author author){
        this.author = author;
    }
    public Author getAuthor(){
        return this.author;
    }
    public void setActors(Actor actor){
        this.actors.add(actor);
    }
    public ArrayList<Actor> getActors(){
        return this.actors;
    }
    public void setCategories(Categorie categorie){
        this.categories.add(categorie);
    }
    public ArrayList<Categorie> getCategories(){
        return this.categories;
    }
    public void setImageIcon(ImageIcon imageIcon){

        if(imageIcon.getIconHeight() == -1 || imageIcon.getIconWidth() == -1)
        {
            this.imageIcon = null;
        }
        else{
            if(imageIcon.getIconWidth()!=210 || imageIcon.getIconHeight()!=280)
            {
                this.imageIcon = ScaleImage.scaleImageIcon(imageIcon,200,267);
            }
            else{
                this.imageIcon = imageIcon;
            }
        }

    }
    public ImageIcon getImageIcon(){
        return this.imageIcon;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public int getDuration(){
        return this.duration;
    }


}
