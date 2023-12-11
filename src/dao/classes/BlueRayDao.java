package dao.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Account;
import beans.BlueRay;
import beans.Film;

public class BlueRayDao extends Dao<BlueRay> {

	public BlueRayDao(Connection connection) {
		super(connection);
	}
	
	public List<BlueRay> getAllAvailableBlueRays() {
		List<BlueRay> blueRays = new ArrayList<>();

		String query = "SELECT b.id AS blueRayId, f.id AS filmId, f.name, f.duration, f.description, b.available, f.image_path " +
				"FROM BlueRay b " +
				"INNER JOIN Film f ON b.id_film = f.id";

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

