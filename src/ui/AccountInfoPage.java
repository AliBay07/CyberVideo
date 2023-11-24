package ui;

import facade.ui.Account;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountInfoPage extends BasePage implements ActionListener {

    public static final int DEF_TF_SIZE = 30;
    public static final int ITEM_PADDING = 15;
    private static final int FONT_SIZE = 19;

    private String pageTitle = "Account Information";

    private Font font = new Font("Dialog", Font.PLAIN, FONT_SIZE);

    private static final String ACT_EDIT_FIRST_NAME = "edit_first_name";
    private static final String ACT_EDIT_LAST_NAME = "edit_last_name";
    private static final String ACT_EDIT_EMAIL = "edit_email";
    private static final String ACT_EDIT_PASSWORD = "edit_password";
    private static final String ACT_EDIT_BIRTHDAY = "edit_birthday";
    private static final String ACT_TOGGLE_ABONNE = "toggle_abonne";

    private JTextField tfId;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfEmail;
    private JTextField tfBirthday;
    private JTextField tfState;
    private String password;

    public AccountInfoPage(JFrame frame){
        super(frame);
        this.setLayout(new BorderLayout());
        initViews();
    }

    private void initViews() {
        NavigationBar navbar = initNavigationBar();
        this.add(navbar, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        int gridY = 0;
        GridBagConstraints gbc = new GridBagConstraints();

        // basic info
        tfId = addFormattedEditTextView(contentPanel, "ID", "", DEF_TF_SIZE/2,
                "", false, null, this,0, gridY++);
        tfFirstName = addFormattedEditTextView(contentPanel, "First name", "", DEF_TF_SIZE/2,
                "", true, ACT_EDIT_FIRST_NAME, this,0, gridY++);
        tfLastName = addFormattedEditTextView(contentPanel, "Last name", "", DEF_TF_SIZE/2,
                "", true, ACT_EDIT_LAST_NAME, this,0, gridY++);
        tfBirthday = addFormattedEditTextView(contentPanel, "Birthday", "", DEF_TF_SIZE/2,
                "####-##-##", true, ACT_EDIT_BIRTHDAY, this,0, gridY++);

        // separator
        addSeparator(contentPanel, 0, gridY++);

        // email
        tfEmail = addFormattedEditTextView(contentPanel, "Email", "", DEF_TF_SIZE/2,
                "", false, ACT_EDIT_EMAIL, this,0, gridY++);
        // edit button
        JButton emailBtn = new JButton("edit email");
        JButton pwdBtn = new JButton("edit password");
        gbc.gridx = 1;
        gbc.gridy = gridY++;
        gbc.gridwidth = 1;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(emailBtn, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(pwdBtn, gbc);

        // separator
        addSeparator(contentPanel, 0, gridY++);

        // state
        tfState = addFormattedEditTextView(contentPanel, "State", "", DEF_TF_SIZE/2,
                "", false, null, this, 0, gridY++);
        JButton abonnetBtn = new JButton("Abonner");
        gbc.gridx = 1;
        gbc.gridy = gridY++;
        gbc.gridwidth = 2;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(abonnetBtn, gbc);

        // actions
        emailBtn.setActionCommand(ACT_EDIT_EMAIL);
        emailBtn.addActionListener(this);
        pwdBtn.setActionCommand(ACT_EDIT_PASSWORD);
        pwdBtn.addActionListener(this);
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

    private void addSeparator(JPanel container, int gridX, int gridY) {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setBackground(Color.BLUE);
        sep.setBackground(new Color(153,153,153));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.ipady = 10;
        gbc.insets = new Insets(15, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(sep, gbc);
    }

    private JTextField addFormattedEditTextView(JPanel container, String label, String tips, int size, String format,
                                                boolean editable, String act_name, ActionListener listener,
                                                int gridX, int gridY) {
        Font font = null;
        // label
        JLabel jlabel = new JLabel(label);
        jlabel.setBorder(new EmptyBorder(0, 5, 0, 10)); // padding entre component
        jlabel.setVerticalTextPosition(SwingConstants.CENTER);
        font = jlabel.getFont();
        jlabel.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        // text filed
        JTextField jtextField;
        if(format==null || format.isEmpty()){
            jtextField = new JTextField();
        } else {
            jtextField = new JFormattedTextField(createFormatter(format));
        }
        jtextField.setColumns(size);
        jtextField.setToolTipText(tips);
        font = jtextField.getFont();
        jtextField.setFont(new Font(font.getName(), font.getStyle(), FONT_SIZE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(ITEM_PADDING, 0, 5, 20);
        gbc.ipady = 5;
        container.add(jlabel, gbc);

        gbc.gridx = gridX+1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(ITEM_PADDING, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        container.add(jtextField, gbc);

        // button
        if(editable){
            gbc.gridx = gridX+3;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(ITEM_PADDING, 20, 5, 0);
            JButton btn = new JButton("edit");
            btn.setActionCommand(act_name);
            if(listener!=null){
                btn.addActionListener(listener);
            }
            container.add(btn, gbc);
        }
        jtextField.setEditable(editable);
        jtextField.setEnabled(false);

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
        frame.add(new AccountInfoPage(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 960);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String cmd = e.getActionCommand();
        if(cmd.equals(ACT_EDIT_FIRST_NAME)){
            showFirstNameEditDialog();
        }else
        if(cmd.equals(ACT_EDIT_LAST_NAME)){
            showLastNameEditDialog();
        }else
        if(cmd.equals(ACT_EDIT_BIRTHDAY)){
            showBirthdayEditDialog();
        }else
        if(cmd.equals(ACT_EDIT_EMAIL)){
            showEmailEditDialog();
        }else
        if(cmd.equals(ACT_EDIT_PASSWORD)){
            showPasswordEditDialog();
        }
    }

    private void showFirstNameEditDialog() {
        TextEditDialog dialogFirstname = new TextEditDialog(frame, "Change first name",
                "Tips : can not use special charaters.", tfFirstName.getText(), "SAVE", "CANCEL");
        dialogFirstname.setButtonClickListener(new TextEditDialog.ButtonClickListener() {
            @Override
            public void leftBtnClicked(TextEditDialog dialog) {
                String newFirstname = dialog.getTextInput();
                tfFirstName.setText(newFirstname);
                dialog.close();
                changeFirstname(newFirstname);
            }

            @Override
            public void rightBtnClicked(TextEditDialog dialog) {
                dialog.close();
            }
        });
        dialogFirstname.show();
    }

    private void showLastNameEditDialog() {
        TextEditDialog dialogLastname = new TextEditDialog(frame, "Change last name",
                "Tips : can not use special charaters.", tfLastName.getText(), "SAVE", "CANCEL");
        dialogLastname.setButtonClickListener(new TextEditDialog.ButtonClickListener() {
            @Override
            public void leftBtnClicked(TextEditDialog dialog) {
                String newLastname = dialog.getTextInput();
                tfLastName.setText(newLastname);
                dialog.close();
                changeLastname(newLastname);
            }

            @Override
            public void rightBtnClicked(TextEditDialog dialog) {
                dialog.close();
            }
        });
        dialogLastname.show();
    }

    private void showBirthdayEditDialog() {
        BirthdayEditDialog dialogBirthday = new BirthdayEditDialog(frame, "Change Birthday",
                "", tfBirthday.getText(), "SAVE", "CANCEL");
        dialogBirthday.setButtonClickListener(new BirthdayEditDialog.ButtonClickListener() {
            @Override
            public void leftBtnClicked(BirthdayEditDialog dialog) {
                String newBirthday = dialog.getDate();
                tfBirthday.setText(newBirthday);
                dialog.close();
                changeBirthday(newBirthday);
            }

            @Override
            public void rightBtnClicked(BirthdayEditDialog dialog) {
                dialog.close();
            }
        });
        dialogBirthday.show();
    }

    private void showEmailEditDialog() {
        TextEditDialog dialogEmail = new TextEditDialog(frame, "Change Email",
                "Input your new email address.", tfEmail.getText(), "SAVE", "CANCEL");
        dialogEmail.setButtonClickListener(new TextEditDialog.ButtonClickListener() {
            @Override
            public void leftBtnClicked(TextEditDialog dialog) {
                String newMail = dialog.getTextInput();
                tfEmail.setText(newMail);
                dialog.close();
                changeEmail(newMail);
            }

            @Override
            public void rightBtnClicked(TextEditDialog dialog) {
                dialog.close();
            }
        });
        dialogEmail.show();
    }

    private void showPasswordEditDialog() {
        PasswordEditDialog dialogPassword = new PasswordEditDialog(frame, "Change Email",
                "Tips: 8-10 numbers.", "Old password:", "New password:", "SAVE", "CANCEL");
        dialogPassword.setButtonClickListener(new PasswordEditDialog.ButtonClickListener() {
            @Override
            public void leftBtnClicked(PasswordEditDialog dialog) {
                String oldPassword = dialogPassword.getOldPassword();
                String newPassword = dialogPassword.getNewPassword();
                if(isOldPasswordCorrect(oldPassword)){
                    changePassword(newPassword);
                    dialog.close();
                }else{
                    //@TODO à compléter
                    System.out.println("OldPassword not right !");
                }
            }

            @Override
            public void rightBtnClicked(PasswordEditDialog dialog) {
                dialog.close();
            }
        });
        dialogPassword.show();
    }

    private void changeFirstname(String newFirstname) {
        Account acc = controller.getFacadeIHM().modifyAccountInformation(newFirstname, tfLastName.getName(), password);
        if(acc!=null){
            controller.setAccount(acc);
            controller.traite(this, Keyword.CHANGE_ACCOUNT_INFO);
        }else{
            showError("Change Firstname failed", "Veuillez vérifier votre entrée.");
        }
    }

    private void changeLastname(String newLastname) {
        Account acc = controller.getFacadeIHM().modifyAccountInformation(tfFirstName.getName(), newLastname, password);
        if(acc!=null){
            controller.setAccount(acc);
            controller.traite(this, Keyword.CHANGE_ACCOUNT_INFO);
        }else{
            showError("Change Lastname failed", "Veuillez vérifier votre entrée.");
        }
    }

    private void changeBirthday(String birthday) {
        // manque une fonction correspondant ?
    }

    private void changeEmail(String newMail) {
        // manque une fonction correspondant ?
    }

    private void changePassword(String newPassword) {
        password = newPassword;
        Account acc = controller.getFacadeIHM().modifyAccountInformation(tfFirstName.getName(), tfLastName.getName(), newPassword);
        if(acc!=null){
            controller.setAccount(acc);
            controller.traite(this, Keyword.CHANGE_ACCOUNT_INFO);
        }else{
            showError("Change password failed", "Veuillez vérifier votre entrée.");
        }
    }

    private boolean isOldPasswordCorrect(String oldPassword) {
        //@TODO à compléter
        return true;
    }

}
