package beans;

import java.sql.Date;
import java.text.SimpleDateFormat;

public abstract class Reservation {
	
	private Account account;
	private Film film;
	private Date StartReservationDate;
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Film getFilm() {
		return film;
	}
	
	public void setFilm(Film film) {
		this.film = film;
	}
	
	public Date getStartReservationDate() {
		return StartReservationDate;
	}
	
	public void setStartReservationDate(Date startReservationDate) {
		StartReservationDate = startReservationDate;
	}

    @Override
    public String toString() {

        return "Reservation Information:\n" +
               account +
               "Film: " + film.getName() + " (ID: " + film.getId() + ")\n" +
               "Start Reservation Date: " + StartReservationDate + "\n";
    }
}
