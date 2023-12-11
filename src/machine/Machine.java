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
	
	public Account userLoginWithCard(String cardNumber, String password) {
		this.account = facadeBd.userLoginWithCard(cardNumber, password);
		return this.account;
    }
	
	public void userLogOut() {
		this.account = null;
	}

	public Account modifyAccountInformation(String newFirstName, String newLastName, String oldPassword, String newPassword) {
		return this.account = facadeBd.modifyAccountInformation(this.account, newFirstName, newLastName, oldPassword, newPassword);
	}
	
	public boolean setWeeklyRentalLimit(int weeklyLimit) {
		return facadeBd.setWeeklyRentalLimit(this.account, weeklyLimit);
	}
	
	public Account subscribeToService() {
		SubscriberAccount subAccount = (SubscriberAccount) facadeBd.subscribeToService(account);
		if (subAccount != null) {
			this.account = subAccount;
		}
		return this.account;
	}

	public Account unsubscribeFromService() {
		Account retrievedAccount = facadeBd.unsubscribeFromService(this.account);
		if (retrievedAccount != null) {
			this.account = retrievedAccount;
		}
		return this.account;
	}

	public ArrayList<Film> getAllFilms() {
		ArrayList<Film> films = (ArrayList<Film>) facadeBd.getAllFilms(this.account);
		return films;
	}

	public ArrayList<Film>  getTopFilmsWeek(){
		List<List<Object>> topWeekFilms =  facadeBd.getTopFilmsWeek(this.account);
		ArrayList<Film> results = new ArrayList<>();
		
        for (List<Object> filmInfo : topWeekFilms) {
            if (filmInfo.size() >= 2) {
                Film film = (Film) filmInfo.get(1);
                results.add(film);
            }
        }
		return results;
	}
	public ArrayList<Film>  getTopFilmsMonth(){
		List<List<Object>> topMonthFilms =  facadeBd.getTopFilmsMonth(this.account);
		ArrayList<Film> results = new ArrayList<>();
		
        for (List<Object> filmInfo : topMonthFilms) {
            if (filmInfo.size() >= 2) {
                Film film = (Film) filmInfo.get(1);
                results.add(film);
            }
        }
		return results;
	}
	public ArrayList<Category> getAccountBannedCategories() {
		return facadeBd.getBannedCategories(this.account);
	}

	public ArrayList<Reservation> getCurrentReservationsByAccount() {
		return facadeBd.getCurrentReservationsByAccount(this.account);
	}

	public boolean ReserveBlueRay(BlueRay blueRay, CreditCard creditCard, SubscriberCard subscriberCard) {
		 return facadeBd.ReserveBlueRay(this.account, blueRay, creditCard, subscriberCard);
	}
	
	public ArrayList<Category> getAllCategories() {
    	return facadeBd.getAllCategories();
    }
	
	public boolean removeCurrentReservation(BlueRay blueRay) {
		return facadeBd.removeCurrentReservation(blueRay);
	}

	public boolean ReserveQrCode(Film film) {
		return facadeBd.ReserveQrCode(this.account, film);
	}
	
	public boolean addMoneyToCard(SubscriberCard subscriberCard, double amount) {
		return facadeBd.addMoneyToCard(subscriberCard, amount);
	}

	public boolean processPaymentBySubscriberCard(SubscriberCard subscriberCard, double amount) {
		return facadeBd.processPaymentBySubscriberCard(subscriberCard, amount);
	}
	
	public boolean processPaymentByCreditCard(CreditCard creditCard, double amount) {
		return facadeBd.processPaymentByCreditCard(creditCard, amount);
	}
	
	public boolean reportLostBlueRayDisc(BlueRay blueRay) {
        return facadeBd.reportLostBlueRayDisc(this.account, blueRay);
    }

	public boolean requestUnavailableFilm(String filmName) {
		return facadeBd.requestUnavailableFilm(this.account, filmName);
	}

	public boolean banFilmCategories(List<Category> categories) {
		return facadeBd.banFilmCategories(this.account, categories);
	}

	public boolean unbanFilmCategories(Category category) {
		return facadeBd.unbanFilmCategories(this.account, category);
	}
	
	public boolean addReservationToHistoric(BlueRay blueRay) {
		return facadeBd.addReservationToHistoric(blueRay);
	}
	
	public ArrayList<HistoricReservation> getHistoricReservations() {
    	return facadeBd.getHistoricReservations(this.account);
    }
	
	public ArrayList<BlueRay> getAllAvailableBlueRays() {
		return facadeBd.getAllAvailableBlueRays();
	}
	
    public boolean addCreditCardToAccount() {
    	return facadeBd.addCreditCardToAccount(this.account);
    }
    
    public boolean addSubscriberCardToAccount() {
    	return facadeBd.addSubscriberCardToAccount(this.account);
    }
    
    public Film getFilmInformation(String filmName) {
        return facadeBd.getFilmInformation(this.account, filmName);
    }
	
	public Account getAccount(){ return this.account;}

	public void setAccount(Account account) {
		this.account = account;
	}
}