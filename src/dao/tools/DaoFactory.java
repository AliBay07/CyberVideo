package dao.tools;

import java.sql.SQLException;

import dao.classes.*;

public class DaoFactory {

	public static FilmDao getFilmDao() {
		try {
			return new FilmDao(DatabaseConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BlueRayDao getBlueRayDao() {
		try {
			return new BlueRayDao(DatabaseConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static AccountDao getAccountDao() {
		try {
			return new AccountDao(DatabaseConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ReservationDao getReservationsDao() {
		try {
			return new ReservationDao(DatabaseConnection.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
