package ui;

import beans.*;
import facade.ui.FacadeIHM;

import java.util.*;

import javax.swing.*;

/**
 * Controller pour le mode MVC
 */
public class Controller {

    FacadeIHM facadeIHM;
    JFrame frame;
    BasePage currentPage;
    BasePage oldPage;
    Account currentAccount;
    enum State { IDLE, SIGNIN_NORMAL, SIGNUP_NORMAL, SIGNIN_FOR_RENT, SIGNUP_FOR_RENT, SIGNIN_FOR_SUBSCRIBE, SIGNUP_FOR_SUBSCRIBE, SIGNIN_FOR_RETURN_FILM, LOGGED_NORMAL, LOGGED_PREMIUM, CHANGE_ACCOUNT_SETTING, SHOW_TOP10W_NO_CONNECT, SHOW_TOP10M_NO_CONNECT, SHOW_FILMS_NO_CONNECT, SHOW_BLURAY_NO_CONNECT, SHOW_RESEARCH_RESULTS_NO_CONNECT, SHOW_FILM_DETAILS_NO_CONNECT, SHOW_TOP10W_CONNECT, SHOW_TOP10M_CONNECT, SHOW_FILMS_CONNECT, SHOW_BLURAY_CONNECT, SHOW_RESEARCH_RESULTS_CONNECT, SHOW_FILMS_CATEGORY, SHOW_FILM_DETAILS_CONNECT, RENT_FILM, SHOW_VALIDATION_RENT, SHOW_ERROR_RENT, SHOW_VALIDATION_SUBSCRIBE, SHOW_RETURN_PAGE, SHOW_VALIDATION_RETURN_PAGE, SHOW_ERROR_RETURN_PAGE};
    State state = State.IDLE;

    public Controller(JFrame frame, FacadeIHM fihm) {
        this.frame = frame;
        facadeIHM = fihm;
        currentAccount = null;
    }

    public void traite(BasePage page, Keyword action){
        if(action==Keyword.LOGIN) { //Faire les cas ou l'utilisateur se login à cause d'une demande d'abonnement ou de location
            if(currentAccount instanceof NormalAccount)
                state = State.LOGGED_NORMAL;
            else if(currentAccount instanceof SubscriberAccount)
                state = State.LOGGED_PREMIUM;
            frame.remove(currentPage);
        }else
        if(action==Keyword.SHOWLOGINPAGE) {
            switch (state) {
                case IDLE:
                    state = State.SIGNIN_NORMAL;
                    break;
                case SHOW_RESEARCH_RESULTS_CONNECT:
                    state = State.SIGNIN_FOR_RENT;
                    break;
                case SHOW_BLURAY_CONNECT:
                    state = State.SIGNIN_FOR_RENT;
                    break;
                case SHOW_TOP10M_CONNECT:
                    state = State.SIGNIN_FOR_RENT;
                    break;
                case SHOW_TOP10W_CONNECT:
                    state = State.SIGNIN_FOR_RENT;
                    break;
                case SHOW_FILMS_CONNECT:
                    state = State.SIGNIN_FOR_RENT;
                    break;
                default:
                    break;
            }
            oldPage=currentPage;
            showLoginPage();
        }else
        if(action==Keyword.SUBSCRIBE){
            switch (state) {
                case IDLE:
                    showLoginPage();
                    state = State.SIGNIN_FOR_SUBSCRIBE;
                    break;
                case LOGGED_NORMAL: 
                    //Voir s'il faut afficher une pop up pour informer l'utilisateur qu'il s'est bien abonné
                    facadeIHM.subscribeToService();
                    break;
                case LOGGED_PREMIUM:
                    //Show pop up ou page pour créditer sa carte abonnée
                default:
                    break;
            }
        }else
        if(action==Keyword.RETURN_BLURAY){
            switch(state) {
                case IDLE:
                    showLoginPage();
                    state = State.SIGNIN_FOR_RETURN_FILM;
                    break;
                default:
                    break;
            }
        }else
        if(action == Keyword.SHOWADVANCEDRESEARCH){
            ArrayList<Film> results = facadeIHM.searchFilmByCriteria(((MainPage)currentPage).getChosenCriterias());
            state = State.SHOW_RESEARCH_RESULTS_CONNECT;
            showResearchPage(Section.ADVANCED, results); //Vérifier le state !
        }
        else if(action == Keyword.SHOWTOP10W){
            ArrayList<Film> results = facadeIHM.getTopFilmsOfTheWeek();
            state = State.SHOW_TOP10W_CONNECT;
            showResearchPage(Section.TOP10W, results);
        }
        else if(action == Keyword.SHOWTOP10M){
            ArrayList<Film> results = facadeIHM.getTopFilmsOfTheMonth();
            state = State.SHOW_TOP10M_CONNECT;
            showResearchPage(Section.TOP10M, results);
        }
        else if(action == Keyword.SHOWBLURAY){
            ArrayList<Film> results = facadeIHM.getAvailableBlueRays();
            state = State.SHOW_BLURAY_CONNECT;
            showResearchPage(Section.BLURAY, results);
        }
        else if(action == Keyword.SHOWALLFILMS){
            ArrayList<Film> results = facadeIHM.getAllFilms();
            state = State.SHOW_FILMS_CONNECT;
            showResearchPage(Section.ALL, results);
        }
        else if(action == Keyword.SHOWCATEGORIES){
            ArrayList<Film> results = facadeIHM.searchFilmByCriteria(((MainPage)currentPage).getChosenCriterias());
            state = State.SHOW_FILMS_CATEGORY;
            showResearchPage(Section.CATEGORY, results);
        }
        if(action == Keyword.BACK){
            switch(state){
                case SHOW_BLURAY_CONNECT:
                    frame.remove(currentPage);
                    showMainPage();
                    break;
                case SHOW_FILMS_CATEGORY:
                    frame.remove(currentPage);
                    showMainPage();
                    break;
                case SHOW_FILMS_CONNECT:
                    frame.remove(currentPage);
                    showMainPage();
                    break;
                case SHOW_RESEARCH_RESULTS_CONNECT:
                    frame.remove(currentPage);
                    showMainPage();
                    break;
                case SHOW_TOP10M_CONNECT:
                    frame.remove(currentPage);
                    showMainPage();
                    break;
                case SHOW_TOP10W_CONNECT:
                    frame.remove(currentPage);
                    showMainPage();
                    break;
                //Il faut remodifier le state quand on fait un back ! écrire tous les cas
                default:
                    back();
            }
        }
        if(action == Keyword.SHOWFILMDETAILS){
            switch(state){ //A tester
                case SHOW_RESEARCH_RESULTS_CONNECT:
                    showDetailedFilm(((ResearchResults)currentPage).getSelectedFilm());
                    state = State.SHOW_FILM_DETAILS_CONNECT;
                case SHOW_BLURAY_CONNECT:
                    showDetailedFilm(((ResearchResults)currentPage).getSelectedFilm());
                    state = State.SHOW_FILM_DETAILS_CONNECT;
                case SHOW_TOP10M_CONNECT:
                    showDetailedFilm(((ResearchResults)currentPage).getSelectedFilm());
                    state = State.SHOW_FILM_DETAILS_CONNECT;
                case SHOW_TOP10W_CONNECT:
                    showDetailedFilm(((ResearchResults)currentPage).getSelectedFilm());
                    state = State.SHOW_FILM_DETAILS_CONNECT;
                case SHOW_FILMS_CONNECT:
                    showDetailedFilm(((ResearchResults)currentPage).getSelectedFilm());
                    state = State.SHOW_FILM_DETAILS_CONNECT;
                default:
                    break;
            }
        }
    }

