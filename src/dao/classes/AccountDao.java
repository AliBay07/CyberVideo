package dao.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import beans.Account;
import beans.CreditCard;
import beans.NormalAccount;
import beans.SubscriberAccount;
import beans.SubscriberCard;
import beans.User;


public class AccountDao extends Dao {

	public AccountDao(Connection connection) {
		super(connection);
	}

	public boolean createUserAccount(User user, String email, String password) {
		
		String insertUserQuery = "INSERT INTO Users (first_name, last_name, dob) VALUES (?, ?, ?)";
		String insertAccountQuery = "INSERT INTO Account (id_users, email, password, is_subscriber, nb_allowed_reservations) " +
				"VALUES (?, ?, ?, 'N', ?)";

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

		try {
			connection.setAutoCommit(false);

			try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, new String[]{"id"})) {
				userStatement.setString(1, user.getFirstName());
				userStatement.setString(2, user.getLastName());
				userStatement.setDate(3, (Date) user.getDateOfBirth());

				int rowsAffected = userStatement.executeUpdate();
				if (rowsAffected > 0) {
					try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							long userId = generatedKeys.getLong(1);

							try (PreparedStatement accountStatement = connection.prepareStatement(insertAccountQuery)) {
								accountStatement.setLong(1, userId);
								accountStatement.setString(2, email);
								accountStatement.setString(3, hashedPassword);
								accountStatement.setInt(4, 0);

								rowsAffected = accountStatement.executeUpdate();
								if (rowsAffected > 0) {
									connection.commit();
									return true;
								}
							}
						}
					}
				}
			}

			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
		} finally {
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public Account userLogin(String email, String password) {
		
	    String query = "SELECT " +
	            "Account.id, Account.id_users, Account.email, Account.password, " +
	            "Account.is_subscriber, Account.nb_allowed_reservations, " +
	            "LISTAGG(CreditCard.card_number, ', ') WITHIN GROUP (ORDER BY CreditCard.id) AS credit_cards, " +
	            "LISTAGG(SubscriberCard.card_number || ':' || SubscriberCard.amount, ', ') " +
	            "WITHIN GROUP (ORDER BY SubscriberCard.id) AS subscriber_cards, " +
	            "Users.first_name, Users.last_name " +
	            "FROM Account " +
	            "LEFT JOIN AccountCreditCard ON Account.id = AccountCreditCard.id_account " +
	            "LEFT JOIN CreditCard ON AccountCreditCard.id_credit_card = CreditCard.id " +
	            "LEFT JOIN AccountSubscriberCard ON Account.id = AccountSubscriberCard.id_account " +
	            "LEFT JOIN SubscriberCard ON AccountSubscriberCard.id_subscriber_card = SubscriberCard.id " +
	            "LEFT JOIN Users ON Account.id_users = Users.id " +
	            "WHERE Account.email = ? " +
	            "GROUP BY Account.id, Account.id_users, Account.email, Account.password, " +
	            "Account.is_subscriber, Account.nb_allowed_reservations, Users.first_name, Users.last_name";

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, email);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                long id = resultSet.getLong("id");
	                long userId = resultSet.getLong("id_users");
	                String storedPassword = resultSet.getString("password");
	                String isSubscriber = resultSet.getString("is_subscriber");
	                int nbAllowedReservations = resultSet.getInt("nb_allowed_reservations");
	                String creditCards = resultSet.getString("credit_cards");
	                String subscriberCards = resultSet.getString("subscriber_cards");

	                String firstName = resultSet.getString("first_name");
	                String lastName = resultSet.getString("last_name");

	                Account account = null;

	                if ("Y".equals(isSubscriber)) {
	                    account = new SubscriberAccount();

	                    if (!(subscriberCards.replace(":", "").equals(""))) {
	                        List<SubscriberCard> subscriberCardList = Arrays.stream(subscriberCards.split(", "))
	                                .map(cardData -> {
	                                    String[] cardInfo = cardData.split(":");
	                                    SubscriberCard subscriberCard = new SubscriberCard();
	                                    subscriberCard.setCardNumber(cardInfo[0]);
	                                    subscriberCard.setAmount(Float.parseFloat(cardInfo[1]));
	                                    return subscriberCard;
	                                })
	                                .collect(Collectors.toList());
	                        ((SubscriberAccount) account).setSubscriberCards(subscriberCardList);
	                    }

	                } else {
	                    account = new NormalAccount();
	                }

	                account.setId(id);
	                account.setIdUser(userId);
	                account.setEmail(email);
	                account.setPassword(storedPassword);
	                account.setNbAllowedReservation(nbAllowedReservations);

	                User user = new User();
	                user.setFirstName(firstName);
	                user.setLastName(lastName);
	                account.setUser(user);

	                if (creditCards != null) {
	                    List<CreditCard> creditCardList = Arrays.stream(creditCards.split(", "))
	                            .map(cardNumber -> {
	                                CreditCard creditCard = new CreditCard();
	                                creditCard.setCardNumber(cardNumber);
	                                return creditCard;
	                            })
	                            .collect(Collectors.toList());
	                    account.setCreditCards(creditCardList);
	                }

	                return account;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	public Account userLoginWithCard(String cardNumber, String password) {
		
		String query = "SELECT " +
			    "Account.id, Account.id_users, Account.email, Account.password, " +
			    "Account.is_subscriber, Account.nb_allowed_reservations, " +
			    "(SELECT LISTAGG(CreditCard.card_number, ', ') WITHIN GROUP (ORDER BY CreditCard.id) " +
			    "FROM AccountCreditCard " +
			    "INNER JOIN CreditCard ON AccountCreditCard.id_credit_card = CreditCard.id " +
			    "WHERE AccountCreditCard.id_account = Account.id) AS credit_cards, " +
			    "(SELECT LISTAGG(SubscriberCard.card_number || ':' || SubscriberCard.amount, ', ') " +
			    "WITHIN GROUP (ORDER BY SubscriberCard.id) " +
			    "FROM AccountSubscriberCard " +
			    "INNER JOIN SubscriberCard ON AccountSubscriberCard.id_subscriber_card = SubscriberCard.id " +
			    "WHERE AccountSubscriberCard.id_account = Account.id) AS subscriber_cards, " +
			    "Users.first_name, Users.last_name " +
			    "FROM Account " +
			    "LEFT JOIN AccountCreditCard ON Account.id = AccountCreditCard.id_account " +
			    "LEFT JOIN CreditCard ON AccountCreditCard.id_credit_card = CreditCard.id " +
			    "LEFT JOIN AccountSubscriberCard ON Account.id = AccountSubscriberCard.id_account " +
			    "LEFT JOIN SubscriberCard ON AccountSubscriberCard.id_subscriber_card = SubscriberCard.id " +
			    "LEFT JOIN Users ON Account.id_users = Users.id " +
			    "WHERE Account.id IN " +
			    "(SELECT AccountSubscriberCard.id_account " +
			    "FROM AccountSubscriberCard " +
			    "INNER JOIN SubscriberCard ON AccountSubscriberCard.id_subscriber_card = SubscriberCard.id " +
			    "WHERE SubscriberCard.card_number = ?)";


	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, cardNumber);
	        
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                long id = resultSet.getLong("id");
	                long userId = resultSet.getLong("id_users");
	                String storedPassword = resultSet.getString("password");
	                String isSubscriber = resultSet.getString("is_subscriber");
	                int nbAllowedReservations = resultSet.getInt("nb_allowed_reservations");
	                String creditCards = resultSet.getString("credit_cards");
	                String subscriberCards = resultSet.getString("subscriber_cards");

	                String firstName = resultSet.getString("first_name");
	                String lastName = resultSet.getString("last_name");

	                Account account = null;

	                if ("Y".equals(isSubscriber)) {
	                    account = new SubscriberAccount();

	                    if (!(subscriberCards.replace(":", "").equals(""))) {
	                        List<SubscriberCard> subscriberCardList = Arrays.stream(subscriberCards.split(", "))
	                                .map(cardData -> {
	                                    String[] cardInfo = cardData.split(":");
	                                    SubscriberCard subscriberCard = new SubscriberCard();
	                                    subscriberCard.setCardNumber(cardInfo[0]);
	                                    subscriberCard.setAmount(Float.parseFloat(cardInfo[1]));
	                                    return subscriberCard;
	                                })
	                                .collect(Collectors.toList());
	                        ((SubscriberAccount) account).setSubscriberCards(subscriberCardList);
	                    }
	                } else {
	                    account = new NormalAccount();
	                }

	                account.setId(id);
	                account.setIdUser(userId);
	                account.setEmail(resultSet.getString("email"));
	                account.setPassword(storedPassword);
	                account.setNbAllowedReservation(nbAllowedReservations);
	                
	                User user = new User();
	                user.setFirstName(firstName);
	                user.setLastName(lastName);
	                account.setUser(user);

	                if (creditCards != null) {
	                    List<CreditCard> creditCardList = Arrays.stream(creditCards.split(", "))
	                            .map(cardNumberStr -> {
	                                CreditCard creditCard = new CreditCard();
	                                creditCard.setCardNumber(cardNumberStr);
	                                return creditCard;
	                            })
	                            .collect(Collectors.toList());
	                    account.setCreditCards(creditCardList);
	                }

	                return account;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	
	// For testing, adding mock data
	
	public void insertMockData() {
	    try {
	        connection.setAutoCommit(false);

	        for (int i = 1; i <= 2; i++) {
	            String insertCreditCardQuery = "INSERT INTO CreditCard (card_number) VALUES (?)";
	            try (PreparedStatement creditCardStatement = connection.prepareStatement(insertCreditCardQuery, new String[]{"ID"})) {
	                creditCardStatement.setString(1, "CredCard" + i);
	                int rowsInserted = creditCardStatement.executeUpdate();

	                if (rowsInserted > 0) {
	                    try (ResultSet generatedKeys = creditCardStatement.getGeneratedKeys()) {
	                        if (generatedKeys.next()) {
	                            long creditCardId = generatedKeys.getLong(1);

	                            String insertSubscriberCardQuery = "INSERT INTO SubscriberCard (card_number, amount) VALUES (?, ?)";
	                            try (PreparedStatement subscriberCardStatement = connection.prepareStatement(insertSubscriberCardQuery, new String[]{"ID"})) {
	                                subscriberCardStatement.setString(1, "SubCard" + i);
	                                subscriberCardStatement.setFloat(2, 100.0f);
	                                rowsInserted = subscriberCardStatement.executeUpdate();

	                                if (rowsInserted > 0) {
	                                    try (ResultSet subscriberKeys = subscriberCardStatement.getGeneratedKeys()) {
	                                        if (subscriberKeys.next()) {
	                                            long subscriberCardId = subscriberKeys.getLong(1);

	                                            String insertUserQuery = "INSERT INTO Users (first_name, last_name, dob) VALUES (?, ?, ?)";
	                                            try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, new String[]{"ID"})) {
	                                                userStatement.setString(1, "first_name_" + i);
	                                                userStatement.setString(2, "last_name_" + i);
	                                                LocalDate dateOfBirth = LocalDate.of(2002, 1, i);
	                                                Date dob = Date.valueOf(dateOfBirth);
	                                                userStatement.setDate(3, dob);
	                                                rowsInserted = userStatement.executeUpdate();

	                                                if (rowsInserted > 0) {
	                                                    try (ResultSet userKeys = userStatement.getGeneratedKeys()) {
	                                                        if (userKeys.next()) {
	                                                            long userId = userKeys.getLong(1);
	                                                            
	                                                            if (i == 1) {
	                                                            	insertSubscriberAccount(connection, userId, creditCardId, subscriberCardId);
	                                                            } else {
	                                                            	insertNormalAccount(connection, userId, creditCardId);
	                                                            }
	                                                        }
	                                                    }
	                                                }
	                                            }
	                                        }
	                                    }
	                                }
	                            }
	                        }
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


	private void insertSubscriberAccount(Connection connection, long userId, long creditCardId, long subscriberCardId) throws SQLException {
	    String insertAccountQuery = "INSERT INTO Account (id_users, email, password, is_subscriber, nb_allowed_reservations) " +
	            "VALUES (?, ?, ?, ?, ?)";

	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String hashedPassword = passwordEncoder.encode("123");

	    try (PreparedStatement accountStatement = connection.prepareStatement(insertAccountQuery, new String[] { "ID" })) {
	        accountStatement.setLong(1, userId);
	        accountStatement.setString(2, "test@example.com");
	        accountStatement.setString(3, hashedPassword);
	        accountStatement.setString(4, "Y");
	        accountStatement.setInt(5, 5);

	        accountStatement.executeUpdate();

	        long accountId;
	        try (ResultSet generatedKeys = accountStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                accountId = generatedKeys.getLong(1);
	            } else {
	                throw new SQLException("Creating account failed, no ID obtained.");
	            }
	        }

	        associateAccountWithCreditCard(connection, accountId, creditCardId);
	        associateAccountWithSubscriberCard(connection, accountId, subscriberCardId);
	    }
	}

	private void insertNormalAccount(Connection connection, long userId, long creditCardId) throws SQLException {
	    String insertAccountQuery = "INSERT INTO Account (id_users, email, password, is_subscriber, nb_allowed_reservations) " +
	            "VALUES (?, ?, ?, ?, ?)";

	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String hashedPassword = passwordEncoder.encode("123");

	    try (PreparedStatement accountStatement = connection.prepareStatement(insertAccountQuery, new String[] { "ID" })) {
	        accountStatement.setLong(1, userId);
	        accountStatement.setString(2, "test2@example.com");
	        accountStatement.setString(3, hashedPassword);
	        accountStatement.setString(4, "N");
	        accountStatement.setInt(5, 5);

	        accountStatement.executeUpdate();

	        long accountId;
	        try (ResultSet generatedKeys = accountStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                accountId = generatedKeys.getLong(1);
	            } else {
	                throw new SQLException("Creating account failed, no ID obtained.");
	            }
	        }

	        associateAccountWithCreditCard(connection, accountId, creditCardId);
	    }
	}

	private void associateAccountWithCreditCard(Connection connection, long accountId, long creditCardId) throws SQLException {
	    String associateQuery = "INSERT INTO AccountCreditCard (id_account, id_credit_card) VALUES (?, ?)";
	    try (PreparedStatement statement = connection.prepareStatement(associateQuery)) {
	        statement.setLong(1, accountId);
	        statement.setLong(2, creditCardId);
	        statement.executeUpdate();
	    }
	}

	private void associateAccountWithSubscriberCard(Connection connection, long accountId, long subscriberCardId) throws SQLException {
	    String associateQuery = "INSERT INTO AccountSubscriberCard (id_account, id_subscriber_card) VALUES (?, ?)";
	    try (PreparedStatement statement = connection.prepareStatement(associateQuery)) {
	        statement.setLong(1, accountId);
	        statement.setLong(2, subscriberCardId);
	        statement.executeUpdate();
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
