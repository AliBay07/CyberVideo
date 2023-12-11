package ui;

import beans.*;
import facade.ui.FacadeIHM;

import java.util.*;

import javax.swing.*;

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
		if(action==Keyword.SUBSCRIBE){
			switch (state) {
				case IDLE:
					showLoginPage();
					state = State.SIGNIN_FOR_SUBSCRIBE;
					break;
				case LOGGED_NORMAL:
					//Voir s'il faut afficher une pop up pour informer l'utilisateur qu'il s'est bien abonné
					facadeIHM.subscribeToService();
					break;
				case LOGGED_PREMIUM:
					//Show pop up ou page pour créditer sa carte abonnée
				default:
					break;
			}
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
			ArrayList<Film> results = facadeIHM.searchFilmByCriteria(((MainPage)currentPage).getChosenCriterias());
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
		}
		else if(action == Keyword.SHOWCATEGORIES){
			ArrayList<Film> results = facadeIHM.searchFilmByCriteria(((MainPage)currentPage).getChosenCriterias());
			state = State.SHOW_FILMS_RESULTS;
			showResearchPage(Section.CATEGORY, results, null);
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
					facadeIHM.rentBlueRay(getCurrentBlueRay());
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
					facadeIHM.printQrCode(this.getCurrentQrCode());
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
		if(s == Section.CATEGORY){
			page = new ResearchResults(frame, this, s, ((MainPage) currentPage).getChosenCriterias().get("categorie").get(0), films);
			page.showResearchResults();
			showPage(page);
			return;
		}
		if(s == Section.BLURAY){
			page = new ResearchResults(frame, this, s, blueRays, null);
			page.showResearchResults();
			showPage(page);
			return;
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
			Affichage_Film page = new Affichage_Film(frame,f,this );
			showPage(page.afficher());
			saveOldPage();
		}

		public void showDetailedBlueRay(BlueRay blueRay){
			Affichage_Film page = new Affichage_Film(frame,blueRay,this );
			showPage(page.afficher());
			saveOldPage();
		}

		public void showAccountInfoPage(){
			AccountInfoPage page = new AccountInfoPage(frame);
			page.setController(this);
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
}
