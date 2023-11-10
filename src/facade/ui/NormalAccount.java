package facade.ui;

public class NormalAccount extends Account{
    public NormalAccount(Long id, String password, User user, CreditCard creditCard) {
        super(id, password, user, creditCard);
    }
    public NormalAccount(){
        super();
    }
    public void subscribe(){};
}
