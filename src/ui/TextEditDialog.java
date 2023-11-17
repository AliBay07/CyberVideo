package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextEditDialog implements ActionListener {

    public interface ButtonClickListener {
        public void leftBtnClicked(TextEditDialog dialog);
        public void rightBtnClicked(TextEditDialog dialog);
    }

    private static final String title = "Change first name";
    private static final String tips = "Tips : can not use special charaters.";
    private static final String btnLeftTxt = "SAVE";
    private static final String btnRightTxt = "CANCEL";

    private JDialog dialog;
    private JTextField textField;
    private JButton btnLeft;
    private JButton btnRight;
    private ButtonClickListener listener;

    public TextEditDialog(JFrame frame, String title, String tips, String txt, String btnLeft, String btnRight) {
        JPanel jPanel = initUI(tips, txt, btnLeft, btnRight);
        dialog = new JDialog(frame);
        dialog.setTitle(title);
        dialog.setContentPane(jPanel);
    }

    public void show() {
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void close() {
        dialog.dispose();
    }

    private JPanel initUI(String tips, String txt, String btnLeftTxt, String btnRightTxt) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // tips
        JLabel label = new JLabel(tips);
        gbc.gridy = 0;
        gbc.ipady = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label, gbc);

        // input text
        textField = new JTextField(txt);
        gbc.gridy = 1;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textField, gbc);

        // buttons
        JPanel controlPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 5, 0, 5);
        jPanel.add(controlPane, gbc);

        this.btnLeft = new JButton(btnLeftTxt);
        this.btnRight = new JButton(btnRightTxt);
        controlPane.add(this.btnLeft);
        controlPane.add(this.btnRight);

        // padding
        Insets paddingBtn = new Insets(5, 10, 5, 10);
        this.btnLeft.setMargin(paddingBtn);
        this.btnRight.setMargin(paddingBtn);
        jPanel.setBorder(new EmptyBorder(20, 30, 10, 30));

        // actions
        this.btnLeft.addActionListener(this);
        this.btnRight.addActionListener(this);

        return jPanel;
    }

    public void setButtonClickListener(ButtonClickListener listener) {
        this.listener = listener;
    }

    public String getTextInput() {
        return textField.getText().trim();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnLeft) {
            if(listener!=null){
                listener.leftBtnClicked(this);
            }
        }else
        if(e.getSource()==btnRight) {
            if(listener!=null){
                listener.rightBtnClicked(this);
            }
        }
    }

    public static void main(String[] args) {
        new TextEditDialog(null, title, tips, "Hello", btnLeftTxt, btnRightTxt).show();
    }

}
