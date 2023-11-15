package dao.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import beans.Account;
import beans.Actor;
import beans.Author;
import beans.Category;
import beans.Film;
import beans.NormalAccount;
import beans.SubscriberAccount;
import beans.User;

public class FilmDao extends Dao<Film> {

	public FilmDao(Connection connection) {
		super(connection);
	}

	public List<Film> getAllFilms(Account account) {

		boolean is_subscriber = false;
		String query = "";
		if (account != null) {
			String selectQuery = "SELECT * FROM Account WHERE Account.email = ?";
			try (PreparedStatement statementAccount = connection.prepareStatement(selectQuery)) {
				statementAccount.setString(1, account.getEmail());
				try (ResultSet resultSet = statementAccount.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
									" FROM FilmCategory fc " +
									" INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f";
						} else {
							is_subscriber = true;
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
									" FROM FilmCategory fc " +
									" INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"WHERE f.id NOT IN " +
									" (SELECT DISTINCT f.id " +
									" FROM Film f " +
									" INNER JOIN FilmCategory fc ON f.id = fc.id_film " +
									" INNER JOIN Category c ON fc.id_category = c.id " +
									" INNER JOIN AccountFilterCategory afc ON c.id = afc.id_category " +
									" WHERE afc.id_account = ?)";
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}

		List<Film> films = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			if (is_subscriber) {
				statement.setLong(1, account.getId());
			}
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Film film = new Film();
				film.setId(resultSet.getLong("id"));
				film.setName(resultSet.getString("name"));
				film.setDuration(resultSet.getInt("duration"));
				film.setDescription(resultSet.getString("description"));
				film.setPath(resultSet.getString("image_path"));
				
				String actorNames = resultSet.getString("actors");
				if (actorNames != null) {
					String[] actorNameArray = actorNames.split(",");
					List<Actor> actors = new ArrayList<>();
					for (String actorName : actorNameArray) {
						Actor actor = new Actor();
						String[] nameParts = actorName.trim().split(" ");
						if (nameParts.length == 2) {
							actor.setFirstName(nameParts[0]);
							actor.setLastName(nameParts[1]);
							actors.add(actor);
						}
					}
					film.setActors(actors);
				}

				String authorNames = resultSet.getString("authors");
				if (authorNames != null) {
					String[] authorNameArray = authorNames.split(",");
					List<Author> authors = new ArrayList<>();
					for (String authorName : authorNameArray) {
						Author author = new Author();
						String[] nameParts = authorName.trim().split(" ");
						if (nameParts.length == 2) {
							author.setFirstName(nameParts[0]);
							author.setLastName(nameParts[1]);
							authors.add(author);
						}
					}
					film.setAuthors(authors);
				}

				String categoryNames = resultSet.getString("categories");
				if (!(categoryNames.replace(":", "").equals(""))) {
					String[] categoryNameArray = categoryNames.split(",");
					List<Category> categories = new ArrayList<>();
					for (String categoryName : categoryNameArray) {
						Category category = new Category();
						String[] categoryNameIdArray = categoryName.split(":");
						category.setId(Long.parseLong(categoryNameIdArray[0].trim()));
						category.setCategoryName(categoryNameIdArray[1].trim());
						categories.add(category);
					}
					film.setCategories(categories);
				}

				films.add(film);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

	public List<List<Object>> getTopFilmsMonth(Account account) {

		boolean is_subscriber = false;
		String query = "";
		if (account != null) {
			String selectQuery = "SELECT * FROM Account WHERE Account.email = ?";
			try (PreparedStatement statementAccount = connection.prepareStatement(selectQuery)) {
				statementAccount.setString(1, account.getEmail());
				try (ResultSet resultSet = statementAccount.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"tm.number_reservations, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id)  " +
									" FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"INNER JOIN TopFilmsMonth tm ON f.id = tm.id_film " +
									"ORDER BY tm.number_reservations DESC";
						} else {
							is_subscriber = true;
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"tm.number_reservations, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
									" FROM FilmCategory fc " +
									" INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"INNER JOIN TopFilmsMonth tm ON f.id = tm.id_film " +
									"WHERE f.id NOT IN " +
									" (SELECT DISTINCT f.id " +
									" FROM Film f " +
									" INNER JOIN FilmCategory fc ON f.id = fc.id_film " +
									" INNER JOIN Category c ON fc.id_category = c.id " +
									" INNER JOIN AccountFilterCategory afc ON c.id = afc.id_category " +
									" WHERE afc.id_account = ?) " +
									"ORDER BY tm.number_reservations DESC";
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}

		List<List<Object>> topFilms = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			if (is_subscriber) {
				statement.setLong(1, account.getId());
			}
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				List<Object> filmData = new ArrayList<>();

				Film film = new Film();
				film.setId(resultSet.getLong("id"));
				film.setName(resultSet.getString("name"));
				film.setDuration(resultSet.getInt("duration"));
				film.setDescription(resultSet.getString("description"));
				film.setPath(resultSet.getString("image_path"));

				String actorNames = resultSet.getString("actors");
				if (actorNames != null) {
					String[] actorNameArray = actorNames.split(",");
					List<Actor> actors = new ArrayList<>();
					for (String actorName : actorNameArray) {
						Actor actor = new Actor();
						String[] nameParts = actorName.trim().split(" ");
						if (nameParts.length == 2) {
							actor.setFirstName(nameParts[0]);
							actor.setLastName(nameParts[1]);
							actors.add(actor);
						}
					}
					film.setActors(actors);
				}

				String authorNames = resultSet.getString("authors");
				if (authorNames != null) {
					String[] authorNameArray = authorNames.split(",");
					List<Author> authors = new ArrayList<>();
					for (String authorName : authorNameArray) {
						Author author = new Author();
						String[] nameParts = authorName.trim().split(" ");
						if (nameParts.length == 2) {
							author.setFirstName(nameParts[0]);
							author.setLastName(nameParts[1]);
							authors.add(author);
						}
					}
					film.setAuthors(authors);
				}

				String categoryNames = resultSet.getString("categories");
				if (!(categoryNames.replace(":", "").equals(""))) {
					String[] categoryNameArray = categoryNames.split(",");
					List<Category> categories = new ArrayList<>();
					for (String categoryName : categoryNameArray) {
						Category category = new Category();
						String[] categoryNameIdArray = categoryName.split(":");
						category.setId(Long.parseLong(categoryNameIdArray[0].trim()));
						category.setCategoryName(categoryNameIdArray[1].trim());
						categories.add(category);
					}
					film.setCategories(categories);
				}

				filmData.add(film);
				filmData.add(resultSet.getInt("number_reservations"));

				topFilms.add(filmData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return topFilms;
	}

	public List<List<Object>> getTopFilmsWeek(Account account) {

		boolean is_subscriber = false;
		String query = "";
		if (account != null) {
			String selectQuery = "SELECT * FROM Account WHERE Account.email = ?";
			try (PreparedStatement statementAccount = connection.prepareStatement(selectQuery)) {
				statementAccount.setString(1, account.getEmail());
				try (ResultSet resultSet = statementAccount.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"tw.number_reservations, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id)  " +
									" FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"INNER JOIN TopFilmsWeek tw ON f.id = tw.id_film " +
									"ORDER BY tw.number_reservations DESC";
						} else {
							is_subscriber = true;
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"tw.number_reservations, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									"FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									"WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									"FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									"WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
									"FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
									"WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"INNER JOIN TopFilmsWeek tw ON f.id = tw.id_film " +
									"WHERE f.id NOT IN ( " +
									"SELECT DISTINCT f.id " +
									"FROM Film f " +
									"INNER JOIN FilmCategory fc ON f.id = fc.id_film " +
									"INNER JOIN Category c ON fc.id_category = c.id " +
									"INNER JOIN AccountFilterCategory afc ON c.id = afc.id_category " +
									"WHERE afc.id_account = ?) " +
									"ORDER BY tw.number_reservations DESC";
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}

		List<List<Object>> topFilms = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			if (is_subscriber) {
				statement.setLong(1, account.getId());
			}
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				List<Object> filmData = new ArrayList<>();

				Film film = new Film();
				film.setId(resultSet.getLong("id"));
				film.setName(resultSet.getString("name"));
				film.setDuration(resultSet.getInt("duration"));
				film.setDescription(resultSet.getString("description"));
				film.setPath(resultSet.getString("image_path"));

				String actorNames = resultSet.getString("actors");
				if (actorNames != null) {
					String[] actorNameArray = actorNames.split(",");
					List<Actor> actors = new ArrayList<>();
					for (String actorName : actorNameArray) {
						Actor actor = new Actor();
						String[] nameParts = actorName.trim().split(" ");
						if (nameParts.length == 2) {
							actor.setFirstName(nameParts[0]);
							actor.setLastName(nameParts[1]);
							actors.add(actor);
						}
					}
					film.setActors(actors);
				}

				String authorNames = resultSet.getString("authors");
				if (authorNames != null) {
					String[] authorNameArray = authorNames.split(",");
					List<Author> authors = new ArrayList<>();
					for (String authorName : authorNameArray) {
						Author author = new Author();
						String[] nameParts = authorName.trim().split(" ");
						if (nameParts.length == 2) {
							author.setFirstName(nameParts[0]);
							author.setLastName(nameParts[1]);
							authors.add(author);
						}
					}
					film.setAuthors(authors);
				}

				String categoryNames = resultSet.getString("categories");
				if (!(categoryNames.replace(":", "").equals(""))) {
					String[] categoryNameArray = categoryNames.split(",");
					List<Category> categories = new ArrayList<>();
					for (String categoryName : categoryNameArray) {
						Category category = new Category();
						String[] categoryNameIdArray = categoryName.split(":");
						category.setId(Long.parseLong(categoryNameIdArray[0].trim()));
						category.setCategoryName(categoryNameIdArray[1].trim());
						categories.add(category);
					}
					film.setCategories(categories);
				}

				filmData.add(film);
				filmData.add(resultSet.getInt("number_reservations"));

				topFilms.add(filmData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return topFilms;
	}

	public Film getFilmInformation(Account account, String filmName) {

		boolean is_subscriber = false;
		String query = "";
		if (account != null) {
			String selectQuery = "SELECT * FROM Account WHERE Account.email = ?";
			try (PreparedStatement statementAccount = connection.prepareStatement(selectQuery)) {
				statementAccount.setString(1, account.getEmail());
				try (ResultSet resultSet = statementAccount.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id)  " +
									" FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"WHERE f.name = ?";
						} else {
							is_subscriber = true;
							query = "SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									"FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									"WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									"FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									"WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
									"FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
									"WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"WHERE f.name = ? AND f.id NOT IN ( " +
									"SELECT DISTINCT f.id " +
									"FROM Film f " +
									"INNER JOIN FilmCategory fc ON f.id = fc.id_film " +
									"INNER JOIN Category c ON fc.id_category = c.id " +
									"INNER JOIN AccountFilterCategory afc ON c.id = afc.id_category " +
									"WHERE afc.id_account = ?)";

						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}

		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, filmName);

			if (is_subscriber) {
				statement.setLong(2, account.getId());
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Film film = new Film();
					film.setId(resultSet.getLong("id"));
					film.setName(resultSet.getString("name"));
					film.setDuration(resultSet.getInt("duration"));
					film.setDescription(resultSet.getString("description"));
					film.setPath(resultSet.getString("image_path"));

					String actorNames = resultSet.getString("actors");
					if (actorNames != null) {
						String[] actorNameArray = actorNames.split(", ");
						List<Actor> actors = new ArrayList<>();
						for (String actorName : actorNameArray) {
							Actor actor = new Actor();
							String[] nameParts = actorName.split(" ");
							if (nameParts.length == 2) {
								actor.setFirstName(nameParts[0]);
								actor.setLastName(nameParts[1]);
								actors.add(actor);
							}
						}
						film.setActors(actors);
					}

					String authorNames = resultSet.getString("authors");
					if (authorNames != null) {
						String[] authorNameArray = authorNames.split(", ");
						List<Author> authors = new ArrayList<>();
						for (String authorName : authorNameArray) {
							Author author = new Author();
							String[] nameParts = authorName.split(" ");
							if (nameParts.length == 2) {
								author.setFirstName(nameParts[0]);
								author.setLastName(nameParts[1]);
								authors.add(author);
							}
						}
						film.setAuthors(authors);
					}

					String categoryNames = resultSet.getString("categories");
					if (!(categoryNames.replace(":", "").equals(""))) {
						String[] categoryNameArray = categoryNames.split(",");
						List<Category> categories = new ArrayList<>();
						for (String categoryName : categoryNameArray) {
							Category category = new Category();
							String[] categoryNameIdArray = categoryName.split(":");
							category.setId(Long.parseLong(categoryNameIdArray[0].trim()));
							category.setCategoryName(categoryNameIdArray[1].trim());
							categories.add(category);
						}
						film.setCategories(categories);
					}

					return film;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}


	public List<Film> searchFilmByCriteria(Account account, Map<String, String> filters) {

		boolean is_subscriber = false;
		StringBuilder queryBuilder = new StringBuilder("");
		if (account != null) {
			String selectQuery = "SELECT * FROM Account WHERE Account.email = ?";
			try (PreparedStatement statementAccount = connection.prepareStatement(selectQuery)) {
				statementAccount.setString(1, account.getEmail());
				try (ResultSet resultSet = statementAccount.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							queryBuilder.append("SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
									"(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
									" FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
									" WHERE fa.id_film = f.id) AS actors, " +
									"(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
									" FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
									" WHERE fau.id_film = f.id) AS authors, " +
									"(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id)  " +
									" FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
									" WHERE fc.id_film = f.id) AS categories " +
									"FROM Film f " +
									"WHERE 1=1");
						} else {
							is_subscriber = true;
							queryBuilder.append("SELECT f.id, f.name, f.duration, f.image_path, f.description, " +
								    "(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
								    "FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
								    "WHERE fa.id_film = f.id) AS actors, " +
								    "(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
								    "FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
								    "WHERE fau.id_film = f.id) AS authors, " +
								    "(SELECT LISTAGG(c.id || ':' || c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
								    "FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
								    "WHERE fc.id_film = f.id) AS categories " +
								    "FROM Film f " +
								    "WHERE 1=1 AND f.id NOT IN ( " +
								    "SELECT DISTINCT f.id " +
								    "FROM Film f " +
								    "INNER JOIN FilmCategory fc ON f.id = fc.id_film " +
								    "INNER JOIN Category c ON fc.id_category = c.id " +
								    "INNER JOIN AccountFilterCategory afc ON c.id = afc.id_category " +
								    "WHERE afc.id_account = ?)");
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}

		List<Film> films = new ArrayList<>();

		for (Map.Entry<String, String> entry : filters.entrySet()) {
			String category = entry.getKey();
			String filterValue = entry.getValue().trim().toLowerCase();
			if (!filterValue.isEmpty()) {
				queryBuilder.append(" AND (LOWER(f." + category + ") LIKE ?)");
			}
		}

		String query = queryBuilder.toString();

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			int parameterIndex = 1;
			if (is_subscriber) {
				statement.setLong(parameterIndex++, account.getId());
			}

			for (Map.Entry<String, String> entry : filters.entrySet()) {
				String filterValue = entry.getValue().trim().toLowerCase();
				if (!filterValue.isEmpty()) {
					statement.setString(parameterIndex++, "%" + filterValue + "%");
				}
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Film film = new Film();
					film.setId(resultSet.getLong("id"));
					film.setName(resultSet.getString("name"));
					film.setDuration(resultSet.getInt("duration"));
					film.setDescription(resultSet.getString("description"));
					film.setPath(resultSet.getString("image_path"));

					String actorNames = resultSet.getString("actors");
					if (actorNames != null) {
						String[] actorNameArray = actorNames.split(", ");
						List<Actor> actors = new ArrayList<>();
						for (String actorName : actorNameArray) {
							Actor actor = new Actor();
							String[] nameParts = actorName.trim().split(" ");
							if (nameParts.length == 2) {
								actor.setFirstName(nameParts[0]);
								actor.setLastName(nameParts[1]);
								actors.add(actor);
							}
						}
						film.setActors(actors);
					}

					String authorNames = resultSet.getString("authors");
					if (authorNames != null) {
						String[] authorNameArray = authorNames.split(", ");
						List<Author> authors = new ArrayList<>();
						for (String authorName : authorNameArray) {
							Author author = new Author();
							String[] nameParts = authorName.trim().split(" ");
							if (nameParts.length == 2) {
								author.setFirstName(nameParts[0]);
								author.setLastName(nameParts[1]);
								authors.add(author);
							}
						}
						film.setAuthors(authors);
					}

					String categoryNames = resultSet.getString("categories");
					if (!(categoryNames.replace(":", "").equals(""))) {
						String[] categoryNameArray = categoryNames.split(",");
						List<Category> categories = new ArrayList<>();
						for (String categoryName : categoryNameArray) {
							Category category = new Category();
							String[] categoryNameIdArray = categoryName.split(":");
							category.setId(Long.parseLong(categoryNameIdArray[0].trim()));
							category.setCategoryName(categoryNameIdArray[1].trim());
							categories.add(category);
						}
						film.setCategories(categories);
					}

					films.add(film);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

	// For testing, adding mock data

	public void insertMockData() {
		try {
			connection.setAutoCommit(false);

			String insertFilmQuery = "INSERT INTO Film (name, duration, description) VALUES (?, ?, ?)";
			String insertFilmCategoryQuery = "INSERT INTO FilmCategory (id_film, id_category) VALUES (?, ?)";
			String insertActorQuery = "INSERT INTO Actor (first_name, last_name) VALUES (?, ?)";
			String insertFilmActorQuery = "INSERT INTO FilmActor (id_film, id_actor) VALUES (?, ?)";
			String insertAuthorQuery = "INSERT INTO Author (first_name, last_name) VALUES (?, ?)";
			String insertFilmAuthorQuery = "INSERT INTO FilmAuthor (id_film, id_author) VALUES (?, ?)";
			String insertTopFilmsMonthQuery = "INSERT INTO TopFilmsMonth (id_film, number_reservations) VALUES (?, ?)";
			String insertTopFilmsWeekQuery = "INSERT INTO TopFilmsWeek (id_film, number_reservations) VALUES (?, ?)";
			String insertCategoryQuery = "INSERT INTO Category (category_name) VALUES (?)";
			String insertFilmCategoryFilter= "INSERT INTO AccountFilterCategory (id_account, id_category) VALUES (?, ?)";

			for (int i = 1; i <= 10; i++) {
				long filmId;
				try (PreparedStatement insertFilmStatement = connection.prepareStatement(insertFilmQuery, new String[] {"ID"})) {
					insertFilmStatement.setString(1, "Film " + i);
					insertFilmStatement.setInt(2, 120);
					insertFilmStatement.setString(3, "Description for Film " + i);
					int rowsInserted = insertFilmStatement.executeUpdate();

					if (rowsInserted > 0) {
						try (ResultSet generatedKeys = insertFilmStatement.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								filmId = generatedKeys.getLong(1);
							} else {
								throw new SQLException("Creating Film failed, no ID obtained.");
							}
						}
					} else {
						throw new SQLException("Creating Film failed, no rows affected.");
					}
				}

				for (int j = 1; j <= 3; j++) {
					long actorId;
					try (PreparedStatement insertActorStatement = connection.prepareStatement(insertActorQuery, new String[] {"ID"})) {
						insertActorStatement.setString(1, "Actor" + j);
						insertActorStatement.setString(2, "Lastname" + j);
						int rowsInserted = insertActorStatement.executeUpdate();

						if (rowsInserted > 0) {
							try (ResultSet generatedKeys = insertActorStatement.getGeneratedKeys()) {
								if (generatedKeys.next()) {
									actorId = generatedKeys.getLong(1);
								} else {
									throw new SQLException("Creating Actor failed, no ID obtained.");
								}
							}
						} else {
							throw new SQLException("Creating Actor failed, no rows affected.");
						}
					}

					try (PreparedStatement insertFilmActorStatement = connection.prepareStatement(insertFilmActorQuery)) {
						insertFilmActorStatement.setLong(1, filmId);
						insertFilmActorStatement.setLong(2, actorId);
						insertFilmActorStatement.executeUpdate();
					}
				}

				for (int j = 1; j <= 2; j++) {
					long authorId;
					try (PreparedStatement insertAuthorStatement = connection.prepareStatement(insertAuthorQuery, new String[] {"ID"})) {
						insertAuthorStatement.setString(1, "Author" + j);
						insertAuthorStatement.setString(2, "Lastname" + j);
						int rowsInserted = insertAuthorStatement.executeUpdate();

						if (rowsInserted > 0) {
							try (ResultSet generatedKeys = insertAuthorStatement.getGeneratedKeys()) {
								if (generatedKeys.next()) {
									authorId = generatedKeys.getLong(1);
								} else {
									throw new SQLException("Creating Author failed, no ID obtained.");
								}
							}
						} else {
							throw new SQLException("Creating Author failed, no rows affected.");
						}
					}

					try (PreparedStatement insertFilmAuthorStatement = connection.prepareStatement(insertFilmAuthorQuery)) {
						insertFilmAuthorStatement.setLong(1, filmId);
						insertFilmAuthorStatement.setLong(2, authorId);
						insertFilmAuthorStatement.executeUpdate();
					}
				} 

				try (PreparedStatement insertTopFilmsMonthStatement = connection.prepareStatement(insertTopFilmsMonthQuery)) {
					insertTopFilmsMonthStatement.setLong(1, filmId);
					insertTopFilmsMonthStatement.setInt(2, (int) (Math.random() * 100));
					insertTopFilmsMonthStatement.executeUpdate();
				}

				try (PreparedStatement insertTopFilmsWeekStatement = connection.prepareStatement(insertTopFilmsWeekQuery)) {
					insertTopFilmsWeekStatement.setLong(1, filmId);
					insertTopFilmsWeekStatement.setInt(2, (int) (Math.random() * 100));
					insertTopFilmsWeekStatement.executeUpdate();
				}
				long categoryId = 0;
				for (int j = 1; j <= 3; j++) {

					try (PreparedStatement insertCategoryStatement = connection.prepareStatement(insertCategoryQuery, new String[] {"ID"})) {
						insertCategoryStatement.setString(1, "Category " + j);
						int rowsInserted = insertCategoryStatement.executeUpdate();

						if (rowsInserted > 0) {
							try (ResultSet generatedKeys = insertCategoryStatement.getGeneratedKeys()) {
								if (generatedKeys.next()) {
									categoryId = generatedKeys.getLong(1);
								} else {
									throw new SQLException("Creating Category failed, no ID obtained.");
								}
							}
						} else {
							throw new SQLException("Creating Category failed, no rows affected.");
						}
					}

					try (PreparedStatement insertFilmCategoryStatement = connection.prepareStatement(insertFilmCategoryQuery)) {
						insertFilmCategoryStatement.setLong(1, filmId);
						insertFilmCategoryStatement.setLong(2, categoryId);
						insertFilmCategoryStatement.executeUpdate();
					}
				}

				if (Math.random() > 0.75) {
					try (PreparedStatement insertFilmCategoryFilterStatement = connection.prepareStatement(insertFilmCategoryFilter)) {
						insertFilmCategoryFilterStatement.setLong(1, 100);
						insertFilmCategoryFilterStatement.setLong(2, categoryId);
						insertFilmCategoryFilterStatement.executeUpdate();
					}
				}
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void removeMockData() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException autoCommitException) {
				autoCommitException.printStackTrace();
			}
		}
	}
}
