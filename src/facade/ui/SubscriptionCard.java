package facade.ui;

public class SubscriptionCard {
    private Account account;
    private long id;
    private float balance;

    // Constructeur par défaut
    public SubscriptionCard() {
    }

    // Constructeur avec des paramètres
    public SubscriptionCard(/*Account account, */long id, float balance) {
        //this.account = account;
        this.id = id;
        this.balance = balance;
    }

    // Getter pour account
    public Account getAccount() {
        return account;
    }

    // Setter pour account
    public void setAccount(Account account) {
        this.account = account;
    }

    // Getter pour id
    public long getId() {
        return id;
    }

    // Setter pour id
    public void setId(long id) {
        this.id = id;
    }

    // Getter pour balance
    public float getBalance() {
        return balance;
    }

    // Setter pour balance
    public void setBalance(float balance) {
        this.balance = balance;
    }

    // Méthode pour définir le solde de la carte
    public Boolean setCardBalance(double balance) {
        // Implémentez la logique pour définir le solde de la carte
        // Ici, vous pouvez définir la valeur de "balance" en fonction de "balance" passé en argument.
        // Par exemple : this.balance = (float) balance;
        return true; // Indique que la modification du solde a été effectuée avec succès.
    }
}
