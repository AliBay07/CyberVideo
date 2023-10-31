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

	public List<Film> getAllFilms() {
	    List<Film> films = new ArrayList<>();
	    String query = "SELECT f.id, f.name, f.duration, f.description, " +
	            "(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
	            " FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
	            " WHERE fa.id_film = f.id) AS actors, " +
	            "(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
	            " FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
	            " WHERE fau.id_film = f.id) AS authors, " +
	            "(SELECT LISTAGG(c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
	            " FROM FilmCategory fc " +
	            " INNER JOIN Category c ON fc.id_category = c.id " +
	            " WHERE fc.id_film = f.id) AS categories " +
	            "FROM Film f";

	    try (PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {
	        while (resultSet.next()) {
	            Film film = new Film();
	            film.setId(resultSet.getLong("id"));
	            film.setName(resultSet.getString("name"));
	            film.setDuration(resultSet.getInt("duration"));
	            film.setDescription(resultSet.getString("description"));

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
	            if (categoryNames != null) {
	                String[] categoryNameArray = categoryNames.split(",");
	                List<Category> categories = new ArrayList<>();
	                for (String categoryName : categoryNameArray) {
	                    Category category = new Category();
	                    category.setCategoryName(categoryName.trim());
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

	public List<List<Object>> getTopFilmsMonth() {
	    List<List<Object>> topFilms = new ArrayList<>();
	    String query = "SELECT f.id, f.name, f.duration, f.description, " +
	            "tm.number_reservations, " +
	            "(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
	            " FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
	            " WHERE fa.id_film = f.id) AS actors, " +
	            "(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
	            " FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
	            " WHERE fau.id_film = f.id) AS authors, " +
	            "(SELECT LISTAGG(c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
	            " FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
	            " WHERE fc.id_film = f.id) AS categories " +
	            "FROM Film f " +
	            "INNER JOIN TopFilmsMonth tm ON f.id = tm.id_film " +
	            "ORDER BY tm.number_reservations DESC";

	    try (PreparedStatement statement = connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery()) {
	        while (resultSet.next()) {
	            List<Object> filmData = new ArrayList<>();

	            Film film = new Film();
	            film.setId(resultSet.getLong("id"));
	            film.setName(resultSet.getString("name"));
	            film.setDuration(resultSet.getInt("duration"));
	            film.setDescription(resultSet.getString("description"));

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
	            if (categoryNames != null) {
	                String[] categoryArray = categoryNames.split(",");
	                List<Category> categories = new ArrayList<>();
	                for (String categoryName : categoryArray) {
	                    Category category = new Category();
	                    category.setCategoryName(categoryName);
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

	public Film getFilmInformation(String filmName) {
	    String query = "SELECT f.id, f.name, f.duration, f.description, " +
	            "(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
	            " FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
	            " WHERE fa.id_film = f.id) AS actors, " +
	            "(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
	            " FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
	            " WHERE fau.id_film = f.id) AS authors, " +
	            "(SELECT LISTAGG(c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
	            " FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
	            " WHERE fc.id_film = f.id) AS categories " +
	            "FROM Film f " +
	            "WHERE f.name = ?";

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, filmName);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                Film film = new Film();
	                film.setId(resultSet.getLong("id"));
	                film.setName(resultSet.getString("name"));
	                film.setDuration(resultSet.getInt("duration"));
	                film.setDescription(resultSet.getString("description"));

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
	                if (categoryNames != null) {
	                    String[] categoryArray = categoryNames.split(", ");
	                    List<Category> categories = new ArrayList<>();
	                    for (String categoryName : categoryArray) {
	                        Category category = new Category();
	                        category.setCategoryName(categoryName);
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


	public List<Film> searchFilmByCriteria(Map<String, String> filters) {
	    List<Film> films = new ArrayList<>();

	    StringBuilder queryBuilder = new StringBuilder("SELECT f.id, f.name, f.duration, f.description, " +
	            "(SELECT LISTAGG(DISTINCT a.first_name || ' ' || a.last_name, ', ') WITHIN GROUP (ORDER BY a.id) " +
	            " FROM FilmActor fa INNER JOIN Actor a ON fa.id_actor = a.id " +
	            " WHERE fa.id_film = f.id) AS actors, " +
	            "(SELECT LISTAGG(DISTINCT au.first_name || ' ' || au.last_name, ', ') WITHIN GROUP (ORDER BY au.id) " +
	            " FROM FilmAuthor fau INNER JOIN Author au ON fau.id_author = au.id " +
	            " WHERE fau.id_film = f.id) AS authors, " +
	            "(SELECT LISTAGG(c.category_name, ', ') WITHIN GROUP (ORDER BY c.id) " +
	            " FROM FilmCategory fc INNER JOIN Category c ON fc.id_category = c.id " +
	            " WHERE fc.id_film = f.id) AS categories " +
	            "FROM Film f " +
	            "WHERE 1=1");

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
	                if (categoryNames != null) {
	                    String[] categoryArray = categoryNames.split(", ");
	                    List<Category> categories = new ArrayList<>();
	                    for (String categoryName : categoryArray) {
	                        Category category = new Category();
	                        category.setCategoryName(categoryName);
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

			String insertFilmQuery = "INSERT INTO Film (id, name, duration, description) VALUES (?, ?, ?, ?)";
			String insertFilmCategoryQuery = "INSERT INTO FilmCategory (id_film, id_category) VALUES (?, ?)";
			String insertActorQuery = "INSERT INTO Actor (id, first_name, last_name) VALUES (?, ?, ?)";
			String insertFilmActorQuery = "INSERT INTO FilmActor (id_film, id_actor) VALUES (?, ?)";
			String insertAuthorQuery = "INSERT INTO Author (id, first_name, last_name) VALUES (?, ?, ?)";
			String insertFilmAuthorQuery = "INSERT INTO FilmAuthor (id_film, id_author) VALUES (?, ?)";
			String insertTopMoviesQuery = "INSERT INTO TopFilmsMonth (id, id_film, number_reservations) VALUES (?, ?, ?)";
			String insertCategoryQuery = "INSERT INTO Category (id, category_name) VALUES (?, ?)";

			for (int i = 1; i <= 10; i++) {
				try (PreparedStatement insertFilmStatement = connection.prepareStatement(insertFilmQuery)) {
					insertFilmStatement.setLong(1, i);
					insertFilmStatement.setString(2, "Film " + i);
					insertFilmStatement.setInt(3, 120);
					insertFilmStatement.setString(4, "Description for Film " + i);
					insertFilmStatement.executeUpdate();

					
					for (int j = 1; j <= 3; j++) {
						try (PreparedStatement insertActorStatement = connection.prepareStatement(insertActorQuery)) {
							insertActorStatement.setLong(1, i * 10 + j);
							insertActorStatement.setString(2, "Actor" + j);
							insertActorStatement.setString(3, "Lastname" + j);
							insertActorStatement.executeUpdate();
						}
						
						try (PreparedStatement insertFilmActorStatement = connection.prepareStatement(insertFilmActorQuery)) {
							insertFilmActorStatement.setLong(1, i);
							insertFilmActorStatement.setLong(2, i * 10 + j);
							insertFilmActorStatement.executeUpdate();
						}
					}
					
					for (int j = 1; j <= 2; j++) {
						try (PreparedStatement insertAuthorStatement = connection.prepareStatement(insertAuthorQuery)) {
							insertAuthorStatement.setLong(1, i * 100 + j);
							insertAuthorStatement.setString(2, "Author" + j);
							insertAuthorStatement.setString(3, "Lastname" + j);
							insertAuthorStatement.executeUpdate();
						}

						
						try (PreparedStatement insertFilmAuthorStatement = connection.prepareStatement(insertFilmAuthorQuery)) {
							insertFilmAuthorStatement.setLong(1, i);
							insertFilmAuthorStatement.setLong(2, i * 100 + j);
							insertFilmAuthorStatement.executeUpdate();
						}
					}

					
					try (PreparedStatement insertTopMoviesStatement = connection.prepareStatement(insertTopMoviesQuery)) {
						insertTopMoviesStatement.setLong(1, i * 10);
						insertTopMoviesStatement.setLong(2, i);
						insertTopMoviesStatement.setInt(3, (int) (Math.random() * 100));
						insertTopMoviesStatement.executeUpdate();
					}
					
		            for (int j = 1; j <= 3; j++) {
		                long categoryId = i * 1000 + j;

		                try (PreparedStatement insertCategoryStatement = connection.prepareStatement(insertCategoryQuery)) {
		                    insertCategoryStatement.setLong(1, categoryId);
		                    insertCategoryStatement.setString(2, "Category " + j);
		                    insertCategoryStatement.executeUpdate();
		                }
		                
		                try (PreparedStatement insertFilmCategoryStatement = connection.prepareStatement(insertFilmCategoryQuery)) {
		                    insertFilmCategoryStatement.setLong(1, i);
		                    insertFilmCategoryStatement.setLong(2, categoryId);
		                    insertFilmCategoryStatement.executeUpdate();
		                }
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
