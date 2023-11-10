package facade.ui;

public abstract class Account {
    private Long id;
    private String password;
    private User user;
    private CreditCard creditCard;


    public Account(Long id, String password, User user, CreditCard creditCard) {
        this.id = id;
        this.password = password;
        this.user = user;
        this.creditCard = creditCard;
    }
    public Account(){}
    // Getter pour l'ID
    public Long getId() {
        return id;
    }

    // Setter pour l'ID
    public void setId(Long id) {
        this.id = id;
    }

    // Getter pour le mot de passe (password)
    public String getPassword() {
        return password;
    }

    // Setter pour le mot de passe (password)
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter pour l'utilisateur (user)
    public User getUser() {
        return user;
    }

    // Setter pour l'utilisateur (user)
    public void setUser(User user) {
        this.user = user;
    }

    // Getter pour la carte de crédit (creditCard)
    public CreditCard getCreditCard() {
        return creditCard;
    }

    // Setter pour la carte de crédit (creditCard)
    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public double calculateRentalFee() {
        double i = 0;
        return i;
    }
    public boolean blockUserAccount(){return true;}
    public double calculatePenalty(int daysLate){
        return daysLate;
    }
}
