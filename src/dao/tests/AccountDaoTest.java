package dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dao.classes.AccountDao;
import beans.Account;
import beans.CreditCard;
import beans.SubscriberCard;
import beans.User;
import beans.NormalAccount;
import beans.SubscriberAccount;
import dao.tools.Session;

public class AccountDaoTest {
	private static Session session;
	private static AccountDao accountDao;	

	@BeforeClass
	public static void setUp() {
		session = new Session(false);
		try {
			session.open();
			accountDao = new AccountDao(session.get());
			accountDao.insertMockData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() {
		try {
			accountDao.removeMockData();
		} finally {
			try {
				session.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testUserLogin() {
		
		System.out.println("======================================");
		System.out.println("	Test User Login");
		System.out.println("======================================");

		String email = "test@example.com";		
		String password = "123";
		
		Account account = accountDao.userLogin(email, password);

		assertNotNull(account);
		assertEquals(email, account.getEmail());

		if (account instanceof SubscriberAccount) {

			SubscriberAccount subscriberAccount = (SubscriberAccount) account;

			List<SubscriberCard> subscriberCards = subscriberAccount.getSubscriberCards();
			assertNotNull(subscriberCards);
		}
		List<CreditCard> creditCards = account.getCreditCards();
		assertNotNull(creditCards);
		
		System.out.println(account);
		System.out.println();
	}

	@Test
	public void testUserLoginWithCard() {
		
		System.out.println("======================================");
		System.out.println("	Test User Login Card");
		System.out.println("======================================");

		String cardNumber = "SubCard1";
		String password = "123";
		Account account = accountDao.userLoginWithCard(cardNumber, password);

		assertNotNull(account);

		List<CreditCard> creditCards = account.getCreditCards();
		assertNotNull(creditCards);

		List<SubscriberCard> subscriberCards = ((SubscriberAccount) account).getSubscriberCards();
		assertNotNull(subscriberCards);
		
		System.out.println(account);
		System.out.println();

	}
}
