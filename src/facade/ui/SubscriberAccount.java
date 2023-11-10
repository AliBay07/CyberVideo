package facade.ui;

import java.util.ArrayList;

public class SubscriberAccount extends Account{
    private ArrayList<Categorie> filteredCategories;
    private ArrayList<SubscriptionCard> subscriptionCards;
    private int maximumRentalPerWeek;
    private ArrayList<SubscriptionCard> subscriptionCard;

    public SubscriberAccount(Long id, String password, User user, CreditCard creditCard) {
        super(id, password, user, creditCard);
        subscriptionCard = new ArrayList<>();
    }

    public SubscriberAccount(){
        super();
    }

    public Boolean receiveBonusForRentingTwentyFilm(){
        return false;
    }
    public Boolean blockUserCardForNegativeBalance(){return false;}
    public Boolean addCardToSubscriber(String cardNumber){
        return false;
    }
    public void setSubscriptionCard(SubscriptionCard subscriptionCard){
        this.subscriptionCard.add(subscriptionCard);
    }
    public ArrayList<SubscriptionCard> getSubscriptionCard(){
        return this.subscriptionCard;
    }
}
