package beans;

public class BlueRay {
	
	private long id;
	private Film film;
	private int availableQuantity;

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

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
		return "Id : " + this.getId() + "\n" + this.getFilm() + "\nAvailable Quantity : " + this.getAvailableQuantity();
	}

}
