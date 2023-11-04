package dao.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import beans.Account;
import beans.BlueRay;
import beans.Film;
import beans.Reservation;

public class ReservationDao extends Dao<Reservation>{

	public ReservationDao(Connection connection) {
		super(connection);
	}

    public ArrayList<Reservation> getCurrentReservationsByAccount(Account account) {
    	
        ArrayList<Reservation> reservations = new ArrayList<>();

        if (account != null) {
            String query = "SELECT r.id AS reservation_id, r.id_blueray, r.reservation_start_date, " +
                           "f.id AS film_id, f.name, f.duration, f.description " +
                           "FROM CurrentReservations r " +
                           "INNER JOIN BlueRay b ON r.id_blueray = b.id " +
                           "INNER JOIN Film f ON b.id_film = f.id " +
                           "WHERE r.id_account = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, account.getId());
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setAccount(account);

                    Film film = new Film();
                    film.setId(resultSet.getLong("film_id"));
                    film.setName(resultSet.getString("name"));
                    film.setDuration(resultSet.getInt("duration"));
                    film.setDescription(resultSet.getString("description"));

                    reservation.setFilm(film);
                    reservation.setStartReservationDate(resultSet.getDate("reservation_start_date"));
                    reservation.setEndReservationDate(null);

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

                String insertQrCodeQuery = "INSERT INTO QRCode (id_account, id_film, reservation_start_date, link) " +
                        "VALUES (?, ?, CURRENT_DATE, ?)";

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

}
