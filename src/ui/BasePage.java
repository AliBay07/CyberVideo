package ui;

import javax.swing.*;

/**
 * la base de touts les pages
 */
public abstract class BasePage extends JPanel {

    Controller controller;
    JFrame frame;

    public BasePage(JFrame frame) {
        this.frame = frame;
    }

    public BasePage(JFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController(){
        return controller;
    }

    protected void dispose() {
//        controller.exitPage(this);
        controller.traite(this, Keyword.BACK);
    }

    public void showInfo(String title, String msg) {
        showMessage(JOptionPane.DEFAULT_OPTION, title, msg);
    }

    public void showWarning(String title, String msg) {
        showMessage(JOptionPane.WARNING_MESSAGE, title, msg);
    }

    public void showError(String title, String msg) {
        showMessage(JOptionPane.ERROR_MESSAGE, title, msg);
    }

    private void showMessage(int type, String title, String msg) {
        JOptionPane.showMessageDialog(frame, msg, title, type);
    }

}
