package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordEditDialog implements ActionListener {

    public interface ButtonClickListener {
        public void leftBtnClicked(PasswordEditDialog dialog);
        public void rightBtnClicked(PasswordEditDialog dialog);
    }

    private static final String title = "Change first name";
    private static final String btnLeftTxt = "SAVE";
    private static final String btnRightTxt = "CANCEL";

    private JDialog dialog;
    private JTextField tfOldPwd;
    private JTextField tfNewPwd;
    private JButton btnLeft;
    private JButton btnRight;
    private ButtonClickListener listener;

    public PasswordEditDialog(JFrame frame, String title, String tips, String label1, String label2, String btnLeft, String btnRight) {
        JPanel jPanel = initUI(tips, label1, label2, btnLeft, btnRight);
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

    private JPanel initUI(String tips, String label1, String label2, String btnLeftTxt, String btnRightTxt) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // tips
        JLabel jtips = new JLabel(tips);
        gbc.gridy = 0;
        gbc.ipady = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(jtips, gbc);

        // label1
        JLabel jlabel1 = new JLabel(label1);
        gbc.gridy = 1;
        gbc.ipady = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(jlabel1, gbc);

        // old password
        tfOldPwd = new JPasswordField();
        gbc.gridy = 2;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        jPanel.add(tfOldPwd, gbc);

        // label2
        JLabel jlabel2 = new JLabel(label2);
        gbc.gridy = 3;
        gbc.ipady = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        jPanel.add(jlabel2, gbc);

        // new password
        tfNewPwd = new JPasswordField();
        gbc.gridy = 4;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(tfNewPwd, gbc);

        // buttons
        JPanel controlPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        gbc.gridy = 8;
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

    public String getOldPassword() {
        return tfOldPwd.getText().trim();
    }

    public String getNewPassword() {
        return tfNewPwd.getText().trim();
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
        new PasswordEditDialog(null, title,  "Tips: 8-10 numbers.",
                "Old password:", "New password:", btnLeftTxt, btnRightTxt).show();
    }

}
