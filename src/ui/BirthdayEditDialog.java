package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthdayEditDialog implements ActionListener {

    public interface ButtonClickListener {
        public void leftBtnClicked(BirthdayEditDialog dialog);
        public void rightBtnClicked(BirthdayEditDialog dialog);
    }

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String title = "Change birthday";
    private static final String tips = "";
    private static final String btnLeftTxt = "SAVE";
    private static final String btnRightTxt = "CANCEL";

    private JDialog dialog;
    private DatePicker picker;
    private JButton btnLeft;
    private JButton btnRight;
    private ButtonClickListener listener;

    public BirthdayEditDialog(JFrame frame, String title, String tips, String txt, String btnLeft, String btnRight) {
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

        // date picker
        picker = new DatePicker();
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date date = dateFormat.parse(txt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            picker.setDate(year, month, day);
        } catch (ParseException e) {
            //e.fillInStackTrace();
        }
        gbc.gridy = 1;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(picker, gbc);

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

    public String getDate() {
        int year = picker.getYearSelected();
        int month = picker.getMonthSelected();
        int day = picker.getDaySelected();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);

        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
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
        new BirthdayEditDialog(null, title, tips, "1999-08-13", btnLeftTxt, btnRightTxt).show();
    }

}
