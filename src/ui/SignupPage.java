package ui;

import beans.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPage extends BasePage implements ActionListener {

    public static final String PANEL_BASIC_INFO = "basic_info";
    public static final String PANEL_CARD_INFO = "card_info";

    private String pageTitle = "Signup";
    private JPanel contentPanel;
    private CardLayout cardLayout;

    //
    private SignupBasicInfoPane basicInfoPane;
    private SignupCardInfoPane cardInfoPane;

    public SignupPage(JFrame frame) {
        super(frame);
        this.setLayout(new BorderLayout());
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

        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
//            user.setDateOfBirth(basicInfoPane.get);
        boolean isOk = controller.getFacadeIHM().createUserAccount(user, email, pwd);
        if(isOk) {
            controller.traite(this, Keyword.SIGNUP);
        } else {
            showError("Signup failed", "Veuillez vérifier vos informations données.");
        }
        return isOk;
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
        BackNavigationBar navbar = new BackNavigationBar(pageTitle);
        navbar.getLeftBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                dispose();
            }
        });
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
            dispose();
        }else
        if(cmd.equals(SignupCardInfoPane.ACT_SUBSCRIBE)){
            // demande une carte abonnee
            //@TODO à compléter
            if(controller!=null){
                // manque une fonction to addCard ?
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(SignupPage.class.getSimpleName());
        frame.add(new SignupPage(frame));
        frame.setSize(1280, 960);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
//        SysAL2000.getInstance().showSignupPage();
    }

}
