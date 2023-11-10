package facade.ui;

public class CreditCard {
    private Long id;
    // private Bank bank;
    private String bank;

    public CreditCard(Long id, String bank) {
        this.id = id;
        this.bank = bank;
    }

    public CreditCard() {
    }

    // Getter pour l'ID
    public Long getId() {
        return id;
    }

    // Setter pour l'ID
    public void setId(Long id) {
        this.id = id;
    }

    /*
    // Getter pour la banque (Bank)
    public Bank getBank() {
        return bank;
    }

    // Setter pour la banque (Bank)
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    */

    // Getter pour la chaîne "bank"
    public String getBank() {
        return bank;
    }

    // Setter pour la chaîne "bank"
    public void setBank(String bank) {
        this.bank = bank;
    }
}
