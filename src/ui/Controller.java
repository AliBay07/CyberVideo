package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import beans.Account;
import beans.Actor;
import beans.Author;
import beans.BlueRay;
import beans.Category;
import beans.Film;
import beans.NormalAccount;
import beans.QrCode;
import beans.SubscriberAccount;
import coo.classes.FilmFilterIterator;
import facade.ui.FacadeIHM;

/**
 * Controller pour le mode MVC
 */
public class Controller {

	FacadeIHM facadeIHM;
	JFrame frame;
	BasePage currentPage;
	Account currentAccount;
	Film currentFilm;
	BlueRay currentBlueRay;
	QrCode currentQrCode;
	String researchedFilm;

	// State des pages
	enum State { IDLE, SIGNIN_NORMAL, SIGNUP_NORMAL, SIGNIN_FOR_RENT, SIGNUP_FOR_RENT, SIGNIN_FOR_SUBSCRIBE,
		SIGNUP_FOR_SUBSCRIBE, SIGNIN_FOR_RETURN_FILM, LOGGED_NORMAL, LOGGED_PREMIUM, CHANGE_ACCOUNT_SETTING,
		SHOW_FILMS_RESULTS, SHOW_BLURAY_RESULTS, SHOW_FILM_DETAILS, RENT_FILM,
		SHOW_RETURN_PAGE, SHOW_ERROR_RETURN_PAGE};
	State state = State.IDLE;

	// page précédent pour Back
	BasePage oldPage;
	State oldState;

	public Controller(JFrame frame, FacadeIHM fihm) {
		this.frame = frame;
		facadeIHM = fihm;
		currentAccount = null;
		researchedFilm = "";
	}

