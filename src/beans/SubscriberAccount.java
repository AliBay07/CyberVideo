package beans;

import java.util.ArrayList;
import java.util.List;

public class SubscriberAccount extends Account {

	private double subscriptionAmount;
    private List<SubscriberCard> subscriberCards;

    public SubscriberAccount() {
        this.subscriptionAmount = 0.0;
        this.subscriberCards = new ArrayList<SubscriberCard>();
    }

    public double getSubscriptionAmount() {
        return subscriptionAmount;
    }

    public void setSubscriptionAmount(double subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
    }

    public List<SubscriberCard> getSubscriberCards() {
        return subscriberCards;
    }

    public void setSubscriberCards(List<SubscriberCard> subscriberCards) {
        this.subscriberCards = subscriberCards;
    }

    public void addSubscriberCard(SubscriberCard subscriberCard) {
        this.subscriberCards.add(subscriberCard);
    }

    public void removeSubscriberCard(SubscriberCard subscriberCard) {
        this.subscriberCards.remove(subscriberCard);
    }
    
    @Override
    public String toString() {
        return super.toString() +
                "\nsubscriptionAmount=" + subscriptionAmount +
                "\nsubscriberCards=" + subscriberCards;
    }
}
