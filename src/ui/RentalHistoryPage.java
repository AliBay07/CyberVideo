package ui;

import beans.HistoricReservation;
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

public class RentalHistoryPage extends BasePage {
    JTable table;
    BackListener listener;
    public interface BackListener {
        public void backClicked();
    }

    class MyTableModel implements TableModel {

        static final String[] headers = {"No.", "Film Name", "Rental Date", "Return Date"};
        static final Class[] types = {Integer.class, String.class, Date.class, Date.class};
        static final int numCols = headers.length;

        private ArrayList<HistoricReservation> list;

        public MyTableModel(ArrayList<HistoricReservation> list) {
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
                return list.get(rowIndex).getBlueray().getFilm().getName();
            }
            if (columnIndex == 2) {
                return list.get(rowIndex).getStartReservationDate();
            }
            if (columnIndex == 3) {
                return list.get(rowIndex).getEndReservationDate();
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

    public RentalHistoryPage(JFrame frame) {
        super(frame);
        initViews();
    }

    public RentalHistoryPage(JFrame frame, Controller controller) {
        super(frame, controller);
        initViews();
    }

    private void initViews() {
        NavigationBar navbar = initNavigationBar();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(0, 30, 0, 30));

        JTable table = initTable();
        if(table!=null){
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(new EmptyBorder(30, 0, 30, 0));
            table.setFillsViewportHeight(true);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
        }else{
            contentPanel.add(new JLabel("    <Rental History vide.>"), BorderLayout.CENTER);
        }

        this.setLayout(new BorderLayout());
        this.add(navbar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private JTable initTable() {
        ArrayList<HistoricReservation> list = loadData();
        if(list==null || list.isEmpty()){
            return null;
        }
        MyTableModel model = new MyTableModel(list);
        table = new JTable(model);
        // column size
        table.getColumnModel().getColumn(0).setMaxWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
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

    private ArrayList<HistoricReservation> loadData() {
        ArrayList<HistoricReservation> list = null;
        if(controller!=null){
            list = controller.getFacadeIHM().getHistoricReservationByAccount();
        }
        return list;
    }

    private NavigationBar initNavigationBar() {
        BackNavigationBar navbar = new BackNavigationBar("Rental History");
        navbar.getLeftBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se termine
                RentalHistoryPage.this.setVisible(false);
                if(listener!=null){
                    listener.backClicked();
                }
            }
        });
        return navbar;
    }

    public void setBackListener(BackListener listener) {
        this.listener = listener;
    }

}
