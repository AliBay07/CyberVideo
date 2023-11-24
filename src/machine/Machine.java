package machine;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.*;
import dao.tools.Session;
import facade.bd.FacadeBd;

public class Machine {

	private static FacadeBd facadeBd = new FacadeBd();
	private Account account;
	public Machine(Account account) {
		this.account = account;
	}
	public static void main(String[] args) {
		SubscriberAccount firstAccount = new SubscriberAccount();
		Session session = new Session(false);
		//Account myAccount = new beans.NormalAccount();
		Machine machine = new Machine(firstAccount);

		try {
			session.open();
//			machine.getAllFilms();
//			machine.getTopFilmsWeek();
//			machine.getTopFilmsMonth();
			User user = new User();
			user.setFirstName("Nizar");
			user.setLastName("adfh");
			user.setDateOfBirth(new Date(2000,11,1));
			String email = "nizar@gmail.com";
			String pwd = "password;jfd";
//			machine.createUserAccount(user,"nizar@gmail.com","password;jfd");
			machine.userLogin(email,pwd);
			machine.unsubscribeFromService();
			System.out.println(machine.account);
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

	public boolean createUserAccount(User user, String email, String password) {
		return facadeBd.createUserAccount(user, email, password);
	}

	public void userLogin(String email, String pwd){
		//i think we should get this info from facadeUI to make this function work correctly.
		this.account = facadeBd.userLogin(email, pwd);
	}
	public boolean subscribeToService() {
		SubscriberAccount subAccount = (SubscriberAccount) facadeBd.subscribeToService(account);
		if (subAccount != null) {
			this.account = subAccount;
			return true;
		}
		return false;
	}

	public boolean unsubscribeFromService() {
		Account retrievedAccount = facadeBd.unsubscribeFromService(account);
		if (retrievedAccount != null) {
			this.account = retrievedAccount;
			return true;
		}
		return false;
	}

	public void getAllFilms() {
		ArrayList<Film> films = (ArrayList<Film>) facadeBd.getAllFilms(this.account);
		for (Film film : films) {
			System.out.println(film);
		}
	}

	public void getTopFilmsWeek(){
		List<List<Object>> topWeekFilms =  facadeBd.getTopFilmsWeek(this.account);
		for (List<Object> film: topWeekFilms){
			System.out.println(film.get(0) + "Number of reservations: " + film.get(1));
		}
	}
	public void getTopFilmsMonth(){
		List<List<Object>> topMonthFilms =  facadeBd.getTopFilmsMonth(this.account);
		for (List<Object> film: topMonthFilms){
			System.out.println(film.get(0) + "Number of reservations: " + film.get(1));
		}
	}
	public void getAccountBannedCategories(Account account) {
		ArrayList<Category> bannedCategories = (ArrayList<Category>) facadeBd.getBannedCategories(account);
		String output = "";
		int i = 0;
		for (Category c : bannedCategories) {
			output += c + ", ";
			i++;
			if (i == bannedCategories.size() - 1) {
				output += c;
			}
		}
		System.out.println(account.getUser().getFirstName() + "'s banned categories: " + output);
	}

	public void getAccountRentalHistory(Account account) {
		//TO DO we dont have a function for this in the dao
	}

	public Account getAccount(){ return this.account;}
	public void setAccount(Account account) {
		this.account = account;
	}
}