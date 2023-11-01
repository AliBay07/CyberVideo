package beans;

public class SubscriberCard {

	private long id;
	private String cardNumber;
	private float amount;

	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}

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
