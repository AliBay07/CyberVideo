package beans;

public class BlueRay {
	
	private long id;
	private Film film;
	private long available;
	
	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Id : " + this.getId() + "\n" + this.getFilm();
	}

	public long getAvailable() {
		return available;
	}

	public void setAvailable(long available) {
		this.available = available;
	}

}
