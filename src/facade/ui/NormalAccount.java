package facade.ui;

import java.util.ArrayList;

public class NormalAccount extends Account{
    public NormalAccount(Long id, String password, User user, ArrayList<CreditCard> creditCard) {
        super(id, password, user, creditCard);
    }
    public NormalAccount(){
        super();
    }
    public void subscribe(){};
}
