package dao.classes;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import beans.Machine;

public class MachineDao extends Dao<Machine> {

    private ArrayList<Machine> machineList;

    public MachineDao(Connection connection) {
        super(connection);
        machineList = new ArrayList<>();
    }
}

