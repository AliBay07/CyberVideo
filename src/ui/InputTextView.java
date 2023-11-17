package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

public class InputTextView extends AbstractInputTextView {

    private String format;


    public InputTextView(String label, String tips, int size, int mode) {
        this(label, tips, size, null, mode);
    }

    public InputTextView(String label, String tips, int size, String format, int mode) {
        super(label, tips, size, mode);
        this.format = format;
    }

    public InputTextView(String label, String tips, int size, String format, int mode, Border labelBorder) {
        super(label, tips, size, mode, labelBorder);
        this.format = format;
    }

    @Override
    protected JTextField createTextView() {
        JFormattedTextField textField;
        if(format!=null){
            textField = new JFormattedTextField(createFormatter(format));
        }else{
            textField = new JFormattedTextField();
        }
        return textField;
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new InputTextView("nom:", "input your first name", 30, null, MODE_HORIZONTAL));
        panel.add(new InputTextView("prenom:", "input your last name", 30, null, MODE_HORIZONTAL));
        panel.add(new InputTextView("email:", "input your mail", 30, null, MODE_VERTICAL));
        panel.add(new PasswordTextView("pass word:", "input your pass word", 30, MODE_VERTICAL));

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
