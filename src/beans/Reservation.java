package beans;

import java.sql.Date;
import java.text.SimpleDateFormat;

public abstract class Reservation {
	
	private Account account;
	private BlueRay blueray;
	private Date StartReservationDate;
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Date getStartReservationDate() {
		return StartReservationDate;
	}
	
	public void setStartReservationDate(Date startReservationDate) {
		StartReservationDate = startReservationDate;
	}
	
	public BlueRay getBlueray() {
		return blueray;
	}

	public void setBlueray(BlueRay blueray) {
		this.blueray = blueray;
	}

    @Override
    public String toString() {

        return "Reservation Information:\n" +
               account +
               "Film: " + blueray.getFilm().getName() + " (ID: " + blueray.getFilm().getId() + ")\n" +
               "Start Reservation Date: " + StartReservationDate + "\n";
    }
}
