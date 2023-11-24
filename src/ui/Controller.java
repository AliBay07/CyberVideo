package ui;

import beans.*;
import facade.ui.FacadeIHM;

import javax.swing.*;

/**
 * Controller pour le mode MVC
 */
public class Controller {

    FacadeIHM facadeIHM;
    JFrame frame;
    BasePage currentPage;
    Account currentAccount;
    enum State { IDLE, SIGNIN_NORMAL, SIGNUP_NORMAL, SIGNIN_FOR_RENT, SIGNUP_FOR_RENT, SIGNIN_FOR_SUBSCRIBE, SIGNUP_FOR_SUBSCRIBE, SIGNIN_FOR_RETURN_FILM, LOGGED_NORMAL, LOGGED_PREMIUM, CHANGE_ACCOUNT_SETTING, SHOW_TOP10W_NO_CONNECT, SHOW_TOP10M_NO_CONNECT, SHOW_FILMS_NO_CONNECT, SHOW_BLURAY_NO_CONNECT, SHOW_RESEARCH_RESULTS_NO_CONNECT, SHOW_FILM_DETAILS_NO_CONNECT, SHOW_TOP10W_CONNECT, SHOW_TOP10M_CONNECT, SHOW_FILMS_CONNECT, SHOW_BLURAY_CONNECT, SHOW_RESEARCH_RESULTS_CONNECT, SHOW_FILM_DETAILS_CONNECT, RENT_FILM, SHOW_VALIDATION_RENT, SHOW_ERROR_RENT, SHOW_VALIDATION_SUBSCRIBE, SHOW_RETURN_PAGE, SHOW_VALIDATION_RETURN_PAGE, SHOW_ERROR_RETURN_PAGE};
    State state = State.IDLE;

    public Controller(JFrame frame, FacadeIHM fihm) {
        this.frame = frame;
        facadeIHM = fihm;
    }

    public void traite(BasePage page, Keyword action){
        if(action==Keyword.LOGIN) {
            if(currentAccount instanceof NormalAccount)
                state = State.LOGGED_NORMAL;
            else if(currentAccount instanceof SubscriberAccount)
                state = State.LOGGED_PREMIUM;
            frame.remove(currentPage);
        }
    }

    public void exitPage(BasePage page) {
        back();
    }

    public void showMainPage() {
        MainPage page = new MainPage(frame, this);
        //page.setController(this);
        showPage(page);
    }


    public void showLoginPage() {
        LoginPage page = new LoginPage(frame);
        page.setController(this);
        showPage(page);
    }

    public void showSignupPage() {
        SignupPage page = new SignupPage(frame);
        page.setController(this);
        showPage(page);
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

    public Account getCurrentAccount(){
        return currentAccount;
    }

    public void setAccount(Account acc){
        this.currentAccount = acc;
    }

    // sortie le current page
    private void back() {
        /*BasePage lastPage = pagePile.pop();
        if(lastPage!=null){
            frame.remove(lastPage);
        }
        curPage = pagePile.getLast();
        if(curPage!=null){
            System.out.println("Back to :["+curPage.getClass()+"]... pile_size:"+pagePile.size());
            curPage.setVisible(true);
        }*/
    }
}
