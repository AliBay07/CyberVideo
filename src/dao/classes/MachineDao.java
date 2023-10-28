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

    @Override
    public void create(Machine machine) {
        machineList.add(machine);
    }

    @Override
    public Machine getById(Long id) {
        for (Machine machine : machineList) {
            if (machine.getId() == id) {
                return machine;
            }
        }
        return null;
    }

    @Override
    public void update(Machine machine) {
        for (int i = 0; i < machineList.size(); i++) {
            Machine existingMachine = machineList.get(i);
            if (existingMachine.getId() == machine.getId()) {
                machineList.set(i, machine);
                return;
            }
        }
    }

    @Override
    public void delete(Machine machine) {
        machineList.removeIf(m -> m.getId() == machine.getId());
    }

    @Override
    public List<Machine> getAll() {
        return new ArrayList<>(machineList);
    }
}