	public void traite(BasePage page, Keyword action){
		if(action==Keyword.LOGIN) {
			//            frame.remove(currentPage);
			switch(state){
				case SIGNIN_FOR_RETURN_FILM:
					showReturnBlueRayPage();
					state = State.SHOW_RETURN_PAGE;
					break;
				default:
					if(currentAccount instanceof NormalAccount)
						state = State.LOGGED_NORMAL;
					else if(currentAccount instanceof SubscriberAccount)
						state = State.LOGGED_PREMIUM;
					showMainPage();
			}
		}
		else if(action==Keyword.SHOWLOGINPAGE) {
			saveOldPage();
			switch (state) {
				case IDLE:
					state = State.SIGNIN_NORMAL;
					break;
				case SHOW_FILMS_RESULTS:
					state = State.SIGNIN_FOR_RENT;
					break;
				case SHOW_BLURAY_RESULTS:
					state = State.SIGNIN_FOR_RENT;
					break;
				default:
					break;
			}
			showLoginPage();
		}else
		if(action==Keyword.SUBSCRIBE_SUCCESS){
			if(state==State.LOGGED_NORMAL){
				((MainPage)currentPage).updateUI_Account();
			}else{
				showMainPage();
			}
			state = State.LOGGED_PREMIUM;
		}else
		if(action==Keyword.SHOWRETURNBLURAYPAGE){
			switch(state) {
				case IDLE:
					showLoginPage();
					state = State.SIGNIN_FOR_RETURN_FILM;
					break;
				case LOGGED_NORMAL:
					showReturnBlueRayPage();
					state = State.SHOW_RETURN_PAGE;
					break;
				case LOGGED_PREMIUM:
					showReturnBlueRayPage();
					state = State.SHOW_RETURN_PAGE;
					break;
				default:
					break;
			}
		}else
		if(action == Keyword.SHOWADVANCEDRESEARCH){
			ArrayList<Film> results = new ArrayList<Film>();
			HashMap<String, ArrayList<String>> criterias = ((MainPage)currentPage).getChosenCriterias();
			FilmFilterIterator filmIterator = new FilmFilterIterator();
			filmIterator.setFilms(facadeIHM.getAllFilms());

			List<Category> categoryFilter = new ArrayList<Category>();
			if(criterias.containsKey("categorie")){
				for(String category : criterias.get("categorie")){
					Category chosenCategory = new Category();
					chosenCategory.setCategoryName(category);
					categoryFilter.add(chosenCategory);
				}
			}

			List<Actor> actorFilter = new ArrayList<>();
			if (criterias.containsKey("actors")) {
				for (String actor : criterias.get("actors")) {
					String[] actorNames = actor.trim().toLowerCase().split("\\s+");

					Actor chosenActor = new Actor();
					if (actorNames.length > 0) {
						chosenActor.setFirstName(actorNames[0]);

						if (actorNames.length > 1) {
							chosenActor.setLastName(actorNames[1]);
						} else {
							chosenActor.setLastName("");
						}
					}
					actorFilter.add(chosenActor);
				}
			}

			List<Author> authorFilter = new ArrayList<>();
			if (criterias.containsKey("authors")) {
				for (String author : criterias.get("authors")) {
					String[] authorNames = author.trim().toLowerCase().split("\\s+");

					Author chosenAuthor = new Author();
					if (authorNames.length > 0) {
						chosenAuthor.setFirstName(authorNames[0]);

						if (authorNames.length > 1) {
							chosenAuthor.setLastName(authorNames[1]);
						} else {
							chosenAuthor.setLastName("");
						}
					}
					authorFilter.add(chosenAuthor);
				}
			}


			filmIterator.setCategoryFilter(categoryFilter);
			filmIterator.setActorFilter(actorFilter);
			filmIterator.setAuthorFilter(authorFilter);

			System.out.println(actorFilter);
			System.out.println(authorFilter);
			System.out.println(categoryFilter);
			filmIterator.reset();
			while (filmIterator.hasNext()) {
				Film filteredFilm = filmIterator.next();
				results.add(filteredFilm);
			}
			state = State.SHOW_FILMS_RESULTS;
			showResearchPage(Section.ADVANCED, results, null); //Vérifier le state !
		}
		else if(action == Keyword.SHOWTOP10W){
			ArrayList<Film> results = facadeIHM.getTopFilmsOfTheWeek();
			state = State.SHOW_FILMS_RESULTS;
			showResearchPage(Section.TOP10W, results, null);
		}
		else if(action == Keyword.SHOWTOP10M){
			ArrayList<Film> results = facadeIHM.getTopFilmsOfTheMonth();
			state = State.SHOW_FILMS_RESULTS;
			showResearchPage(Section.TOP10M, results, null);
		}
		else if(action == Keyword.SHOWBLURAY){
			ArrayList<BlueRay> results = facadeIHM.getAvailableBlueRays();
			state = State.SHOW_BLURAY_RESULTS;
			showResearchPage(Section.BLURAY, null, results);
		}
		else if(action == Keyword.SHOWALLFILMS){
			ArrayList<Film> results = facadeIHM.getAllFilms();
			state = State.SHOW_FILMS_RESULTS;
			showResearchPage(Section.ALL, results, null);
		}else
		if(action == Keyword.SHOWTEXTRESEARCH){
			Film film = facadeIHM.getFilmInformation(researchedFilm);
			ArrayList<Film> films = new ArrayList<Film>();
			if(film != null)
				films.add(film);
			state = State.SHOW_FILMS_RESULTS;
			showResearchPage(Section.ADVANCED, films, null);
		}else
		if(action == Keyword.BACK){
			switch(state){
				case SHOW_BLURAY_RESULTS:
					//                    frame.remove(currentPage);
					showMainPage();
					changeMainPageState();
					break;
				case SHOW_FILMS_RESULTS:
					showMainPage();
					changeMainPageState();
					break;
				case SHOW_RETURN_PAGE:
					showMainPage();
					changeMainPageState();
					break;
				case CHANGE_ACCOUNT_SETTING:
					showMainPage();
					changeMainPageState();
					break;
				case SHOW_FILM_DETAILS:
					frame.remove(currentPage);
					back();
					if(currentPage instanceof MainPage)
					{
						if (currentAccount == null)
						{
							state = State.IDLE;
						}
						else if (currentAccount instanceof NormalAccount)
						{
							state = State.LOGGED_NORMAL;
						}
						else if (currentAccount instanceof SubscriberAccount)
						{
							state = State.LOGGED_PREMIUM;
						}
					}
					else
					if(getCurrentFilm()==null){
						state = State.SHOW_FILMS_RESULTS;
						//showResearchResult global
					}else{
						state = State.SHOW_BLURAY_RESULTS;
					}
					break;
				case SIGNIN_NORMAL:
					state = State.IDLE;
					back();
					break;
				//Il faut remodifier le state quand on fait un back ! écrire tous les cas
				case SIGNUP_NORMAL:
					state = State.SIGNIN_NORMAL;
				default:
					back();

			}
		}else
		if(action == Keyword.SHOWFILMDETAILS){
			switch(state){ //A tester
				case SHOW_FILMS_RESULTS:
					showDetailedFilm(getCurrentFilm());
					state = State.SHOW_FILM_DETAILS;
					break;
				case SHOW_BLURAY_RESULTS:
					showDetailedBlueRay(getCurrentBlueRay());
					state = State.SHOW_FILM_DETAILS;
					break;
				case IDLE:
					saveOldPage();
					if(currentFilm == null){
						showDetailedBlueRay(getCurrentBlueRay());
					}
					else{
						showDetailedFilm(getCurrentFilm());
					}
					state = State.SHOW_FILM_DETAILS;
					break;
				case LOGGED_NORMAL:
					saveOldPage();
					if(currentFilm == null){
						showDetailedBlueRay(getCurrentBlueRay());
					}
					else{
						showDetailedFilm(getCurrentFilm());
					}
					state = State.SHOW_FILM_DETAILS;
					break;
				case LOGGED_PREMIUM:
					saveOldPage();
					if(currentFilm == null){
						showDetailedBlueRay(getCurrentBlueRay());
					}
					else{
						showDetailedFilm(getCurrentFilm());
					}
					state = State.SHOW_FILM_DETAILS;
				default:
					break;
			}
		}else
		if(action == Keyword.SHOWSIGNUPPAGE){
			showSignupPage();
			state = State.SIGNUP_NORMAL;
		}else
		if (action == Keyword.RENTED_BlueRay_FILM) {
			switch (state) {
				case SHOW_BLURAY_RESULTS:
					showDetailedBlueRay(getCurrentBlueRay());
					state = State.SHOW_FILM_DETAILS;
					break;
				case SHOW_FILM_DETAILS:
					showMainPage();
					//facadeIHM.rentBlueRay(getCurrentBlueRay());
					if(currentAccount instanceof NormalAccount)
						state = State.LOGGED_NORMAL;
					else if(currentAccount instanceof SubscriberAccount)
						state = State.LOGGED_PREMIUM;
					break;
				default:
					break;
			}
		}else
		if(action == Keyword.RENTED_QrCode_FILM)
		{
			switch (state) {
				case SHOW_FILMS_RESULTS:
					showDetailedFilm(getCurrentFilm());
					state = State.SHOW_FILM_DETAILS;
					break;
				case SHOW_FILM_DETAILS:
					showMainPage();
					//facadeIHM.printQrCode(this.getCurrentQrCode());
					if(currentAccount instanceof NormalAccount)
						state = State.LOGGED_NORMAL;
					else if(currentAccount instanceof SubscriberAccount)
						state = State.LOGGED_PREMIUM;
					break;
				default:
					break;
			}
		}else
		if(action == Keyword.SIGNUP){
			// il faut encore distinguer SINGUP_FOR_RENT et SINGUP_FOR_SUSCRIBE ?
			state = State.SIGNUP_NORMAL;
			showMainPage();
		}else
		if(action == Keyword.SHOWACCOUNTINFOPAGE){
			showAccountInfoPage();
			state = State.CHANGE_ACCOUNT_SETTING;
		}else
		if(action == Keyword.CHANGE_ACCOUNT_INFO){
			//@TODO après la modification des infos d'account
			showMainPage();
			changeMainPageState();
		}else
		if(action == Keyword.LOGOUT){
			logout();
		}else
		if(action == Keyword.UNSUBSCRIBE_SUCCESS){
			if(state==State.LOGGED_PREMIUM){
				((MainPage)currentPage).updateUI_Account();
			}else{
				showMainPage();
			}
			state = State.IDLE;
		}
	}

