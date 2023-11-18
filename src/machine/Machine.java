package machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Account;
import beans.Film;
import beans.User;
import dao.tools.Session;
import facade.bd.FacadeBd;

public class Machine {
	
	private static FacadeBd facadeBd = new FacadeBd();
	private Account account;
	
	public static void main(String[] args) {
		
		Session session = new Session(false);
		//Account myAccount = new beans.NormalAccount();
		Machine machine = new Machine();

		try {
			session.open();
			machine.getAllFilms();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public boolean createUserAccount(User user, String email, String password){
		return facadeBd.createUserAccount(user, email, password);
	}

	public boolean subscribeToService(Account account){
		beans.SubscriberAccount subAccount = (beans.SubscriberAccount) facadeBd.subscribeToService(account);
		if (subAccount != null) {
			this.account = subAccount;
			return true;
		}
		return false;	
	}

	public void getAllFilms() {
		ArrayList<Film> films = (ArrayList<Film>) facadeBd.getAllFilms(account); 		 
		for (Film film : films) {
			System.out.println(film);
		}
    }

	public void getAccountBannedCategories(Account account){
		ArrayList<beans.Category> bannedCategories = (ArrayList<beans.Category>) facadeBd.getBannedCategories(account);
		String output = "";
		int i = 0;
		for (beans.Category c : bannedCategories){
			output += c + ", ";
			i++;
			if (i == bannedCategories.size() - 1){
				output += c;
			}
 		}
		System.out.println(account.getUser().getFirstName()+ "'s banned categories: " + output);
	}

	public void getAccountRentalHistory(Account account){
		//TO DO we dont have a function for this in the dao
	}

}		
