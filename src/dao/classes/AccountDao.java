package dao.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Account;
import beans.NormalAccount;
import beans.SubscriberAccount;
import beans.User;

public class AccountDao extends Dao {

	public AccountDao(Connection connection) {
		super(connection);
	}

	public Account userLoginWithCard(String cardNumber, String password) {
		String query = "SELECT a.id, a.id_users, a.email, a.password, a.is_subscriber, a.nb_allowed_reservations " +
				"FROM Account a " +
				"INNER JOIN AccountCreditCard acc ON a.id = acc.id_account " +
				"INNER JOIN CreditCard c ON acc.id_credit_card = c.id " +
				"WHERE c.card_number = ? AND a.password = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, cardNumber);
			statement.setString(2, password);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String isSubscriber = resultSet.getString("is_subscriber");
					long idUsers = resultSet.getLong("id_users");

					Account account = null;

					if ("Y".equals(isSubscriber)) {
						account = new SubscriberAccount();
					} else {
						account = new NormalAccount();
					}

					account.setId(resultSet.getLong("id"));
					account.setIdUser(idUsers);
					account.setEmail(resultSet.getString("email"));
					account.setPassword(resultSet.getString("password"));
					account.setNbAllowedReservation(resultSet.getInt("nb_allowed_reservations"));

					User user = getUserById(idUsers);

					if (user != null) {
						account.setUser(user);
					}

					return account;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}


	private User getUserById(long id) {
		String query = "SELECT id, first_name, last_name, dob FROM Users WHERE id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					User user = new User();
					user.setId(resultSet.getLong("id"));
					user.setFirstName(resultSet.getString("first_name"));
					user.setLastName(resultSet.getString("last_name"));
					user.setDateOfBirth(resultSet.getDate("dob"));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
