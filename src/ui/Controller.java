package ui;

import beans.*;
import facade.ui.FacadeIHM;
import javax.swing.*;
import java.util.Stack;

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

    Stack<BasePage> pagePile;   // modifier

    public Controller(JFrame frame, FacadeIHM fihm) {
        this.frame = frame;
        facadeIHM = fihm;
        pagePile = new Stack<>();   // modifier
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

    public void showMainPage() {  // modifier
        BasePage page = null;
        switch (state) {
            case IDLE:   // au début est InitialMainPage
                if(currentPage==null || !(currentPage instanceof InitialMainPage)){
                    clearAllPages();   // remove tous les pages
                    page = new InitialMainPage(frame);
                }
                break;
            case LOGGED_NORMAL:
//                if(currentPage==null || !(currentPage instanceof NormalMainPage)){
//                    clearAllPages();
//                    page = new NormalMainPage(frame);
//                }
                break;
            case LOGGED_PREMIUM:
//                if(currentPage==null || !(currentPage instanceof SubscriberMainPage)){
//                    clearAllPages();
//                    page = new SubscriberMainPage(frame);
//                }
                break;
        }

        if(page!=null){
            page.setController(this);
            showPage(page);
        }
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
        pagePile.add(page);  // modifier
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

    private void clearAllPages() {   // modifier
        pagePile.clear();
        for(Component comp : frame.getComponents()){
            if(!(comp instanceof JRootPane)){   // ne pas supprimer le JRootPane, sinon rien à afficher
                frame.remove(comp);
            }
        }
        currentPage = null;
    }

    // sortie le current page
    private void back() {   // modifier
        BasePage lastPage = pagePile.pop();
        if(lastPage!=null) {
            frame.remove(lastPage);
        }
        currentPage = pagePile.getLast();
        if(currentPage!=null){
            System.out.println("Back to :["+currentPage.getClass()+"]... pile_size:"+pagePile.size());
            currentPage.setVisible(true);
        }
    }
}
