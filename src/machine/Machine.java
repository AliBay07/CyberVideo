package machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Account;
import beans.Film;
import dao.tools.Session;
import facade.bd.FacadeBd;

public class Machine {
	
	FacadeBd facadeBd = new FacadeBd();
	Account account = null;

	public static void main(String[] args) {
		
		Session session = new Session(false);
		
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
	
	public void getAllFilms() {
		 ArrayList<Film> films = (ArrayList<Film>) facadeBd.getAllFilms(account);
		 		 
		 for (Film f : films) {
			 System.out.println(f);
		 }
    }

}
