package ui;

import javax.swing.*;

public class BackNavigationBar extends NavigationBar {

    private JButton btnLeft;
    private JButton btnRight;

    public BackNavigationBar(String title){
        super(title);
        btnLeft = new JButton("<--");
        btnRight = new JButton("Aide");
        setLeftComponent(btnLeft);
        setRightComponent(btnRight);
    }

    public JButton getLeftBtn(){
        return btnLeft;
    }

    public JButton getRightBtn(){
        return btnRight;
    }

}
