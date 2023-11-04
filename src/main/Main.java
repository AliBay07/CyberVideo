package main;

import java.sql.SQLException;

import beans.BlueRay;
import beans.Machine;
import dao.tools.DaoFactory;
import dao.tools.Session;

public class Main {

	public static void main(String[] args) {
		
		Session session = new Session(false);
		
		try {

			session.open();

			
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