package ui;

import facade.ui.FacadeIHM;
import beans.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends BasePage {

    public static final int DEF_TF_SIZE = 18;
    public static final int ITEM_PADDING = 15;
    private static final int FONT_SIZE = 19;

    private JTextField idTF;
    private JTextField pwdTF;
    private JButton loginBtn;
    private JButton signupBtn;

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==loginBtn){
                login();
            }else
            if(e.getSource()==signupBtn){
                dispose();
                signup();
            }
        }
    };

    public LoginPage(JFrame frame) {
        super(frame);
        this.setLayout(new BorderLayout());
        initViews();
    }

    public String getEmail() {
        return idTF.getText().trim();
    }

    public String getPassword() {
        return pwdTF.getText().trim();
    }

    private void login() {
        String id = idTF.getText().trim();
        String pwd = pwdTF.getText().trim();

        System.out.println("== login ==");
        System.out.println("id: "+id);
        System.out.println("pwd: "+pwd);

        Account acc = controller.getFacadeIHM().userLogin(id, pwd);
        if(acc!=null){
            controller.setAccount(acc);
            controller.traite(LoginPage.this, Keyword.LOGIN);
        }
        else{
            //Afficher une JDialog avec une erreur !
            showError("Login failed", "Veuillez vérifier ton mail et ton mot de pass.");
        }
    }

    private void signup() {
        if(controller!=null){
            controller.showSignupPage();
        }
    }

    public void showLoading(boolean isShow) {

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

        Font font = loginBtn.getFont();
        font = new Font(font.getName(), font.getStyle(), FONT_SIZE);
        loginBtn.setFont(font);
        signupBtn.setFont(font);

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
        BackNavigationBar navbar = new BackNavigationBar("Login");
        navbar.getLeftBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                dispose();
            }
        });
        return navbar;
    }

    private JTextField addInputTextView(JPanel container, String label, String tips, int size) {
        Font font = null;
        // label
        JLabel jlabel = new JLabel(label);
        jlabel.setBorder(new EmptyBorder(ITEM_PADDING, 0, 5, 0)); // padding entre component
        font = jlabel.getFont();
        jlabel.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        // text filed
        JTextField jtextField = new JTextField(size);
        jtextField.setToolTipText(tips);
        font = jtextField.getFont();
        jtextField.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        container.add(jlabel);
        container.add(jtextField);
        return jtextField;
    }

    private JTextField addInputPasswordView(JPanel container, String label, String tips, int size) {
        Font font = null;
        // label
        JLabel jlabel = new JLabel(label);
        jlabel.setHorizontalAlignment(SwingConstants.LEADING);
        jlabel.setBorder(new EmptyBorder(ITEM_PADDING, 0, 5, 0)); // padding entre component
        font = jlabel.getFont();
        jlabel.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        // text filed
        JTextField jtextField = new JPasswordField(size);
        jtextField.setToolTipText(tips);
        font = jtextField.getFont();
        jtextField.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        container.add(jlabel);
        container.add(jtextField);
        return jtextField;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(LoginPage.class.getSimpleName());
        frame.add(new LoginPage(frame));
        frame.setSize(1280, 960);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
