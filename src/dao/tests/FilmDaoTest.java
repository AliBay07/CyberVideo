package dao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.classes.FilmDao;
import beans.Film;
import beans.SubscriberAccount;
import dao.tools.Session;

public class FilmDaoTest {
    private static Session session;
    private static FilmDao filmDao;

    @BeforeClass
    public static void setUp() {
        session = new Session(false);
        try {
            session.open();
            filmDao = new FilmDao(session.get());
            filmDao.insertMockData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            filmDao.removeMockData();
        } finally {
            try {
                session.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testGetAllFilms() {
    	System.out.println("========================================");
    	System.out.println("		Get All Films");
    	System.out.println("========================================");
    	
		SubscriberAccount subscriberAccount = new SubscriberAccount();
		subscriberAccount.setId(100);
		subscriberAccount.setIdUser(2);
		subscriberAccount.setEmail("test10@example.com");
		subscriberAccount.setPassword("password");
		subscriberAccount.setNbAllowedReservation(5);
    	
        List<Film> films = filmDao.getAllFilms(subscriberAccount);
        assertNotNull(films);

        for (Film film : films) {
            System.out.println(film.toString());
            System.out.println();
        }
    }

    @Test
    public void testGetTopFilmsOfTheMonth() {
        System.out.println("========================================");
        System.out.println("   Top Films Of The Month");
        System.out.println("========================================");
        
		SubscriberAccount subscriberAccount = new SubscriberAccount();
		subscriberAccount.setId(100);
		subscriberAccount.setIdUser(2);
		subscriberAccount.setEmail("test10@example.com");
		subscriberAccount.setPassword("password");
		subscriberAccount.setNbAllowedReservation(5);
		
        List<List<Object>> topFilms = filmDao.getTopFilmsMonth(subscriberAccount);
        assertNotNull(topFilms);

        for (List<Object> filmData : topFilms) {
            Film film = (Film) filmData.get(0);
            int numberReservations = (int) filmData.get(1);
            System.out.println(film);
            System.out.println("Number of Reservations: " + numberReservations);
            System.out.println();
        }
    }
    
    @Test
    public void testGetTopFilmsOfTheWeek() {
        System.out.println("========================================");
        System.out.println("   Top Films Of The Week");
        System.out.println("========================================");
        
		SubscriberAccount subscriberAccount = new SubscriberAccount();
		subscriberAccount.setId(100);
		subscriberAccount.setIdUser(2);
		subscriberAccount.setEmail("test10@example.com");
		subscriberAccount.setPassword("password");
		subscriberAccount.setNbAllowedReservation(5);
		
        List<List<Object>> topFilms = filmDao.getTopFilmsWeek(subscriberAccount);
        assertNotNull(topFilms);

        for (List<Object> filmData : topFilms) {
            Film film = (Film) filmData.get(0);
            int numberReservations = (int) filmData.get(1);
            System.out.println(film);
            System.out.println("Number of Reservations: " + numberReservations);
            System.out.println();
        }
    }

    @Test
    public void testGetFilmInformation() {
    	System.out.println("========================================");
    	System.out.println("		Film Information");
    	System.out.println("========================================");
    	
		SubscriberAccount subscriberAccount = new SubscriberAccount();
		subscriberAccount.setId(100);
		subscriberAccount.setIdUser(2);
		subscriberAccount.setEmail("test10@example.com");
		subscriberAccount.setPassword("password");
		subscriberAccount.setNbAllowedReservation(5);
		
        String filmName = "Film 1";
        Film availableFilm = filmDao.getFilmInformation(subscriberAccount, filmName);
        assertNotNull(availableFilm);

        System.out.println(availableFilm.toString());
        System.out.println();
    }

    @Test
    public void testSearchFilmByCriteria() {
    	System.out.println("========================================");
    	System.out.println("		Search Criteria");
    	System.out.println("========================================");
    	
		SubscriberAccount subscriberAccount = new SubscriberAccount();
		subscriberAccount.setId(100);
		subscriberAccount.setIdUser(2);
		subscriberAccount.setEmail("test10@example.com");
		subscriberAccount.setPassword("password");
		subscriberAccount.setNbAllowedReservation(5);
		
        Map<String, String> filters = new HashMap<>();
        filters.put("name", "Film 2");
        List<Film> filteredFilms = filmDao.searchFilmByCriteria(subscriberAccount, filters);
        assertNotNull(filteredFilms);

        for (Film film : filteredFilms) {
            System.out.println(film.toString());
            System.out.println();
        }
    }

}
