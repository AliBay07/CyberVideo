package dao.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import beans.Account;
import beans.Actor;
import beans.Author;
import beans.BlueRay;
import beans.Category;
import beans.CurrentReservation;
import beans.Film;
import beans.HistoricReservation;
import beans.Reservation;

public class ReservationDao extends Dao<Reservation>{

	public ReservationDao(Connection connection) {
		super(connection);
	}

	public ArrayList<Reservation> getCurrentReservationsByAccount(Account account) {

		ArrayList<Reservation> reservations = new ArrayList<>();

		if (account != null) {
			String query = "SELECT b.available, r.id AS reservation_id, r.id_blueray, r.reservation_start_date, " +
				    "f.id AS film_id, f.name, f.duration, f.description, " +
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
				    "FROM CurrentReservations r " +
				    "INNER JOIN BlueRay b ON r.id_blueray = b.id " +
				    "INNER JOIN Film f ON b.id_film = f.id " +
				    "WHERE r.id_account = ?";

			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setLong(1, account.getId());
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) {
					CurrentReservation reservation = new CurrentReservation();
					reservation.setAccount(account);

					Film film = new Film();
					film.setId(resultSet.getLong("film_id"));
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

					BlueRay blueray = new BlueRay();
					blueray.setId(resultSet.getLong("id_blueray"));
					blueray.setFilm(film);
					blueray.setAvailable(resultSet.getLong("available"));

					reservation.setBlueray(blueray);
					reservation.setStartReservationDate(resultSet.getDate("reservation_start_date"));

					reservations.add(reservation);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return reservations;
	}

	public boolean ReserveBlueRay(Account account, BlueRay blueRay) {

		if (account != null && blueRay != null) {
			try {

				String selectAccountQuery = "SELECT is_subscriber FROM Account WHERE id = ?";
				try (PreparedStatement selectAccountStatement = connection.prepareStatement(selectAccountQuery)) {
					selectAccountStatement.setLong(1, account.getId());
					ResultSet accountResult = selectAccountStatement.executeQuery();

					if (accountResult.next()) {
						String isSubscriber = accountResult.getString("is_subscriber");

						if (isSubscriber.equals("N")) {
							String checkReservationQuery = "SELECT COUNT(*) AS numReservations " +
									"FROM CurrentReservations " +
									"WHERE id_account = ?";
							try (PreparedStatement checkReservationStatement = connection.prepareStatement(checkReservationQuery)) {
								checkReservationStatement.setLong(1, account.getId());
								ResultSet numReservationsResult = checkReservationStatement.executeQuery();

								if (numReservationsResult.next()) {
									int numReservations = numReservationsResult.getInt("numReservations");

									if (numReservations > 0) {
										connection.rollback();
										return false;
									}
								}
							}
						} else if (isSubscriber.equals("Y")) {
							String checkReservationQuery = "SELECT COUNT(*) AS numReservations " +
									"FROM CurrentReservations " +
									"WHERE id_account = ?";
							try (PreparedStatement checkReservationStatement = connection.prepareStatement(checkReservationQuery)) {
								checkReservationStatement.setLong(1, account.getId());
								ResultSet numReservationsResult = checkReservationStatement.executeQuery();

								if (numReservationsResult.next()) {
									int numReservations = numReservationsResult.getInt("numReservations");

									if (numReservations >= 3) {
										connection.rollback();
										return false;
									}
								}
							}
						}
					}
				}

				String insertReservationQuery = "INSERT INTO CurrentReservations (id_account, id_blueray, reservation_start_date) " +
						"VALUES (?, ?, CURRENT_DATE)";

				try (PreparedStatement insertReservationStatement = connection.prepareStatement(insertReservationQuery)) {
					insertReservationStatement.setLong(1, account.getId());
					insertReservationStatement.setLong(2, blueRay.getId());

					int updatedRows = insertReservationStatement.executeUpdate();

					if (updatedRows <= 0) {
						connection.rollback();
						return false;
					}
				}

				String updateQuery = "UPDATE BlueRay SET available = 0 WHERE id = ?";

				try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
					updateStatement.setLong(1, blueRay.getId());

					int updatedRows = updateStatement.executeUpdate();

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

	public boolean ReserveQrCode(Account account, Film film) {
		if (account != null && film != null) {
			try {

				String baseLink = "https://example.com/qr-codes/";
				String randomToken = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
				String randomLink = baseLink + randomToken;

				String insertQrCodeQuery = "INSERT INTO QRCodeHistory (id_account, id_film, reservation_start_date, expiration_date, link) " +
						"VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + INTERVAL '12' HOUR, ?)";

				try (PreparedStatement insertQrCodeStatement = connection.prepareStatement(insertQrCodeQuery)) {
					insertQrCodeStatement.setLong(1, account.getId());
					insertQrCodeStatement.setLong(2, film.getId());
					insertQrCodeStatement.setString(3, randomLink);

					int updatedRows = insertQrCodeStatement.executeUpdate();

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

	public boolean addReservationToHistoric(BlueRay blueRay) {

		try {
			String selectQuery = "SELECT id_account, id_blueray, reservation_start_date FROM CurrentReservations WHERE id_blueray = ?";
			try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
				selectStatement.setLong(1, blueRay.getId());
				try (ResultSet resultSet = selectStatement.executeQuery()) {
					if (resultSet.next()) {
						long accountId = resultSet.getLong("id_account");
						long blueRayId = resultSet.getLong("id_blueray");
						Date reservationStartDate = resultSet.getDate("reservation_start_date");

						String insertQuery = "INSERT INTO ReservationHistory (id_account, id_blueray, reservation_start_date, reservation_end_date) " +
								"VALUES (?, ?, ?, CURRENT_DATE)";
						try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
							insertStatement.setLong(1, accountId);
							insertStatement.setLong(2, blueRayId);
							insertStatement.setDate(3, reservationStartDate);
							int rowsInserted = insertStatement.executeUpdate();

							if (rowsInserted > 0) {
								connection.commit();
								return true;
							}
							connection.rollback();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean removeCurrentReservation(BlueRay blueRay) {
		try {
			String deleteQuery = "DELETE FROM CurrentReservations WHERE id_blueray = ?";
			try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
				deleteStatement.setLong(1, blueRay.getId());
				int rowsDeleted = deleteStatement.executeUpdate();

				if (rowsDeleted <= 0) {
					return false;
				}
			}

			String updateQuery = "UPDATE BlueRay SET available = 1 WHERE id = ?";

			try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
				updateStatement.setLong(1, blueRay.getId());

				int updatedRows = updateStatement.executeUpdate();

				if (updatedRows > 0) {
					connection.commit();
					return true;
				}
				connection.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<HistoricReservation> getHistoricReservations(Account account) {
		List<HistoricReservation> historicReservations = new ArrayList<>();

		if (account != null) {
			String sql = "SELECT rh.*, b.*, f.*, " +
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
				    "FROM ReservationHistory rh " +
				    "JOIN BlueRay b ON rh.id_blueray = b.id " +
				    "JOIN Film f ON b.id_film = f.id " +
				    "WHERE rh.id_account = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setLong(1, account.getId());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						HistoricReservation historicReservation = new HistoricReservation();
						historicReservation.setAccount(account);

						BlueRay blueRay = new BlueRay();
						blueRay.setId(resultSet.getLong("id_blueray"));
						blueRay.setAvailable(resultSet.getLong("available"));

						Film film = new Film();
						film.setId(resultSet.getLong("id_film"));
						film.setName(resultSet.getString("name"));
						film.setDescription(resultSet.getString("description"));
						film.setDuration(resultSet.getInt("duration"));
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

						blueRay.setFilm(film);

						historicReservation.setBlueray(blueRay);
						historicReservation.setStartReservationDate(resultSet.getDate("reservation_start_date"));
						historicReservation.setEndReservationDate(resultSet.getDate("reservation_end_date"));

						historicReservations.add(historicReservation);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return historicReservations;
	}

}
