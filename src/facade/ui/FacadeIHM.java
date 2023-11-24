package facade.ui;

import beans.*;
import facade.ui.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import machine.Machine;

public class FacadeIHM {

    //private Machine machine;

    public boolean createUserAccount(User user, String email, String password) {
        return false;
    }

    public Account userLogin(String email, String password) {
        return null;
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
    public List<List<Object>> getTopFilmsOfTheWeek() {
        return null;
    }

    public List<List<Object>> getTopFilmsOfTheMonth() {
        return null;
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

    public ArrayList<HistoricReservation> getAccountRentalHistory() {
        return null;
    }

    public ArrayList<Film> searchFilmByCriteria(Map<String,ArrayList<String>> filters) {
        return null;
    }

    public Film getFilmInformation(Film film) {
        return null;
    }

    public ArrayList<Film> getAllFilms() {
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

    public ArrayList<BlueRay> getAvailableBlueRays() {
        return null;
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
