package dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.BlueRay;
import beans.NormalAccount;
import dao.classes.BlueRayDao;
import dao.tools.Session;

public class BlueRayDaoTest {
	private static Session session;
	private static BlueRayDao blueRayDao;

	@BeforeClass
	public static void setUp() {
		session = new Session(false);
		try {
			session.open();
			blueRayDao = new BlueRayDao(session.get());
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
	public void testGetAllAvailableBlueRays() {

		System.out.println("============================");
		System.out.println("Test Available Blue Ray");
		System.out.println("============================");

		List<BlueRay> results = blueRayDao.getAllAvailableBlueRays();
		assertTrue(results != null);

		for (BlueRay b : results) {
			System.out.println(b);
			System.out.println();
		}
	}

	@Test
	public void testIncrementBlueRay() {

		System.out.println("============================");
		System.out.println("Test Increment Blue Ray");
		System.out.println("============================");


		BlueRay blueRay = new BlueRay();
		blueRay.setId(1);
		blueRay.setAvailableQuantity(5);
		int initialQuantity = blueRay.getAvailableQuantity();
		boolean result = blueRayDao.incrementBlueRayQuantity(blueRay);
		assertTrue(result);

		assertEquals(initialQuantity + 1, blueRay.getAvailableQuantity());
		System.out.println(blueRay);
		System.out.println();
	}

	@Test
	public void testDecrementBlueRay() {

		System.out.println("============================");
		System.out.println("Test Decrement Blue Ray");
		System.out.println("============================");

		BlueRay blueRay = new BlueRay();
		blueRay.setId(1);
		blueRay.setAvailableQuantity(5);
		int initialQuantity = blueRay.getAvailableQuantity();
		boolean result = blueRayDao.decrementBlueRayQuantity(blueRay);
		assertTrue(result);

		assertEquals(initialQuantity - 1, blueRay.getAvailableQuantity());
		System.out.println(blueRay);
		System.out.println();
	}

	@Test
	public void testReportLostBlueRayDisc() {

		System.out.println("============================");
		System.out.println("Test Lost Blue Ray");
		System.out.println("============================");

		NormalAccount account = new NormalAccount();
		account.setId(1);

		BlueRay blueRay = new BlueRay();
		blueRay.setId(1);

		boolean success = blueRayDao.reportLostBlueRayDisc(account, blueRay);
		assertTrue(success);

	}

}
