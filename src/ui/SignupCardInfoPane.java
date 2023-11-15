package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignupCardInfoPane extends JPanel {

    public static final int DEF_TF_SIZE = 30;
    public static final int ITEM_PADDING = 15;
    private static final int FONT_SIZE = 19;

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

        // input text
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        noCardTF = addFormattedInputTextView(inputPanel, "No. de carte", "16 nombres de card", DEF_TF_SIZE/2, FORMAT_NO_CARD, 0, 0);
        dateExpTF = addFormattedInputTextView(inputPanel, "Date exiprée", "", 6, FORMAT_DATE_EXP, 0, 2);
        codeSecTF = addFormattedInputTextView(inputPanel, "Code de sécurité", "", 4, FORMAT_CODE_SEC, 0, 4);

        // control button
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        confirmBtn = new JButton("Ajouter");
        confirmBtn.setActionCommand(ACT_ADD_CARD);
        cancelBtn = new JButton("Plus tard");
        cancelBtn.setActionCommand(ACT_CANCEL);
        controlPanel.add(confirmBtn);
        controlPanel.add(cancelBtn);

        // subscribe button
        subscribeBtn = new JButton("<html>Demande<br>carte abonnée</html>");
        subscribeBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        subscribeBtn.setActionCommand(ACT_SUBSCRIBE);

        // size et font
        confirmBtn.setMargin(new Insets(8, 12, 8, 12));
        cancelBtn.setMargin(new Insets(8, 12, 8, 12));
        subscribeBtn.setMargin(new Insets(8, 12, 8, 12));

        Font font = confirmBtn.getFont();
        font = new Font(font.getName(), font.getStyle(), FONT_SIZE);
        confirmBtn.setFont(font);
        cancelBtn.setFont(font);
        subscribeBtn.setFont(font);

        // position
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.ipady = 20;
        //c.anchor = GridBagConstraints.CENTER;
        this.add(new JLabel(SignupCardInfoPane.class.getSimpleName()), c);

        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 6;
        //c.insets = new Insets(15, 10, 15, 10);
        this.add(inputPanel, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.insets = new Insets(5, 10, 5, 10);
        this.add(controlPanel, c);

        c.gridx = 4;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 5;
        c.ipady = 0;
        c.insets = new Insets(0, 50, 0, 10);
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

    private JFormattedTextField addFormattedInputTextView(JPanel container, String label, String tips, int size, String format, int gridX, int gridY) {
        Font font = null;
        // label
        JLabel jlabel = new JLabel(label);
        jlabel.setBorder(new EmptyBorder(ITEM_PADDING, 0, 5, 0)); // padding entre component
        font = jlabel.getFont();
        jlabel.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));
        // text filed
        JFormattedTextField jtextField = new JFormattedTextField(createFormatter(format));
        jtextField.setColumns(size);
        jtextField.setToolTipText(tips);
        font = jtextField.getFont();
        jtextField.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.ipady = 5;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(jlabel, gbc);
        gbc.gridy = gridY+1;
        container.add(jtextField, gbc);
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
        frame.setSize(1280, 960);
        frame.setVisible(true);
    }

}
