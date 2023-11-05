package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    public static final int DEF_TF_SIZE = 25;

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
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        idTF = addInputTextView(inputPanel, "Identifiant:", "Entrez votre id", DEF_TF_SIZE);
        pwdTF = addInputPasswordView(inputPanel, "Mot de pass:", "Entrez le mot de pass", DEF_TF_SIZE);

        loginBtn = new JButton("Login");
        JLabel sepLabel = new JLabel("----------------------");
        signupBtn = new JButton("Signup");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.ipady = 15;
        c.anchor = GridBagConstraints.CENTER;
        contentPanel.add(inputPanel, c);

        c.gridy = 1;
        c.ipady = 10;
        c.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(loginBtn, c);

        c.gridy = 2;
        c.insets = new Insets(5, 10, 5, 10);
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

    private JTextField addInputTextView(JPanel container, String label, String tips, int size) {
        JLabel jlabel = new JLabel(label);
        jlabel.setHorizontalAlignment(SwingConstants.LEADING);
        jlabel.setBorder(new EmptyBorder(8, 0, 3, 0)); // padding entre component
        JTextField jtextField = new JTextField(size);
        jtextField.setToolTipText(tips);
        container.add(jlabel);
        container.add(jtextField);
        return jtextField;
    }

    private JTextField addInputPasswordView(JPanel container, String label, String tips, int size) {
        JLabel jlabel = new JLabel(label);
        jlabel.setHorizontalAlignment(SwingConstants.LEADING);
        jlabel.setBorder(new EmptyBorder(8, 0, 3, 0)); // padding entre component
        JTextField jtextField = new JPasswordField(size);
        jtextField.setToolTipText(tips);
        container.add(jlabel);
        container.add(jtextField);
        return jtextField;
    }

    public static void main(String[] args) {
        new LoginPage(SysAL2000.SCREEN_WIDTH, SysAL2000.SCREEN_HEIGHT).setVisible(true);
    }

}
