package dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dao.classes.AccountDao;
import beans.Account;
import beans.Category;
import beans.CreditCard;
import beans.SubscriberCard;
import beans.User;
import beans.NormalAccount;
import beans.SubscriberAccount;
import dao.tools.Session;

/**
 * Any test which requires inserting / deleting / updating values from the database won't work without inserting mock data 
 * into the database, the functions are kept in case we want to test each function separately later on, but running this
 * JUnit test will most likely fail without adding mock data to the DB!!!
 */

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
	
	// ============================================================================================================
	// 							Need DataBase manipulations in order to test!!!
	// ============================================================================================================

	
//    @Test
//    public void testCreateUserAccount() {
//
//    	User user = new User();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        LocalDate dateOfBirth = LocalDate.of(2002, 1, 1);
//		Date dob = Date.valueOf(dateOfBirth);
//        user.setDateOfBirth(dob);
//
//        String email = "johndoe@example.com";
//        String password = "password123";
//
//        boolean result = accountDao.createUserAccount(user, email, password);
//
//        assertTrue(result);
//    }
//	
//	@Test
//	public void testSubscribeToService() {
//        NormalAccount normalAccount = new NormalAccount();
//        normalAccount.setId(1);
//        normalAccount.setIdUser(2);
//        normalAccount.setEmail("test10@example.com");
//        normalAccount.setPassword("password");
//        normalAccount.setNbAllowedReservation(5);
//
//        Account result = accountDao.subscribeToService(normalAccount);
//
//        assertTrue(result instanceof SubscriberAccount);
//	}
//	
//	@Test
//	public void testUnSubscribeFromService() {
//		SubscriberAccount subscriberAccount = new SubscriberAccount();
//		subscriberAccount.setId(1);
//		subscriberAccount.setIdUser(2);
//		subscriberAccount.setEmail("test10@example.com");
//		subscriberAccount.setPassword("password");
//		subscriberAccount.setNbAllowedReservation(5);
//
//        Account result = accountDao.unsubscribeFromService(subscriberAccount);
//
//        assertTrue(result instanceof NormalAccount);
//	}
//	
//	@Test
//	public void testAddMoneyToCard() {
//		SubscriberCard subscriberCard = new SubscriberCard();
//        subscriberCard.setCardNumber("TestCard123");
//        subscriberCard.setAmount(100);
//        
//        boolean result = accountDao.addMoneyToCard(subscriberCard, 50);
//        assertTrue(result);
//        assertEquals(150.0, subscriberCard.getAmount(), 0.01);
//
//        result = accountDao.addMoneyToCard(subscriberCard, -20);
//        assertFalse(result);
//        assertEquals(150.0, subscriberCard.getAmount(), 0.01);
//	}
//	
//	@Test 
//	public void testProcessPaymentBySubscriberCard() {
//		SubscriberCard subscriberCard = new SubscriberCard();
//        subscriberCard.setCardNumber("TestCard123");
//        subscriberCard.setAmount(100);
//		
//        boolean result = accountDao.processPaymentBySubscriberCard(subscriberCard, 40.0);
//        assertTrue(result);
//        assertEquals(60.0, subscriberCard.getAmount(), 0.01);
//
//        result = accountDao.processPaymentBySubscriberCard(subscriberCard, 200.0);
//        assertFalse(result);
//        assertEquals(60.0, subscriberCard.getAmount(), 0.01);
//	}
//	
//	@Test
//	public void testRequestFilm() {
//		SubscriberAccount subscriberAccount = new SubscriberAccount();
//		subscriberAccount.setId(100);
//		subscriberAccount.setIdUser(2);
//		subscriberAccount.setEmail("test10@example.com");
//		subscriberAccount.setPassword("password");
//		subscriberAccount.setNbAllowedReservation(5);
//		
//		boolean result1 = accountDao.requestUnavailableFilm(subscriberAccount, "Film New");
//		assertTrue(result1);
//		
//		boolean result2 = accountDao.requestUnavailableFilm(subscriberAccount, "");
//		assertFalse(result2);
//	}
//	
//	@Test
//	public void banFilmCategory() {
//		
//		SubscriberAccount subscriberAccount = new SubscriberAccount();
//		subscriberAccount.setId(100);
//		subscriberAccount.setIdUser(2);
//		subscriberAccount.setEmail("test10@example.com");
//		subscriberAccount.setPassword("password");
//		subscriberAccount.setNbAllowedReservation(5);
//		
//		Category cat1 = new Category();
//		cat1.setId(1);
//		cat1.setCategoryName("Category1");
//		
//		Category cat2= new Category();
//		cat2.setId(2);
//		cat2.setCategoryName("Category2");
//		
//		ArrayList<Category> listCat = new ArrayList<Category>();
//		listCat.add(cat1);
//		listCat.add(cat2);
//		
//		boolean result = accountDao.banFilmCategories(subscriberAccount, listCat);
//		assertTrue(result);
//		
//	}
//	
//	@Test
//	public void unbanFilmCategory() {
//		
//		System.out.println("======================================");
//		System.out.println("	Test Unban Category");
//		System.out.println("======================================");
//		
//		SubscriberAccount subscriberAccount = new SubscriberAccount();
//		subscriberAccount.setId(100);
//		subscriberAccount.setIdUser(2);
//		subscriberAccount.setEmail("test10@example.com");
//		subscriberAccount.setPassword("password");
//		subscriberAccount.setNbAllowedReservation(5);
//		
//		Category cat1 = new Category();
//		cat1.setId(100);
//		cat1.setCategoryName("Category1");
//		
//		boolean result = accountDao.unbanFilmCategories(subscriberAccount, cat1);
//		assertTrue(result);
//		
//	}
//	
//	@Test
//	public void getAllBannedCategories() {
//		System.out.println("======================================");
//		System.out.println("	Test Get Banned Categories");
//		System.out.println("======================================");
//		SubscriberAccount subscriberAccount = new SubscriberAccount();
//		subscriberAccount.setId(100);
//		subscriberAccount.setIdUser(2);
//		subscriberAccount.setEmail("test10@example.com");
//		subscriberAccount.setPassword("password");
//		subscriberAccount.setNbAllowedReservation(5);
//		
//		List<Category> results = accountDao.getBannedCategories(subscriberAccount);
//		
//		for (Category cat : results) {
//			System.out.println(cat);
//		}
//	}
//	
//	@Test
//	public void testWeeklyRentalLimit() {
//
//		SubscriberAccount subscriberAccount = new SubscriberAccount();
//	    subscriberAccount.setId(100);
//	    subscriberAccount.setIdUser(2);
//	    subscriberAccount.setEmail("test10@example.com");
//	    subscriberAccount.setPassword("password");
//	    subscriberAccount.setNbAllowedReservation(5);
//
//	    int newWeeklyLimit = 7;
//	    boolean result = accountDao.setWeeklyRentalLimit(subscriberAccount, newWeeklyLimit);
//
//	    assertTrue(result);
//	    
//	}
	
    @Test
    public void testModifyAccountInformation() throws SQLException {
    	
		SubscriberAccount subscriberAccount = new SubscriberAccount();
	    subscriberAccount.setId(100);
	    subscriberAccount.setIdUser(2);
	    subscriberAccount.setEmail("test10@example.com");
	    subscriberAccount.setPassword("123");
	    subscriberAccount.setNbAllowedReservation(5);
    	
        String newFirstName = "Jane";
        String newLastName = "Smith";
        Date newDob = Date.valueOf("1995-05-05");
        String oldPassword = "123";
        String newPassword = "newPassword";

        Account updatedAccount = accountDao.modifyAccountInformation(subscriberAccount, newFirstName, newLastName, newDob, oldPassword, newPassword);
        
        System.out.println(updatedAccount);

        assertEquals(newFirstName, updatedAccount.getUser().getFirstName());
        assertEquals(newLastName, updatedAccount.getUser().getLastName());
        assertEquals(newDob, updatedAccount.getUser().getDateOfBirth());
    }

	
	// ============================================================================================================

