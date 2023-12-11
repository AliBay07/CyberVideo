package dao.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Account;
import beans.Actor;
import beans.Author;
import beans.BlueRay;
import beans.Category;
import beans.Film;

public class BlueRayDao extends Dao<BlueRay> {

	public BlueRayDao(Connection connection) {
		super(connection);
	}
	
	public ArrayList<BlueRay> getAllAvailableBlueRays() {
		ArrayList<BlueRay> blueRays = new ArrayList<>();

		String query = "SELECT b.id AS blueRayId, f.id AS filmId, f.name, f.duration, f.description, b.available, f.image_path " +
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
				"FROM BlueRay b " +
				"INNER JOIN Film f ON b.id_film = f.id" +
				"WHERE b.available = 1;";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int blueRayId = resultSet.getInt("blueRayId");
				int blueRayAvailability = resultSet.getInt("available");

				int filmId = resultSet.getInt("filmId");
				String filmName = resultSet.getString("name");
				int filmDuration = resultSet.getInt("duration");
				String filmDescription = resultSet.getString("description");

				Film film = new Film();
				film.setId(filmId);
				film.setName(filmName);
				film.setDuration(filmDuration);
				film.setDescription(filmDescription);
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
				if (categoryNames != null) {
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
				}

				BlueRay blueRay = new BlueRay();
				blueRay.setId(blueRayId);
				blueRay.setFilm(film);
				blueRay.setAvailable(blueRayAvailability);

				blueRays.add(blueRay);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blueRays;
	}

	public boolean reportLostBlueRayDisc(Account account, BlueRay blueRay) {
	    if (account != null && blueRay != null) {
	        try {
	            String insertLostBlueRayQuery = "INSERT INTO LostBlueRay (id_account, id_blueray) VALUES (?, ?)";
	            try (PreparedStatement insertLostBlueRayStatement = connection.prepareStatement(insertLostBlueRayQuery)) {
	                insertLostBlueRayStatement.setLong(1, account.getId());
	                insertLostBlueRayStatement.setLong(2, blueRay.getId());
	                int updatedRows = insertLostBlueRayStatement.executeUpdate();

	                if (updatedRows > 0) {
	                	connection.commit();
	                    return true;
	                }
	                connection.rollback();
	            }
	        } catch (SQLException e) {
	        	try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
}

