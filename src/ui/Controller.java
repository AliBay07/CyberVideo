package ui;

import facade.ui.Account;
import facade.ui.FacadeIHM;

import javax.swing.*;
import java.util.Stack;

/**
 * Controller pour le mode MVC
 */
public class Controller implements PageActionListener {

    FacadeIHM facadeIHM;
    JFrame frame;

    BasePage oldPage;
    BasePage curPage;

    Stack<BasePage> pagePile;

    public Controller(JFrame frame, FacadeIHM fihm) {
        this.frame = frame;
        facadeIHM = fihm;
        pagePile = new Stack<>();
    }

    @Override
    public void login(BasePage page) {
        if(!(page instanceof LoginPage)){
            return;
        }
        LoginPage loginPage = (LoginPage)page;
        loginPage.showLoading(true);

        String email = loginPage.getEmail();
        String password = loginPage.getPassword();
        if(!userLogin(email, password)){
            loginPage.showLoading(false);
            loginPage.showError();
        }

        // réussi
        loginPage.showLoading(false);
        back();
    }

    @Override
    public void exitPage(BasePage page) {
        back();
    }

    private boolean userLogin(String email, String password) {
        Account acount = facadeIHM.userLogin(email, password);
        // pour le page, il n'a pas besoin
        if(acount==null){
            return false;
        }
        return true;
    }

    public void showMainPage() {
        InitialMainPage page = new InitialMainPage(frame);
        page.setController(this);
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
        if(curPage!=null){
            curPage.setVisible(false);
        }
        oldPage = curPage;
        curPage = page;
        pagePile.add(page);
        System.out.println("Show :["+page.getClass()+"]... pile_size:"+pagePile.size());

        page.setVisible(true);
        frame.add(page);
        frame.setVisible(true);
    }

    // sortie le current page
    private void back() {
        BasePage lastPage = pagePile.pop();
        if(lastPage!=null){
            frame.remove(lastPage);
        }
        curPage = pagePile.getLast();
        if(curPage!=null){
            System.out.println("Back to :["+curPage.getClass()+"]... pile_size:"+pagePile.size());
            curPage.setVisible(true);
        }
    }

}
