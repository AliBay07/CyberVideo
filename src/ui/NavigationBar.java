package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * NavigationBar en commune
 */
public class NavigationBar extends JPanel {

    private Component leftComp;
    private Component rightComp;
    private final JLabel titleLabel;

    public NavigationBar() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(8, 10, 8 ,10));
        titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel, BorderLayout.CENTER);
    }

    public NavigationBar(String title) {
        this();
        setTitle(title);
    }

    public NavigationBar(String title, Component leftComp, Component rightComp) {
        this(title);
        setLeftComponent(leftComp);
        setRightComponent(rightComp);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setLeftComponent(Component c) {
        if(leftComp!=null){
            this.remove(leftComp);
        }
        leftComp = c;
        if(leftComp!=null){
            this.add(c, BorderLayout.WEST);
        }
    }

    public void setRightComponent(Component c) {
        if(rightComp!=null){
            this.remove(rightComp);
        }
        rightComp = c;
        if(rightComp!=null) {
            this.add(c, BorderLayout.EAST);
        }
    }

}
