package beans;

public class CreditCard {
	
	private long id;
	private String cardNumber;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Override
	public String toString() {
		return this.getCardNumber();
	}

}
