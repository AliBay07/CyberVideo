package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignupCardInfoPane extends JPanel {

    public static final int DEF_TF_SIZE = 20;

    public static final String FORMAT_NO_CARD = "#### #### #### ####";  // 16 chiffres
    public static final String FORMAT_DATE_EXP = "##/##";  // MM/YY
    public static final String FORMAT_CODE_SEC = "###";    // 3 chiffres

    public static final String PREFIX = SignupCardInfoPane.class.getSimpleName();
    public static final String ACT_ADD_CARD = PREFIX+"_addCard";
    public static final String ACT_CANCEL = PREFIX+"_cancel";
    public static final String ACT_SUBSCRIBE = PREFIX+"_subscribe";

    private JTextField noCardTF;
    private JTextField dateExpTF;
    private JTextField codeSecTF;

    private JButton confirmBtn;
    private JButton cancelBtn;
    private JButton subscribeBtn;

    public SignupCardInfoPane() {
        super(new GridBagLayout());
        JPanel inputPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        inputPanel.setLayout(boxLayout);
        noCardTF = addFormattedInputTextView(inputPanel, "No. de carte", "16 nombres de card", DEF_TF_SIZE, FORMAT_NO_CARD);
        dateExpTF = addFormattedInputTextView(inputPanel, "Date exiprée", "", DEF_TF_SIZE/2, FORMAT_DATE_EXP);
        codeSecTF = addFormattedInputTextView(inputPanel, "Code de sécurité", "", DEF_TF_SIZE/2, FORMAT_CODE_SEC);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        confirmBtn = new JButton("Ajouter");
        confirmBtn.setActionCommand(ACT_ADD_CARD);
        confirmBtn.setMargin(new Insets(5, 10, 5, 10));
        cancelBtn = new JButton("Plus tard");
        cancelBtn.setActionCommand(ACT_CANCEL);
        cancelBtn.setMargin(new Insets(5, 10, 5, 10));
        controlPanel.add(confirmBtn);
        controlPanel.add(cancelBtn);

        subscribeBtn = new JButton("<html>Demande<br>carte abonnée</html>");
        subscribeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        subscribeBtn.setActionCommand(ACT_SUBSCRIBE);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.ipady = 20;
        //c.anchor = GridBagConstraints.CENTER;
        this.add(new JLabel(SignupCardInfoPane.class.getSimpleName()), c);

        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 3;
        //c.insets = new Insets(15, 10, 15, 10);
        this.add(inputPanel, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.insets = new Insets(5, 10, 5, 10);
        this.add(controlPanel, c);

        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.ipadx = 5;
        c.ipady = 0;
        this.add(subscribeBtn, c);
    }

    public void addActionListener(ActionListener listener) {
        confirmBtn.addActionListener(listener);
        cancelBtn.addActionListener(listener);
        subscribeBtn.addActionListener(listener);
    }

    public String getNoCard() {
        return noCardTF.getText().trim();
    }

    public String getDateExpire() {
        return dateExpTF.getText().trim();
    }

    public String getCodeSec() {
        return codeSecTF.getText().trim();
    }

    private JFormattedTextField addFormattedInputTextView(JPanel container, String label, String tips, int size, String format) {
        JLabel jlabel = new JLabel(label);
        jlabel.setBorder(new EmptyBorder(8, 0, 3, 0)); // padding entre component
        JFormattedTextField jtextField = new JFormattedTextField(createFormatter(format));
        jtextField.setToolTipText(tips);
        jtextField.setColumns(size);
        container.add(jlabel);
        container.add(jtextField);
        return jtextField;
    }

    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new SignupCardInfoPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

}
