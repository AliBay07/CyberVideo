package beans;

import java.sql.Date;

public class QrCode {
	
	private Film film;
	private Date reservation_start_date;
	private Date expiration_date;
	private String link;

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Date getReservation_start_date() {
		return reservation_start_date;
	}

	public void setReservation_start_date(Date reservation_start_date) {
		this.reservation_start_date = reservation_start_date;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