    public void exitPage(BasePage page) {
        back();
    }

    public void showMainPage() {
        MainPage page = new MainPage(frame, this);
        page.setController(this);
        showPage(page);
    }


    public void showLoginPage() {
        LoginPage page = new LoginPage(frame);
        page.setController(this);
        oldPage = currentPage;
        showPage(page);
    }

    public void showSignupPage() {
        SignupPage page = new SignupPage(frame);
        page.setController(this);
        oldPage = currentPage;
        showPage(page);
    }

    public void showResearchPage(Section s, ArrayList<Film> films){
        ResearchResults page;
        if(s == Section.CATEGORY){
            page = new ResearchResults(frame, this, s, ((MainPage) currentPage).getChosenCriterias().get("categorie").get(0), films);
            page.showResearchResults();
            oldPage = currentPage;
            showPage(page);
            return;
        }
        page = new ResearchResults(frame, this, s, null, films);
        page.showResearchResults();
        oldPage = currentPage;
        showPage(page);
    }

    public void showDetailedFilm(Film f){
        //Affichage_Film page = new Affichage_Film(frame, f, getCurrentAccount());
       // page.setController(this); //Certainement à modifier car il va falloir mettre le controller en paramètre
        oldPage = currentPage;
        //showPage(page);
    }

    private void showPage(BasePage page) {
        if(currentPage!=null){
            currentPage.setVisible(false);
        }
        currentPage = page;
        currentPage.setVisible(true);
        frame.add(currentPage);
        frame.setVisible(true);
    }

    public FacadeIHM getFacadeIHM(){
        return facadeIHM;
    }

    public Account getCurrentAccount(){ //Trouver un moyen lorsque utilisateur non connecté de pouvoir avoir un Account par défaut pour faire les vérifications pour la MainPage
        if(currentAccount == null)
            return null;
        return currentAccount;
    }

    public void setAccount(Account acc){
        this.currentAccount = acc;
    }

    // sortie le current page
    private void back() {
        if(currentPage!=null && oldPage!=null){
            currentPage.setVisible(false);
            currentPage = oldPage;
            frame.remove(oldPage);
            oldPage = null;
            frame.add(currentPage);
            currentPage.setVisible(true);
            frame.setVisible(true);
        }
        else{
            System.out.println("Je veux revenir en arrnullière !");
        }
    }
        /*BasePage lastPage = pagePile.pop();
        if(lastPage!=null){
            frame.remove(lastPage);
        }
        curPage = pagePile.getLast();
        if(curPage!=null){
            System.out.println("Back to :["+curPage.getClass()+"]... pile_size:"+pagePile.size());
            curPage.setVisible(true);
        }*/

    public void showFilm() {
        BasePage page = new Affichage_Film(frame);
        page.setController(this);
        showPage(page);
    }
}
