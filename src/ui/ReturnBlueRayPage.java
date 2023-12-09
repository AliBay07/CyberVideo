package ui;

import beans.BlueRay;
import beans.Film;
import beans.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

public class ReturnBlueRayPage extends BasePage {
    JTable table;

    class MyTableModel implements TableModel {

        static final String[] headers = {"No.", "Film name", "Reservation Date"};
        static final Class[] types = {Integer.class, String.class, Date.class};
        static final int numCols = headers.length;

        private ArrayList<Reservation> list;

        public MyTableModel(ArrayList<Reservation> list) {
            this.list = list;
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return numCols;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return headers[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return types[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return rowIndex + 1;
            }
            if (columnIndex == 1) {
                return list.get(rowIndex).getFilm().getName();
            }
            if (columnIndex == 2) {
                return list.get(rowIndex).getStartReservationDate();
            }
            return null;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
        }

        public Reservation removeRow(int index) {
            return list.remove(index);
        }
    }

    public ReturnBlueRayPage(JFrame frame, Controller controller) {
        super(frame, controller);
        initViews();
    }

    private void initViews() {
        NavigationBar navbar = initNavigationBar();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel topInfoText = new JLabel("Films à rendre :");
        topInfoText.setBorder(new EmptyBorder(15, 0, 0, 0));

        JTable table = initTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(30, 0, 30, 0));
        table.setFillsViewportHeight(true);

        JLabel bottomInfoTxt = new JLabel("Please only put one movie at a time :", SwingConstants.LEFT);
        JButton btnStart = new JButton("Start");
        JPanel controlPane = new JPanel();
        controlPane.add(btnStart, CENTER_ALIGNMENT);
        JPanel bottomPane = new JPanel(new BorderLayout());
        bottomPane.add(bottomInfoTxt, BorderLayout.NORTH);
        bottomPane.add(controlPane, BorderLayout.SOUTH);

        contentPanel.add(topInfoText, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(bottomPane, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(navbar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);

        // action
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRent();
            }
        });
    }

    private void startRent() {
//        if(list==null || list.isEmpty()){
//            showWarning("Warning", "Not movie can rent.");
//            return;
//        }

        int size = 3; //list.size();
        Object[] possibilities = new Object[size+2];
        for(int i=0; i<possibilities.length; i++){  // les deux derniers juste pour tester un cas error
            possibilities[i] = (i+1);
        }
        int id = (int)JOptionPane.showInputDialog(this, "Input a movie to rent:",
                "Rent film", JOptionPane.PLAIN_MESSAGE, null, possibilities, 1);
        if(id>size){
            // error
            showError("Error",
                    String.format("Movie <%d> unrecognizable !" +
                            "\nplease get your film back and try one more time.", id));
        }else{
            // success
            Reservation r = ((MyTableModel)table.getModel()).removeRow(id-1);
            if(returnFilm(r.getFilm())){
                showInfo("Success", String.format("Movie <%d> %s returned",
                        id, r.getFilm().getName()));
                controller.traite(this, Keyword.BLURAY_RETURNED);
            }else{
                showError("Error", String.format("Movie <%d> %s can not return.\n contact xxx.",
                        id, r.getFilm().getName()));
            }
        }
    }

    private boolean returnFilm(Film film) {
        //Un peu bizarre, return un film il faut parcourir tous les blueRay pour le retrouver
        ArrayList<BlueRay> allBlueRay = controller.getFacadeIHM().getAvailableBlueRays();
        for(BlueRay item : allBlueRay){
            if(item.getFilm().getId()==film.getId()){
                return controller.getFacadeIHM().returnBlueRay(item);
            }
        }
        return false;
    }

    private JTable initTable() {
        ArrayList<Reservation> list = loadData();
        MyTableModel model = new MyTableModel(list);
        table = new JTable(model);
        // column size
        table.getColumnModel().getColumn(0).setMaxWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.setRowHeight(35);
        // centraliser header
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        // centraliser content
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,r);

        table.setFocusable(false);
        return table;
    }

    private ArrayList<Reservation> loadData() {
        return new ArrayList<>();//controller.getFacadeIHM().getCurrentReservationsByAccount();
    }

    private NavigationBar initNavigationBar() {
        BackNavigationBar navbar = new BackNavigationBar("Return Movie");
        navbar.getLeftBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                dispose();
            }
        });
        return navbar;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame(ReturnFilmPage.class.getSimpleName());
//        frame.add(new ReturnFilmPage(frame));
//        frame.setSize(1280, 960);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setVisible(true);
//    }

}