	private void logout() {
		if(currentAccount!=null){
			facadeIHM.userLogOut();
			currentAccount = null;
			state = State.IDLE;
			showMainPage();
			JOptionPane.showMessageDialog(frame, "Logout success!", "Logout", JOptionPane.PLAIN_MESSAGE);
		}
	}

	//    public void exitPage(BasePage page) {
	//        back();
	//    }

	private void changeMainPageState(){
		if(currentAccount == null)
			state = State.IDLE;
		else if(currentAccount instanceof NormalAccount)
			state = State.LOGGED_NORMAL;
		else
			state = State.LOGGED_PREMIUM;
	}

	public void showMainPage() {
		System.out.println("show ... MainPage");
		MainPage page = new MainPage(frame, this);
		page.setController(this);
		showPage(page);
	}


	public void showLoginPage() {
		LoginPage page = new LoginPage(frame);
		page.setController(this);
		showPage(page);
	}

	public void showSignupPage() {
		SignupPage page = new SignupPage(frame);
		page.setController(this);
		showPage(page);
	}

	public void showResearchPage(Section s, ArrayList<Film> films, ArrayList<BlueRay> blueRays){
		ResearchResults page;
		if(s == Section.BLURAY){
			page = new ResearchResults(frame, this, s, blueRays, null);
			page.showResearchResults();
			showPage(page);
			return;
		}
		if(s == Section.ADVANCED){
			//TODO
			if(researchedFilm.equals(""))
				page = new ResearchResults(frame, this, s, null, films);
			else
				page = new ResearchResults(frame, this, s, "Recherche avancée", films);
			page.showResearchResults();
			showPage(page);
			return;
			//Rechercher les films par critères
		}
		page = new ResearchResults(frame, this, s, null, films);
		page.showResearchResults();
		showPage(page);
	}

