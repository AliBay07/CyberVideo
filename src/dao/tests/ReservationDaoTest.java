package dao.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.BlueRay;
import beans.Film;
import beans.NormalAccount;
import beans.Reservation;
import dao.classes.ReservationDao;
import dao.tools.Session;

public class ReservationDaoTest {
	private static Session session;
	private static ReservationDao reservationDao;

	@BeforeClass
	public static void setUp() {
		session = new Session(false);
		try {
			session.open();
			reservationDao = new ReservationDao(session.get());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() {
		try {
			session.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCurrentReservationsByAccount() {

		System.out.println("============================");
		System.out.println("Test Current Reservations");
		System.out.println("============================");

		NormalAccount account = new NormalAccount();
		account.setId(1);

		ArrayList<Reservation> results = reservationDao.getCurrentReservationsByAccount(account);
		assertTrue(results != null);

		for (Reservation r : results) {
			System.out.println(r);
			System.out.println();
		}

	}

	@Test
	public void testReserveBlueRay() {

		System.out.println("============================");
		System.out.println("Test Reserve Reservations");
		System.out.println("============================");

		NormalAccount account = new NormalAccount();
		account.setId(1);

		BlueRay blueRay = new BlueRay();
		blueRay.setId(1);

		boolean result = reservationDao.ReserveBlueRay(account, blueRay);
		assertTrue(result);
	}
	
	@Test
	public void testReserveQrCode() {

		System.out.println("============================");
		System.out.println("Test Reserve QrCode");
		System.out.println("============================");

		NormalAccount account = new NormalAccount();
		account.setId(1);

		Film film = new Film();
		film.setId(1);

		boolean result = reservationDao.ReserveQrCode(account, film);
		assertTrue(result);
	}

}
