package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPage extends JFrame {

    public static final int DEF_TF_SIZE = 20;
    public static final String PANEL_BASIC_INFO = "basic_info";
    public static final String PANEL_CARD_INFO = "card_info";

    private String pageTitle = "Signup";
    private JPanel cardPanel;
    private CardLayout cardLayout;

    //
    private JTextField firstnameTF;
    private JTextField lastnameTF;
    private JTextField mailTF;
    private JTextField pwdTF;
    private JButton bi_confirmBtn;

    //
    private JTextField noCardTF;
    private JTextField dateExpTF;
    private JTextField codeSecTF;
    private JButton ci_confirmBtn;
    private JButton ci_cancelBtn;
    private JButton ci_subscribeBtn;

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==bi_confirmBtn){
                // valider
                if(validBasicInfo()){
                    cardLayout.show(cardPanel, PANEL_CARD_INFO);
                }
            }else
            if(e.getSource()==ci_confirmBtn){
                // ajouter carte
                addCard();
            }else
            if(e.getSource()==ci_cancelBtn){
                // plus tard
                SignupPage.this.dispose();
            }else
            if(e.getSource()==ci_subscribeBtn){
                // demande une carte abonnee
                //@TODO à compléter
            }
        }
    };

    public SignupPage(int w, int h) {
        super(SignupPage.class.getSimpleName());
        this.setLayout(new BorderLayout());
        this.setSize(w, h);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initViews();
    }

    private void initViews() {
        NavigationBar navbar = initNavigationBar();

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel basicInfoPanel = createBasicInfoPanel();
        JPanel carteInfoPanel = createCarteInfoPanel();
        cardPanel.add(basicInfoPanel, PANEL_BASIC_INFO);
        cardPanel.add(carteInfoPanel, PANEL_CARD_INFO);

        this.add(navbar, BorderLayout.NORTH);
        this.add(cardPanel, BorderLayout.CENTER);
    }

    public boolean validBasicInfo() {
        String firstname = firstnameTF.getText().trim();
        String lastname = lastnameTF.getText().trim();
        String email = mailTF.getText().trim();
        String pwd = pwdTF.getText().trim();

        System.out.println("== createAccount ==");
        System.out.println("firstname: "+firstname);
        System.out.println("lastname: "+lastname);
        System.out.println("email: "+email);
        System.out.println("pwd: "+pwd);

        //@TODO à compléter

        return true;
    }

    public void addCard() {
        String noCard = noCardTF.getText().trim();
        String dateExpire = dateExpTF.getText().trim();
        String codeSecurity = codeSecTF.getText().trim();

        System.out.println("== addCard ==");
        System.out.println("noCard: "+noCard);
        System.out.println("dateExpire: "+dateExpire);
        System.out.println("codeSecurity: "+codeSecurity);

        //@TODO à compléter
    }

    /**
     * Basic Information Page
     * @return JPanel
     */
    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        firstnameTF = addInputTextView(inputPanel, "Nom", "", DEF_TF_SIZE);
        lastnameTF = addInputTextView(inputPanel, "Prénom", "", DEF_TF_SIZE);
        mailTF = addInputTextView(inputPanel, "Email", "Entrez votre email", DEF_TF_SIZE);
        pwdTF = addInputTextView(inputPanel, "Mot de pass", "Entrez le mot de pass", DEF_TF_SIZE);

        bi_confirmBtn = new JButton("Valider");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel(PANEL_BASIC_INFO), c);

        c.gridy = 1;
        panel.add(inputPanel, c);

        c.gridy = 2;
        panel.add(bi_confirmBtn, c);
        bi_confirmBtn.addActionListener(actionListener);
        return panel;
    }

    /**
     * Carte Information Page
     * @return JPanel
     */
    private JPanel createCarteInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        noCardTF = addInputTextView(inputPanel, "No. de carte", "", DEF_TF_SIZE);
        dateExpTF = addInputTextView(inputPanel, "Date exiprée", "", DEF_TF_SIZE/2);
        codeSecTF = addInputTextView(inputPanel, "Code de sécurité", "", DEF_TF_SIZE);

        JPanel controlPanel = new JPanel();
        ci_confirmBtn = new JButton("Ajouter");
        ci_cancelBtn = new JButton("Plus tard");
        controlPanel.add(ci_confirmBtn);
        controlPanel.add(ci_cancelBtn);

        ci_subscribeBtn = new JButton("Demande carte abonnée");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;
        //c.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel(PANEL_CARD_INFO), c);

        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 3;
        panel.add(inputPanel, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 5;
        c.gridheight = 1;
        panel.add(controlPanel, c);

        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        panel.add(ci_subscribeBtn, c);

        ci_confirmBtn.addActionListener(actionListener);
        ci_cancelBtn.addActionListener(actionListener);
        ci_subscribeBtn.addActionListener(actionListener);
        return panel;
    }

    private NavigationBar initNavigationBar() {
        NavigationBar navbar = new NavigationBar(pageTitle);
        JButton backBtn = new JButton("<--");
        JButton helpBtn = new JButton("Aide");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                SignupPage.this.dispose();
            }
        });

        navbar.setLeftComponent(backBtn);
        navbar.setRightComponent(helpBtn);
        return navbar;
    }

    private JTextField addInputTextView(JPanel container, String label, String tips, int size) {
        JLabel jlabel = new JLabel(label);
        JTextField jtextField = new JTextField(size);
        jtextField.setToolTipText(tips);
        container.add(jlabel);
        container.add(jtextField);
        return jtextField;
    }

    public static void main(String[] args) {
        new SignupPage(500, 400).setVisible(true);
    }

}
