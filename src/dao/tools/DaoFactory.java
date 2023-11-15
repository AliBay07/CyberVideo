package dao.tools;

import dao.classes.*;

public class DaoFactory {

	public static FilmDao getFilmDao() {
		return new FilmDao(DatabaseConnection.getConnection());
	}
	
	public static BlueRayDao getBlueRayDao() {
		return new BlueRayDao(DatabaseConnection.getConnection());
	}
	
	public static AccountDao getAccountDao() {
		return new AccountDao(DatabaseConnection.getConnection());
	}
	
	public static ReservationDao getReservationsDao() {
		return new ReservationDao(DatabaseConnection.getConnection());
	}

}
