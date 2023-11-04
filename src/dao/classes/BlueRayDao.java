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

		String query = "SELECT b.id AS blueRayId, b.available_quantity, f.id AS filmId, f.name, f.duration, f.description " +
				"FROM BlueRay b " +
				"INNER JOIN Film f ON b.id_film = f.id " +
				"WHERE b.available_quantity > 0";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int blueRayId = resultSet.getInt("blueRayId");
				int availableQuantity = resultSet.getInt("available_quantity");

				int filmId = resultSet.getInt("filmId");
				String filmName = resultSet.getString("name");
				int filmDuration = resultSet.getInt("duration");
				String filmDescription = resultSet.getString("description");

				Film film = new Film();
				film.setId(filmId);
				film.setName(filmName);
				film.setDuration(filmDuration);
				film.setDescription(filmDescription);

				BlueRay blueRay = new BlueRay();
				blueRay.setId(blueRayId);
				blueRay.setAvailableQuantity(availableQuantity);
				blueRay.setFilm(film);

				blueRays.add(blueRay);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blueRays;
	}

	public boolean incrementBlueRayQuantity(BlueRay blueRay) {
		String query = "UPDATE BlueRay SET available_quantity = available_quantity + 1 WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, blueRay.getId());
			int updatedRows = statement.executeUpdate();
			if (updatedRows > 0) {
				connection.commit();
				blueRay.setAvailableQuantity(blueRay.getAvailableQuantity() + 1);
				return true;
			}
			connection.rollback();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean decrementBlueRayQuantity(BlueRay blueRay) {
		String query = "UPDATE BlueRay SET available_quantity = available_quantity - 1 WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, blueRay.getId());
			int updatedRows = statement.executeUpdate();
			if (updatedRows > 0) {
				connection.commit();
				blueRay.setAvailableQuantity(blueRay.getAvailableQuantity() - 1);
				return true;
			}
			connection.rollback();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return false;
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