		public void showResearchPage(Section s, ArrayList<Film> films){
			ResearchResults page;
			if(s == Section.CATEGORY){
				page = new ResearchResults(frame, this, s, ((MainPage) currentPage).getChosenCriterias().get("categorie").get(0), films);
				page.showResearchResults();
				showPage(page);
				return;
			}
			page = new ResearchResults(frame, this, s, null, films);
			page.showResearchResults();
			showPage(page);
		}

		public void showReturnBlueRayPage() {
			ReturnBlueRayPage page = new ReturnBlueRayPage(frame, this);
			showPage(page);
		}

		public void showDetailedFilm(Film f){
			Affichage_Film page = new Affichage_Film(frame,f,this);
			showPage(page.afficher());
		}

		public void showDetailedBlueRay(BlueRay blueRay){
			Affichage_Film page = new Affichage_Film(frame,blueRay,this);
			showPage(page.afficher());
		}

		public void showAccountInfoPage(){
			AccountInfoPage page = new AccountInfoPage(frame, this);
			showPage(page);
		}

		private void showPage(BasePage page) {
			if(currentPage!=null){
				currentPage.setVisible(false);
				frame.remove(currentPage);   // mettre plutot le remove ici ?
			}
			currentPage = page;
			currentPage.setVisible(true);
			frame.add(currentPage);
			frame.setVisible(true);
		}

		public FacadeIHM getFacadeIHM(){
			return facadeIHM;
		}

		public Account getCurrentAccount(){ //Trouver un moyen lorsque utilisateur non connecté de pouvoir avoir un Account par défaut pour faire les vérifications pour la MainPage
			if(currentAccount == null)
				return null;
			return currentAccount;
		}

		public void setAccount(Account acc){
			this.currentAccount = acc;
		}

		// il faut l'appel avant changer l'état
		private void saveOldPage() {
			oldPage = currentPage;
			oldState = state;
		}

		// sortie le current page
		private void back() {
			if(currentPage!=null && oldPage!=null){
				System.out.println("Back ... state: "+state+" -> "+oldState);
				currentPage.setVisible(false);
				currentPage = oldPage;
				state = oldState;
				frame.remove(oldPage);
				oldPage = null;
				frame.add(currentPage);
				currentPage.setVisible(true);
				frame.setVisible(true);
			}
			else{
				System.out.println("Je veux revenir en arrière !");
				// new mainPage ??
				showMainPage();
			}
		}
		/*BasePage lastPage = pagePile.pop();
        if(lastPage!=null){
            frame.remove(lastPage);
        }
        curPage = pagePile.getLast();
        if(curPage!=null){
            System.out.println("Back to :["+curPage.getClass()+"]... pile_size:"+pagePile.size());
            curPage.setVisible(true);
        }*/

    public void setCurrentFilm(Film film) {
        this.currentFilm = film;
    }

    public Film getCurrentFilm() {
        return this.currentFilm;
    }

    public BlueRay getCurrentBlueRay() {
        return this.currentBlueRay;
    }

    public void setCurrentBlueRay(BlueRay blueRay) {
        this.currentBlueRay = blueRay;
    }

    public QrCode getCurrentQrCode() {
        return this.currentQrCode;
    }

    public void setCurrentQrCode(QrCode qrCode) {
        this.currentQrCode = qrCode;
    }

    public void showRentalHistoryDialog() {
        RentalHistoryPage historyPage = new RentalHistoryPage(frame, this);
        JDialog dialog = showDialog(historyPage);
        historyPage.setBackListener(new RentalHistoryPage.BackListener() {
            @Override
            public void backClicked() {
                dialog.dispose();
            }
        });
    }

    private JDialog showDialog(JPanel panel) {
        JDialog dialog = new JDialog();
//        dialog.setLayout(new BoxLayout(dialog, BoxLayout.Y_AXIS));
        dialog.setSize(SysAL2000.DIALOG_WIDTH, SysAL2000.DIALOG_HEIGHT);
        dialog.setLocationRelativeTo(null);
        dialog.add(panel);
        dialog.setVisible(true);
        return dialog;
    }

	public void setResearchedFilm(String text){
		researchedFilm = text;
	}
}
