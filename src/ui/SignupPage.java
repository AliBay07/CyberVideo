package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPage extends JFrame implements ActionListener {

    public static final String PANEL_BASIC_INFO = "basic_info";
    public static final String PANEL_CARD_INFO = "card_info";

    private String pageTitle = "Signup";
    private JPanel contentPanel;
    private CardLayout cardLayout;

    //
    private SignupBasicInfoPane basicInfoPane;
    private SignupCardInfoPane cardInfoPane;

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

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // panel pour Step1 -- basic info
        basicInfoPane = new SignupBasicInfoPane();
        basicInfoPane.addActionListener(this);

        // panel pour Step2 -- card info
        cardInfoPane = new SignupCardInfoPane();
        cardInfoPane.addActionListener(this);

        contentPanel.add(basicInfoPane, PANEL_BASIC_INFO);
        contentPanel.add(cardInfoPane, PANEL_CARD_INFO);

        this.add(navbar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public boolean validBasicInfo() {
        String firstname = basicInfoPane.getFirstname();
        String lastname = basicInfoPane.getLastname();
        String email = basicInfoPane.getEmail();
        String pwd = basicInfoPane.getPassword();

        System.out.println("== createAccount ==");
        System.out.println("firstname: "+firstname);
        System.out.println("lastname: "+lastname);
        System.out.println("email: "+email);
        System.out.println("pwd: "+pwd);

        //@TODO à compléter

        return true;
    }

    public void addCard() {
        String noCard = cardInfoPane.getNoCard();
        String dateExpire = cardInfoPane.getDateExpire();
        String codeSecurity = cardInfoPane.getCodeSec();

        System.out.println("== addCard ==");
        System.out.println("noCard: "+noCard);
        System.out.println("dateExpire: "+dateExpire);
        System.out.println("codeSecurity: "+codeSecurity);

        //@TODO à compléter
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

    private void showCardInfoPanel() {
        cardLayout.show(contentPanel, PANEL_CARD_INFO);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equals(SignupBasicInfoPane.ACT_CONFIRM)){
            // valider
            if(validBasicInfo()){
                showCardInfoPanel();
            }
        }else
        if(cmd.equals(SignupCardInfoPane.ACT_ADD_CARD)){
            // ajouter carte
            addCard();
        }else
        if(cmd.equals(SignupCardInfoPane.ACT_CANCEL)){
            // plus tard
            SignupPage.this.dispose();
        }else
        if(cmd.equals(SignupCardInfoPane.ACT_SUBSCRIBE)){
            // demande une carte abonnee
            //@TODO à compléter
        }
    }

    public static void main(String[] args) {
        new SignupPage(SysAL2000.SCREEN_WIDTH, SysAL2000.SCREEN_HEIGHT).setVisible(true);
    }

}
