package main;

import java.sql.SQLException;

import beans.BlueRay;
import beans.Machine;
import dao.classes.MachineDao;
import dao.tools.DaoFactory;
import dao.tools.Session;

public class Main {

	public static void main(String[] args) {
		
		Session session = new Session(true);

		try {

			session.open();
			DaoFactory daoFactory = DaoFactory.getInstance();

			MachineDao machineDao = new MachineDao(session.get());
			daoFactory.registerDao(Machine.class, machineDao);

			Machine machine = machineDao.getById(1L);

			if (machine != null) {
				System.out.println("Retrieved Machine from the Database: " + machine);

				BlueRay newBlueRay = new BlueRay();
				machine.addBlueRay(newBlueRay);
			} else {
				System.out.println("Machine not found in the database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}	
}