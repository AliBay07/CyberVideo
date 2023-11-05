package beans;

import java.sql.Date;

public class HistoricReservation extends Reservation {
	
	private Date endReservationDate;
	
	public Date getEndReservationDate() {
		return endReservationDate;
	}
	
	public void setEndReservationDate(Date endReservationDate) {
		this.endReservationDate = endReservationDate;
	}
	
	@Override
	public String toString() {
		return super.toString() + "End Reservation Date: " + endReservationDate;
	}

}
