package facade.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import beans.Account;
import beans.BlueRay;
import beans.Category;
import beans.CreditCard;
import beans.Film;
import beans.HistoricReservation;
import beans.QrCode;
//import machine.Machine;
import beans.Reservation;
import beans.SubscriberCard;
import beans.User;
import machine.Machine;

public class FacadeIHM {

	private static Machine machine = Machine.getInstance(null);

	public ArrayList<Film> getAllFilms() {
		return machine.getAllFilms();
	}

	public Account userLogin(String email, String password) {
		return machine.userLogin(email, password);
	}

	public boolean createUserAccount(User user, String email, String password) {
		return machine.createUserAccount(user, email, password);
	}

	public ArrayList<Film> getTopFilmsOfTheWeek() {
		return machine.getTopFilmsWeek();
	}

	public ArrayList<Film>  getTopFilmsOfTheMonth() {
		return machine.getTopFilmsMonth();
	}

	public ArrayList<BlueRay> getAvailableBlueRays() {
		return machine.getAllAvailableBlueRays();
	}

	public boolean rentBlueRay(BlueRay blueRay,CreditCard creditCard, SubscriberCard subscriberCard) {
		return machine.ReserveBlueRay(blueRay, creditCard,subscriberCard);
	}

	public boolean returnBlueRay(BlueRay blueRay) {
		return machine.removeCurrentReservation(blueRay);
	}
	
	public ArrayList<Category> getAllCategories() {
    	return machine.getAllCategories();
    }

	public Account userLoginWithCard(String cardNumber, String password) {
		return machine.userLoginWithCard(cardNumber, password);
	}

	public void userLogOut() {
		machine.userLogOut();
	}

	public boolean generateQrCode(Film film, CreditCard creditCard,SubscriberCard subscriberCard) {
		return machine.ReserveQrCode(film);
	}

	public boolean printQrCode(QrCode qrCode) {
		return false;
	}

	public boolean subscribeToService() {
		return machine.subscribeToService();
	}

	public boolean unsubscribeToService() {
		return machine.unsubscribeFromService();
	}

	public boolean addMoneyToCard(SubscriberCard subscriberCard, double amount) {
		return machine.addMoneyToCard(subscriberCard, amount);
	}

	public boolean addCreditCardToAccount() {
		return machine.addCreditCardToAccount();
	}

	public boolean addSubscriberCardToAccount() {
		return machine.addSubscriberCardToAccount();
	}

	public boolean requestUnavailableMovies(String filmName) {
		return machine.requestUnavailableFilm(filmName);
	}

	public boolean banFilmCategory(ArrayList<Category> category) {
		return machine.banFilmCategories(category);
	}

	public boolean unbanFilmCategory(Category c) {
		return machine.unbanFilmCategories(c);
	}

	public ArrayList<Category> getBannedCategories() {
		return machine.getAccountBannedCategories();
	}

	public ArrayList<Film> searchFilmByCriteria(Map<String,ArrayList<String>> filters) {
		return null;
	}

	public Film getFilmInformation(String filmName) {
		return machine.getFilmInformation(filmName);
	}

	public boolean reportLostBlueRayDisc(BlueRay blueray) {
		return machine.reportLostBlueRayDisc(blueray);
	}

	public Account modifyAccountInformation(String newFirstName, String newLastName, String oldPassword, String newPassword) {
		return machine.modifyAccountInformation(newFirstName, newLastName, oldPassword, newPassword);
	}

	public boolean processPaymentByCreditCard(CreditCard creditCard, double amount) {
		return machine.processPaymentByCreditCard(creditCard, amount);
	}

	public boolean processPaymentBySubscriberCard(SubscriberCard subscriberCard, double amount) {
		return machine.processPaymentBySubscriberCard(subscriberCard, amount);
	}

	public boolean setWeeklyRentalLimit(int weeklyLimit) {
		return machine.setWeeklyRentalLimit(weeklyLimit);
	}

	public boolean ensureMinimumBlueRayFilms(int quantity) {
		return false;
	}

	public int getNbAvailableBlueRaysFor(Film film) {
		return 0;
	}

	public ArrayList<Reservation> getCurrentReservationsByAccount() {
		return machine.getCurrentReservationsByAccount();
	}

	public ArrayList<HistoricReservation> getHistoricReservationByAccount() {
		return machine.getHistoricReservations();
	}

	public void handleErrors(int errorCode) {

	}

}
