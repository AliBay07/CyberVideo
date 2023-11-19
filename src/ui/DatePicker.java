package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Objects;

public class DatePicker extends JPanel implements ActionListener {

    public interface DateChangListener {
        public void yearChanged(int newYear);
        public void monthChanged(int newMonth);
        public void dayChanged(int newDay);
    }

    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;

    private int oldYear;
    private int oldMonth;
    private int oldDay;

    private DateChangListener dateChangListener;

    private final ActionListener updateDayListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedDay = dayComboBox.getSelectedIndex();
            int selectedMonth = Integer.parseInt((String) Objects.requireNonNull(monthComboBox.getSelectedItem()));
            dayComboBox.setModel(new DefaultComboBoxModel<>(getDaysInMonth(selectedMonth)));
            if(selectedDay<dayComboBox.getItemCount()){
                dayComboBox.setSelectedIndex(selectedDay);
            }
        }
    };

    public DatePicker() {
        placeComponents(this);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel datePane = new JPanel(new GridBagLayout());
        datePane.setBorder(new EmptyBorder(5, 10, 5, 10));
        panel.add(datePane);

        GridBagConstraints gbc = new GridBagConstraints();

        // date
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.ipady = 5;
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        datePane.add(new JLabel("Year:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        yearComboBox = new JComboBox<>(getYears());
        datePane.add(yearComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        datePane.add(new JLabel("Month:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        monthComboBox = new JComboBox<>(getMonths());
        datePane.add(monthComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        datePane.add(new JLabel("Day:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        dayComboBox = new JComboBox<>(getDaysInMonth(1));
        datePane.add(dayComboBox, gbc);

        // Update days when year or month changes
        yearComboBox.addActionListener(updateDayListener);
        monthComboBox.addActionListener(updateDayListener);

        yearComboBox.addActionListener(this);
        monthComboBox.addActionListener(this);
        dayComboBox.addActionListener(this);
    }

    public void setDate(int year, int month, int day) {
        yearComboBox.setSelectedItem(String.valueOf(year));
        monthComboBox.setSelectedItem(String.valueOf(month));
        dayComboBox.setSelectedItem(String.valueOf(day));
        updateUI();
    }

    public int getYearSelected() {
        return Integer.parseInt((String) Objects.requireNonNull(yearComboBox.getSelectedItem()));
    }

    public int getMonthSelected() {
        return Integer.parseInt((String) Objects.requireNonNull(monthComboBox.getSelectedItem()));
    }

    public int getDaySelected() {
        return Integer.parseInt((String) Objects.requireNonNull(dayComboBox.getSelectedItem()));
    }

    public void setDateChangListener(DateChangListener listener) {
        this.dateChangListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==yearComboBox){
            int newYear = getYearSelected();
            if(oldYear!=newYear){
                oldYear = newYear;
                if(dateChangListener!=null){
                    dateChangListener.yearChanged(newYear);
                }
            }
        }else
        if(e.getSource()==monthComboBox){
            int newMonth = getMonthSelected();
            if(oldMonth!=newMonth) {
                oldMonth = newMonth;
                if (dateChangListener != null) {
                    dateChangListener.monthChanged(newMonth);
                }
            }
        }else
        if(e.getSource()==dayComboBox){
            int newDay = getDaySelected();
            if(oldDay!=newDay) {
                oldDay = newDay;
                if (dateChangListener != null) {
                    dateChangListener.dayChanged(newDay);
                }
            }
        }
    }

    private String[] getDaysInMonth(int month) {
        int daysInMonth = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else if (month == 2) {
            // Leap year check
            int selectedYear = Integer.parseInt((String) Objects.requireNonNull(yearComboBox.getSelectedItem()));
            daysInMonth = (selectedYear % 4 == 0 && (selectedYear % 100 != 0 || selectedYear % 400 == 0)) ? 29 : 28;
        }
        String[] days = new String[daysInMonth];
        for (int i = 1; i <= daysInMonth; i++) {
            days[i - 1] = Integer.toString(i);
        }
        return days;
    }

    private String[] getMonths() {
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = Integer.toString(i);
        }
        return months;
    }

    private String[] getYears() {
        String[] years = new String[100];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 99, j = 0; i <= currentYear; i++, j++) {
            years[j] = Integer.toString(i);
        }
        return years;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Date Picker");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DatePicker picker = new DatePicker();
                picker.setDate(1999, 8, 13);
                picker.setDateChangListener(new DateChangListener() {
                    @Override
                    public void yearChanged(int newYear) {
                        System.out.println("Year: "+newYear);
                    }

                    @Override
                    public void monthChanged(int newMonth) {
                        System.out.println("Month: "+newMonth);
                    }

                    @Override
                    public void dayChanged(int newDay) {
                        System.out.println("Day: "+newDay);
                    }
                });
                frame.add(picker);
            }
        });
        frame.setVisible(true);
    }
}
