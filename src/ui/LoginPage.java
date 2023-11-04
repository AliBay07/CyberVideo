package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    public static final int DEF_TF_SIZE = 20;

    public LoginPage(int w, int h) {
        super(LoginPage.class.getSimpleName());
        this.setLayout(new BorderLayout());
        this.setSize(w, h);
        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initViews();
    }

    private void initViews() {
        NavigationBar navbar = initNavigationBar();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        JLabel idLabel = new JLabel("Identifiant:");
        JTextField idTF = new JTextField(DEF_TF_SIZE);
        idTF.setToolTipText("Entrez votre id");
        JLabel pwLabel = new JLabel("Mot de pass:");
        JTextField pwTF = new JTextField(DEF_TF_SIZE);
        pwTF.setToolTipText("Entrez le mot de pass");
        inputPanel.add(idLabel);
        inputPanel.add(idTF);
        inputPanel.add(pwLabel);
        inputPanel.add(pwTF);

        JButton loginBtn = new JButton("Login");
        JLabel sepLabel = new JLabel("----------------------");
        JButton signupBtn = new JButton("Signup");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.CENTER;
        contentPanel.add(inputPanel, c);

        c.gridy = 1;
        contentPanel.add(loginBtn, c);

        c.gridy = 2;
        contentPanel.add(sepLabel, c);

        c.gridy = 3;
        contentPanel.add(signupBtn, c);

        this.add(navbar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private NavigationBar initNavigationBar() {
        NavigationBar navbar = new NavigationBar("Login");
        JButton backBtn = new JButton("<--");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                LoginPage.this.dispose();
            }
        });
        navbar.setLeftComponent(backBtn);
        return navbar;
    }

    public static void main(String[] args) {
        new LoginPage(500, 400).setVisible(true);
    }

}
