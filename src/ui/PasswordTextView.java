package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

public class PasswordTextView extends AbstractInputTextView {

    private JPasswordField passwordField;

    public PasswordTextView(String label, String tips, int size, int mode) {
        super(label, null, size, mode);
    }

    @Override
    protected JTextField createTextView() {
        passwordField = new JPasswordField();
        return passwordField;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new PasswordTextView("pass word:", "input your pass word", 10, MODE_VERTICAL));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
