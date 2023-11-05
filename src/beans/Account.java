package beans;

import java.util.List;

public abstract class Account {
	
	private long id;
	private long idUser;
	private String email;
	private String password;
	private int nbAllowedReservation;
	private User user;
	private List<CreditCard> CreditCards;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNbAllowedReservation() {
		return nbAllowedReservation;
	}

	public void setNbAllowedReservation(int nbAllowedReservation) {
		this.nbAllowedReservation = nbAllowedReservation;
	}

	public List<CreditCard> getCreditCards() {
		return CreditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		CreditCards = creditCards;
	}
	
    @Override
    public String toString() {
        return  "id=" + id +
                "\nidUser=" + idUser +
                "\nemail=" + email +
                "\nnbAllowedReservation=" + nbAllowedReservation +
                "\nuser=" + user +
                "\nCreditCards=" + CreditCards + "\n";
    }

}
