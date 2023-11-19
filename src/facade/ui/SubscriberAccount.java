package facade.ui;

import java.util.ArrayList;

public class SubscriberAccount extends Account{
    private ArrayList<Categorie> filteredCategories;
    private ArrayList<SubscriptionCard> subscriptionCards;
    private int maximumRentalPerWeek;
    private ArrayList<SubscriptionCard> subscriptionCard;

    public SubscriberAccount(Long id, String password, User user, ArrayList<CreditCard> creditCard,ArrayList<SubscriptionCard> subscriptionCard) {
        super(id, password, user, creditCard);
        this.subscriptionCard = subscriptionCard;
    }

    public SubscriberAccount(){
        super();
    }

    public boolean receiveBonusForRentingTwentyFilm(){
        return false;
    }
    public boolean blockUserCardForNegativeBalance(){return false;}
    public boolean addCardToSubscriber(String cardNumber){
        return false;
    }
    public void setSubscriptionCard(ArrayList<SubscriptionCard> subscriptionCard){
        this.subscriptionCard = subscriptionCard;
    }

    public void addSubscriptionCard(SubscriptionCard subscriptionCard){
        this.subscriptionCard.add(subscriptionCard);
    }
    public ArrayList<SubscriptionCard> getSubscriptionCard(){
        return this.subscriptionCard;
    }
}
