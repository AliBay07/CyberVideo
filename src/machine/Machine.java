package machine;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.*;
import coo.classes.FilmFilterIterator;
import dao.tools.Session;
import facade.bd.FacadeBd;

public class Machine {

	private static FacadeBd facadeBd = new FacadeBd();
	private Account account;
	private static Machine instance;
	
	private Machine(Account account) {
		this.account = account;
	}
	
	public static Machine getInstance(Account account) {
		if (instance == null) {
			instance = new Machine(account);
		}
		return instance;
	}
//	public static void main(String[] args) {
//		SubscriberAccount firstAccount = new SubscriberAccount();
//		
//		//Account myAccount = new beans.NormalAccount();
//		Machine machine = new Machine(firstAccount);
//		try {
//			
//			String email = "johndoe@example.com";
//			String pwd = "password123";
//			machine.userLogin(email,pwd);
//			System.out.println(machine.getAccount().getEmail());
//			//machine.getTopFilmsWeek();
//			//machine.getTopFilmsMonth();
//			//			User user = new User();
//			//			user.setFirstName("Nizar");
//			//			user.setLastName("adfh");
//			//			user.setDateOfBirth(new Date(2000,11,1));
//			//
//			////			machine.createUserAccount(user,"nizar@gmail.com","password;jfd");
//			//			machine.userLogin(email,pwd);
//			//			machine.unsubscribeFromService();
//			//			System.out.println(machine.account);
//			
//			List<Film> allFilms = facadeBd.getAllFilms(machine.getAccount());
//
//			String nameFilter = "";
//
//			Author author = new Author();
//			author.setId((long) 1);
//			author.setFirstName("christopher");
//			author.setLastName("nolan");
//
//			Actor actor =  new Actor();
//			actor.setId((long) 1);
//			actor.setFirstName("tom");
//			actor.setLastName("hanks");
//
//			Category category = new Category();
//			category.setId((long) 1);
//			category.setCategoryName("action");
//
//
//			List<Author> authorFilter = new ArrayList<Author>();
//			authorFilter.add(author);
//			List<Actor> actorFilter = new ArrayList<Actor>();
//			actorFilter.add(actor);
//			List<Category> categoryFilter = new ArrayList<Category>();
//			categoryFilter.add(category);
//			
//			FilmFilterIterator filmIterator = new FilmFilterIterator();
//			
//			filmIterator.setFilms(allFilms);
//			filmIterator.setNameFilter(nameFilter);
//			filmIterator.setAuthorFilter(authorFilter);
//			filmIterator.setActorFilter(actorFilter);
//			filmIterator.setCategoryFilter(categoryFilter);
//
//			while (filmIterator.hasNext()) {
//				Film filteredFilm = filmIterator.next();
//				System.out.println(filteredFilm.toString());
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
	public boolean createUserAccount(User user, String email, String password) {
		return facadeBd.createUserAccount(user, email, password);
	}

	public Account userLogin(String email, String pwd){
		this.account = facadeBd.userLogin(email, pwd);
		return this.account;
	}

	public void modifyAccountInformation(Account account, String newFirstName, String newLastName, Date newDob, String oldPassword, String newPassword) {
		this.account = facadeBd.modifyAccountInformation(account, newFirstName, newLastName, newDob, oldPassword, newPassword);
	}
	public boolean setWeeklyRentalLimit(Account account, int weeklyLimit) {
		return facadeBd.setWeeklyRentalLimit(account, weeklyLimit);
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
		Account retrievedAccount = facadeBd.unsubscribeFromService(this.account);
		if (retrievedAccount != null) {
			this.account = retrievedAccount;
			return true;
		}
		return false;
	}

	public ArrayList<Film> getAllFilms() {
		ArrayList<Film> films = (ArrayList<Film>) facadeBd.getAllFilms(this.account);
		return films;
	}

	public List<List<Object>> getTopFilmsWeek(){
		List<List<Object>> topWeekFilms =  facadeBd.getTopFilmsWeek(this.account);
		return topWeekFilms;
	}
	public List<List<Object>> getTopFilmsMonth(){
		List<List<Object>> topMonthFilms =  facadeBd.getTopFilmsMonth(this.account);
		return topMonthFilms;
	}
	public ArrayList<Category> getAccountBannedCategories(Account account) {
		ArrayList<Category> bannedCategories = (ArrayList<Category>) facadeBd.getBannedCategories(account);
		return bannedCategories;
	}

	public ArrayList<Reservation> getCurrentReservationsByAccount() {
		return facadeBd.getCurrentReservationsByAccount(this.account);
	}

	public boolean ReserveBlueRay(Account account, BlueRay blueRay) {
		 return facadeBd.ReserveBlueRay(account, blueRay);
	}

	public boolean ReserveQrCode(Account account, Film film) {
		return facadeBd.ReserveQrCode(account, film);
	}
	public boolean addMoneyToCard(SubscriberCard subscriberCard, double amount) {
		return facadeBd.addMoneyToCard(subscriberCard, amount);
	}

	public boolean processPaymentBySubscriberCard(SubscriberCard subscriberCard, double amount) {
		return facadeBd.processPaymentBySubscriberCard(subscriberCard, amount);
	}

	public boolean requestUnavailableFilm(Account account, String filmName) {
		return facadeBd.requestUnavailableFilm(account, filmName);
	}

	public boolean banFilmCategories(Account account, List<Category> categories) {
		return facadeBd.banFilmCategories(account, categories);
	}

	public boolean unbanFilmCategories(Account account, Category category) {
		return facadeBd.unbanFilmCategories(account, category);
	}
	public boolean addReservationToHistoric(BlueRay blueRay) {
		return facadeBd.addReservationToHistoric(blueRay);
	}
	public Account getAccount(){ return this.account;}

	public void setAccount(Account account) {
		this.account = account;
	}
}