//	@Test
//	public void testUserLogin() {
//		
//		System.out.println("======================================");
//		System.out.println("	Test User Login");
//		System.out.println("======================================");
//
//		String email = "test@example.com";		
//		String password = "123";
//		
//		Account account = accountDao.userLogin(email, password);
//
//		assertNotNull(account);
//		assertEquals(email, account.getEmail());
//
//		if (account instanceof SubscriberAccount) {
//
//			SubscriberAccount subscriberAccount = (SubscriberAccount) account;
//
//			List<SubscriberCard> subscriberCards = subscriberAccount.getSubscriberCards();
//			assertNotNull(subscriberCards);
//		}
//		List<CreditCard> creditCards = account.getCreditCards();
//		assertNotNull(creditCards);
//		
//		System.out.println(account);
//		System.out.println();
//	}
//
//	@Test
//	public void testUserLoginWithCard() {
//		
//		System.out.println("======================================");
//		System.out.println("	Test User Login Card");
//		System.out.println("======================================");
//
//		String cardNumber = "SubCard1";
//		String password = "123";
//		Account account = accountDao.userLoginWithCard(cardNumber, password);
//
//		assertNotNull(account);
//
//		List<CreditCard> creditCards = account.getCreditCards();
//		assertNotNull(creditCards);
//
//		List<SubscriberCard> subscriberCards = ((SubscriberAccount) account).getSubscriberCards();
//		assertNotNull(subscriberCards);
//		
//		System.out.println(account);
//		System.out.println();
//
//	}

}
