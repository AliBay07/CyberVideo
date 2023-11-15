package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignupBasicInfoPane extends JPanel {

    public static final int DEF_TF_SIZE = 22;
    public static final int ITEM_PADDING = 15;
    private static final int FONT_SIZE = 19;

    public static final String PREFIX = SignupBasicInfoPane.class.getSimpleName();
    public static final String ACT_CONFIRM = PREFIX+"_confirm";

    private JTextField firstnameTF;
    private JTextField lastnameTF;
    private JTextField mailTF;
    private JTextField pwdTF;
    private JButton confirmBtn;

    public SignupBasicInfoPane() {
        super(new GridBagLayout());
        // input text
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        firstnameTF = addInputTextView(inputPanel, "Nom", "", DEF_TF_SIZE);
        lastnameTF = addInputTextView(inputPanel, "Prénom", "", DEF_TF_SIZE);
        mailTF = addInputTextView(inputPanel, "Email", "Entrez votre email", DEF_TF_SIZE);
        pwdTF = addInputPasswordView(inputPanel, "Mot de pass", "Entrez le mot de pass", DEF_TF_SIZE);

        // button
        confirmBtn = new JButton("Valider");
        Font font = confirmBtn.getFont();
        confirmBtn.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));
        confirmBtn.setActionCommand(ACT_CONFIRM);

        // position
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.ipady = 25;
        c.anchor = GridBagConstraints.CENTER;
        this.add(new JLabel(SignupBasicInfoPane.class.getSimpleName()), c);

        c.gridy = 1;
        c.insets = new Insets(15, 10, 15, 10);
        this.add(inputPanel, c);

        c.gridy = 2;
        c.ipady = 10;
        c.insets = new Insets(8, 12, 8, 12);
        this.add(confirmBtn, c);
    }

    public void addActionListener(ActionListener listener) {
        confirmBtn.addActionListener(listener);
    }

    public String getFirstname() {
        return firstnameTF.getText().trim();
    }

    public String getLastname() {
        return lastnameTF.getText().trim();
    }

    public String getEmail() {
        return mailTF.getText().trim();
    }

    public String getPassword() {
        return pwdTF.getText().trim();
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
        JFrame frame = new JFrame();
        frame.add(new SignupBasicInfoPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 960);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
