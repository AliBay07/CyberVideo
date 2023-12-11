package facade.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import beans.Account;
import beans.BlueRay;
import beans.Category;
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

    public Account userLoginWithCard(String cardNumber, String password) {
        return null;
    }

    public void userLogOut() {

    }

    public boolean rentBlueRay(BlueRay blueRay) {
        return false;
    }

    public boolean returnBlueRay(BlueRay blueRay) {
        return false;
    }

    public QrCode generateQrCode(Film film) {
        return null;
    }

    public boolean printQrCode(QrCode qrCode) {
        return false;
    }


    public Account subscribeToService() {
        return null;
    }

    public Account unsubscribeToService() {
        return null;
    }

    public boolean addMoneyToCard(SubscriberCard subscriberCard, double amount) {
        return false;
    }

    public boolean addCreditCardToAccount(Account account) {
        return false;
    }

    public boolean requestUnavailableMovies(String filmName) {
        return false;
    }

    public boolean banFilmCategory(ArrayList<String> Category) {
        return false;
    }

    public boolean unbanFilmCategory(Category c) {
        return false;
    }

    public List<Category> getBannedCategories() {
        return null;
    }

    public Account modifyAccountInformation(Account account, String newFirstName, String newLastName, String NewPassword) {
        return null;
    }

    public ArrayList<Film> searchFilmByCriteria(Map<String,ArrayList<String>> filters) {
        return null;
    }

    public Film getFilmInformation(Film film) {
        return null;
    }

    public boolean reportLostBlueRayDisc(BlueRay blueray) {
        return false;
    }

    public Account modifyAccountInformation(String newFirstName, String newLastName, String NewPassword) {
        return null;
    }

    public boolean processPaymentByCreditCard(double amount) {
        return false;
    }

    public boolean processPaymentBySubscriberCard(Long idSubscriberCard, double amount) {
        return false;
    }

    public boolean setWeeklyRentalLimit(int weeklyLimit) {
        return false;
    }

    public boolean ensureMinimumBlueRayFilms(int quantity) {
        return false;
    }

    public int getNbAvailableBlueRaysFor(Film film) {
        return 0;
    }

    public ArrayList<Reservation> getCurrentReservationsByAccount() {
        return null;
    }

    public ArrayList<HistoricReservation> getHistoricReservationByAccount() {
        return null;
    }

    public void handleErrors(int errorCode) {

    }

}
