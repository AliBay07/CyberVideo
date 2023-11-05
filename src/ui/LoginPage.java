package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    public static final int DEF_TF_SIZE = 20;

    private JTextField idTF;
    private JTextField pwdTF;
    private JButton loginBtn;
    private JButton signupBtn;

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==loginBtn){
                // login
                if(login()){
                    LoginPage.this.dispose();
                }
            }else
            if(e.getSource()==signupBtn){
                // signup
                SysAL2000.showSignupPage();
            }
        }
    };

    public LoginPage(int w, int h) {
        super(LoginPage.class.getSimpleName());
        this.setLayout(new BorderLayout());
        this.setSize(w, h);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initViews();
    }

    private boolean login() {
        String id = idTF.getText().trim();
        String pwd = pwdTF.getText().trim();

        System.out.println("== login ==");
        System.out.println("id: "+id);
        System.out.println("pwd: "+pwd);

        //@TODO à compléter

        return true;
    }

    private void initViews() {
        NavigationBar navbar = initNavigationBar();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        JLabel idLabel = new JLabel("Identifiant:");
        idTF = new JTextField(DEF_TF_SIZE);
        idTF.setToolTipText("Entrez votre id");
        JLabel pwLabel = new JLabel("Mot de pass:");
        pwdTF = new JTextField(DEF_TF_SIZE);
        pwdTF.setToolTipText("Entrez le mot de pass");
        inputPanel.add(idLabel);
        inputPanel.add(idTF);
        inputPanel.add(pwLabel);
        inputPanel.add(pwdTF);

        loginBtn = new JButton("Login");
        JLabel sepLabel = new JLabel("----------------------");
        signupBtn = new JButton("Signup");

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

        loginBtn.addActionListener(actionListener);
        signupBtn.addActionListener(actionListener);

        this.add(navbar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private NavigationBar initNavigationBar() {
        NavigationBar navbar = new NavigationBar("Login");
        JButton backBtn = new JButton("<--");
        JButton helpBtn = new JButton("Aide");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                LoginPage.this.dispose();
            }
        });

        navbar.setLeftComponent(backBtn);
        navbar.setRightComponent(helpBtn);
        return navbar;
    }

    public static void main(String[] args) {
        new LoginPage(SysAL2000.SCREEN_WIDTH, SysAL2000.SCREEN_HEIGHT).setVisible(true);
    }

}
