package facade.bd;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import beans.Account;
import beans.BlueRay;
import beans.Category;
import beans.CreditCard;
import beans.Film;
import beans.HistoricReservation;
import beans.Reservation;
import beans.SubscriberCard;
import beans.User;
import dao.classes.AccountDao;
import dao.classes.BlueRayDao;
import dao.classes.FilmDao;
import dao.classes.ReservationDao;
import dao.tools.DaoFactory;

public class FacadeBd {

    private static DaoFactory daoFactory;
    private FilmDao filmDao;
    private AccountDao accountDao;
    private BlueRayDao blueRayDao;
    private ReservationDao reservationDao;

    public FacadeBd() {
        filmDao = DaoFactory.getFilmDao();
        accountDao = DaoFactory.getAccountDao();
        blueRayDao = DaoFactory.getBlueRayDao();
        reservationDao = DaoFactory.getReservationsDao();
    }
    
    // FilmDao
    public List<Film> getAllFilms(Account account) {
        return filmDao.getAllFilms(account);
    }

    public List<List<Object>> getTopFilmsMonth(Account account) {
        return filmDao.getTopFilmsMonth(account);
    }

    public List<List<Object>> getTopFilmsWeek(Account account) {
        return filmDao.getTopFilmsWeek(account);
    }

    public Film getFilmInformation(Account account, String filmName) {
        return filmDao.getFilmInformation(account, filmName);
    }

    public List<Film> searchFilmByCriteria(Account account, Map<String, String> filters) {
        return filmDao.searchFilmByCriteria(account, filters);
    }

    // AccountDao
    public boolean createUserAccount(User user, String email, String password) {
        return accountDao.createUserAccount(user, email, password);
    }

    public Account userLogin(String email, String password) {
        return accountDao.userLogin(email, password);
    }

    public Account userLoginWithCard(String cardNumber, String password) {
        return accountDao.userLoginWithCard(cardNumber, password);
    }

    public Account subscribeToService(Account account) {
        return accountDao.subscribeToService(account);
    }

    public Account unsubscribeFromService(Account account) {
        return accountDao.unsubscribeFromService(account);
    }

    public boolean addMoneyToCard(SubscriberCard subscriberCard, double amount) {
        return accountDao.addMoneyToCard(subscriberCard, amount);
    }

    public boolean processPaymentBySubscriberCard(SubscriberCard subscriberCard, double amount) {
        return accountDao.processPaymentBySubscriberCard(subscriberCard, amount);
    }

    public boolean requestUnavailableFilm(Account account, String filmName) {
        return accountDao.requestUnavailableFilm(account, filmName);
    }

    public boolean banFilmCategories(Account account, List<Category> categories) {
        return accountDao.banFilmCategories(account, categories);
    }

    public boolean unbanFilmCategories(Account account, Category category) {
        return accountDao.unbanFilmCategories(account, category);
    }

    public ArrayList<Category> getBannedCategories(Account account) {
        return accountDao.getBannedCategories(account);
    }

    public boolean setWeeklyRentalLimit(Account account, int weeklyLimit) {
        return accountDao.setWeeklyRentalLimit(account, weeklyLimit);
    }

    public Account modifyAccountInformation(Account account, String newFirstName, String newLastName, String oldPassword, String newPassword) {
        return accountDao.modifyAccountInformation(account, newFirstName, newLastName, oldPassword, newPassword);
    }

    public boolean processPaymentByCreditCard(CreditCard creditCard) {
        return accountDao.processPaymentByCreditCard(creditCard);
    }
    
    public boolean addCreditCardToAccount(Account account) {
    	return accountDao.addCreditCardToAccount(account);
    }
    
    public boolean addSubscriberCardToAccount(Account account) {
    	return accountDao.addSubscriberCardToAccount(account);
    }

    // BlueRayDao
    public ArrayList<BlueRay> getAllAvailableBlueRays() {
        return blueRayDao.getAllAvailableBlueRays();
    }

    public boolean reportLostBlueRayDisc(Account account, BlueRay blueRay) {
        return blueRayDao.reportLostBlueRayDisc(account, blueRay);
    }

    // ReservationDao
    public ArrayList<Reservation> getCurrentReservationsByAccount(Account account) {
        return reservationDao.getCurrentReservationsByAccount(account);
    }

    public boolean ReserveBlueRay(Account account, BlueRay blueRay) {
        return reservationDao.ReserveBlueRay(account, blueRay);
    }

    public boolean ReserveQrCode(Account account, Film film) {
        return reservationDao.ReserveQrCode(account, film);
    }

    public boolean addReservationToHistoric(BlueRay blueRay) {
        return reservationDao.addReservationToHistoric(blueRay);
    }

    public boolean removeCurrentReservation(BlueRay blueRay) {
        return reservationDao.removeCurrentReservation(blueRay);
    }
    
    public List<HistoricReservation> getHistoricReservations(Account account) {
    	return reservationDao.getHistoricReservations(account);
    }
}
