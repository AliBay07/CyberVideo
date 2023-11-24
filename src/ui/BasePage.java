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

    public void setController(Controller controller) {
        this.controller = controller;
    }

    protected void dispose() {
        controller.exitPage(this);
    }

}
