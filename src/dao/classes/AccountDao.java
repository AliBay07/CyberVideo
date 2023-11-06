package dao.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import beans.Account;
import beans.Category;
import beans.CreditCard;
import beans.NormalAccount;
import beans.SubscriberAccount;
import beans.SubscriberCard;
import beans.User;


public class AccountDao extends Dao<Account> {

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

	public Account subscribeToService(Account account) {
		if (account != null) {

			String query = "SELECT * FROM Account WHERE Account.email = ?";

			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, account.getEmail());
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("Y")) {
							return account;
						} else {
							SubscriberAccount subscriberAccount = new SubscriberAccount();
							subscriberAccount.setId(account.getId());
							subscriberAccount.setIdUser(account.getIdUser());
							subscriberAccount.setEmail(account.getEmail());
							subscriberAccount.setPassword(account.getPassword());
							subscriberAccount.setNbAllowedReservation(account.getNbAllowedReservation());

							String updateQuery = "UPDATE Account SET is_subscriber = 'Y' WHERE email = ?";
							try (PreparedStatement statementupdate = connection.prepareStatement(updateQuery)) {
								statementupdate.setString(1, account.getEmail());

								int rowsUpdated = statementupdate.executeUpdate();

								if (rowsUpdated > 0) {
									connection.commit();
									return subscriberAccount;
								}
							}
						}
					}

				} 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account unsubscribeFromService(Account account) {

		if (account != null) {

			String query = "SELECT * FROM Account WHERE Account.email = ?";

			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, account.getEmail());
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							return account;
						} else {
							NormalAccount normalAccount = new NormalAccount();
							normalAccount.setId(account.getId());
							normalAccount.setIdUser(account.getIdUser());
							normalAccount.setEmail(account.getEmail());
							normalAccount.setPassword(account.getPassword());
							normalAccount.setNbAllowedReservation(account.getNbAllowedReservation());

							String updateQuery = "UPDATE Account SET is_subscriber = 'N' WHERE email = ?";
							try (PreparedStatement statementupdate = connection.prepareStatement(updateQuery)) {
								statementupdate.setString(1, account.getEmail());

								int rowsUpdated = statementupdate.executeUpdate();

								if (rowsUpdated > 0) {
									connection.commit();
									return normalAccount;
								}
							} 
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public boolean addMoneyToCard(SubscriberCard subscriberCard, double amount) {

		if (subscriberCard != null && amount > 0) {
			String query = "SELECT * FROM SubscriberCard WHERE SubscriberCard.card_number = ?";

			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, subscriberCard.getCardNumber());

				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						double amountInCard = resultSet.getFloat("amount");

						String updateQuery = "UPDATE SubscriberCard SET amount = ? WHERE card_number = ?";
						try (PreparedStatement statementupdate = connection.prepareStatement(updateQuery)) {
							statementupdate.setFloat(1, (float)(amountInCard + amount));
							statementupdate.setString(2, subscriberCard.getCardNumber());

							int rowsUpdated = statementupdate.executeUpdate();

							if (rowsUpdated > 0) {
								connection.commit();
								subscriberCard.setAmount((float) (amountInCard + amount));
								return true;
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean processPaymentBySubscriberCard(SubscriberCard subscriberCard, double amount) {

		if (subscriberCard != null) {
			String query = "SELECT * FROM SubscriberCard WHERE SubscriberCard.card_number = ?";

			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, subscriberCard.getCardNumber());

				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						double amountInCard = resultSet.getFloat("amount");
						if ((amountInCard - amount) < 0) {
							return false;
						} else {
							String updateQuery = "UPDATE SubscriberCard SET amount = ? WHERE card_number = ?";
							try (PreparedStatement statementupdate = connection.prepareStatement(updateQuery)) {
								statementupdate.setFloat(1, (float)(amountInCard - amount));
								statementupdate.setString(2, subscriberCard.getCardNumber());

								int rowsUpdated = statementupdate.executeUpdate();

								if (rowsUpdated > 0) {
									connection.commit();
									subscriberCard.setAmount((float)(amountInCard - amount));
									return true;
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean requestUnavailableFilm(Account account, String filmName) {
		if (account != null && filmName != null && !filmName.trim().isEmpty()) {

			String query = "SELECT * FROM Account WHERE Account.email = ?";

			try (PreparedStatement statementAccount = connection.prepareStatement(query)) {
				statementAccount.setString(1, account.getEmail());
				try (ResultSet resultSet = statementAccount.executeQuery()) {
					if (resultSet.next()) {
						if (resultSet.getString("is_subscriber").equals("N")) {
							return false;
						} else {
							String insertQuery = "INSERT INTO FilmRequestsFromUser (id_account, film_name, request_date) VALUES (?, ?, ?)";

							try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
								statement.setLong(1, account.getId());
								statement.setString(2, filmName);

								java.util.Date currentDate = new java.util.Date();
								java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
								statement.setDate(3, sqlDate);

								int rowsInserted = statement.executeUpdate();
								if (rowsInserted > 0) {
									connection.commit();
									return true;
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean banFilmCategories(Account account, List<Category> categories) {
		if (account != null && !categories.isEmpty()) {
			try {
				String query = "SELECT * FROM Account WHERE Account.email = ?";
				try (PreparedStatement statementAccount = connection.prepareStatement(query)) {
					statementAccount.setString(1, account.getEmail());
					try (ResultSet resultSet = statementAccount.executeQuery()) {
						if (resultSet.next()) {
							if (resultSet.getString("is_subscriber").equals("N")) {
								return false;
							} else {
								String insertQuery = "INSERT INTO AccountFilterCategory (id_account, id_category) VALUES (?, ?)";
								try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
									for (Category category : categories) {
										System.out.println(account.getId());
										System.out.println(category.getId());
										System.out.println();
										statement.setLong(1, account.getId());
										statement.setLong(2, category.getId());
										statement.addBatch();
									}
									int[] rowsInserted = statement.executeBatch();

									for (int rows : rowsInserted) {
										if (rows < 0) {
											return false;
										}
									}

									connection.commit();
									return true;
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean unbanFilmCategories(Account account, Category category) {
		if (account != null && category != null) {
			try {
				String query = "SELECT * FROM Account WHERE Account.email = ?";
				try (PreparedStatement statementAccount = connection.prepareStatement(query)) {
					statementAccount.setString(1, account.getEmail());
					try (ResultSet resultSet = statementAccount.executeQuery()) {
						if (resultSet.next()) {
							if (resultSet.getString("is_subscriber").equals("N")) {
								return false;
							} else {
								String deleteQuery = "DELETE FROM AccountFilterCategory WHERE id_account = ? AND id_category = ?";
								try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
									deleteStatement.setLong(1, account.getId());
									deleteStatement.setLong(2, category.getId());
									int rowsDeleted = deleteStatement.executeUpdate();

									if (rowsDeleted > 0) {
										connection.commit();
										return true;
									} else {
										connection.rollback();
									}
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}

		return false;
	}

	public List<Category> getBannedCategories(Account account) {
		List<Category> bannedCategories = new ArrayList<>();

		if (account != null) {
			try {
				String query = "SELECT * FROM Account WHERE Account.email = ?";
				try (PreparedStatement statementAccount = connection.prepareStatement(query)) {
					statementAccount.setString(1, account.getEmail());
					try (ResultSet resultSet = statementAccount.executeQuery()) {
						if (resultSet.next()) {
							if (resultSet.getString("is_subscriber").equals("N")) {
								return bannedCategories;
							} else {
								String selectQuery = "SELECT Category.* FROM Category " +
										"INNER JOIN AccountFilterCategory ON Category.id = AccountFilterCategory.id_category " +
										"WHERE AccountFilterCategory.id_account = ?";
								try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
									selectStatement.setLong(1, account.getId());
									try (ResultSet categoryResultSet = selectStatement.executeQuery()) {
										while (categoryResultSet.next()) {
											Category category = new Category();
											category.setId(categoryResultSet.getLong("id"));
											category.setCategoryName(categoryResultSet.getString("category_name"));
											bannedCategories.add(category);
										}
										return bannedCategories;
									}
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}

		return bannedCategories;
	}

	public boolean setWeeklyRentalLimit(Account account, int weeklyLimit) {
		if (account != null) {
			try {
				String query = "SELECT * FROM Account WHERE Account.email = ?";
				try (PreparedStatement statementAccount = connection.prepareStatement(query)) {
					statementAccount.setString(1, account.getEmail());
					try (ResultSet resultSet = statementAccount.executeQuery()) {
						if (resultSet.next()) {
							if (resultSet.getString("is_subscriber").equals("N")) {
								return false;
							} else {
								String updateQuery = "UPDATE Account SET nb_allowed_reservations = ? WHERE id = ?";
								try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
									updateStatement.setInt(1, weeklyLimit);
									updateStatement.setLong(2, account.getId());
									int rowsUpdated = updateStatement.executeUpdate();

									if (rowsUpdated > 0) {
										connection.commit();
										account.setNbAllowedReservation(weeklyLimit);
										return true;
									} else {
										connection.rollback();
									}
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}
		return false;
	}

	public Account modifyAccountInformation(Account account, String newFirstName, String newLastName, Date newDob, String oldPassword, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (account != null) {
			try {
				boolean isChangingPassword = newPassword != null && !newPassword.trim().isEmpty();

				if (isChangingPassword) {
					String verifyQuery = "SELECT password FROM Account WHERE id = ?";
					try (PreparedStatement verifyStatement = connection.prepareStatement(verifyQuery)) {
						verifyStatement.setLong(1, account.getId());
						try (ResultSet resultSet = verifyStatement.executeQuery()) {
							if (resultSet.next()) {
								String storedPassword = resultSet.getString("password");
								if (!passwordEncoder.matches(oldPassword, storedPassword)) {
									return null;
								}
							}
						}
					}
				}

				StringBuilder updateQuery = new StringBuilder("UPDATE Users SET ");
				List<Object> parameters = new ArrayList<>();

				if (newFirstName != null && !newFirstName.trim().isEmpty()) {
					updateQuery.append("first_name = ?, ");
					parameters.add(newFirstName.trim());
				}

				if (newLastName != null && !newLastName.trim().isEmpty()) {
					updateQuery.append("last_name = ?, ");
					parameters.add(newLastName.trim());
				}

				if (newDob != null) {
					updateQuery.append("dob = ?, ");
					parameters.add(newDob);
				}

				if (updateQuery.toString().endsWith(", ")) {
					updateQuery.setLength(updateQuery.length() - 2);
				}

				updateQuery.append(" WHERE id = ?");

				try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery.toString())) {
					int index = 1;
					for (Object parameter : parameters) {
						updateStatement.setObject(index++, parameter);
					}
					updateStatement.setLong(index, account.getId());

					int rowsUpdated = updateStatement.executeUpdate();
					if (rowsUpdated > 0 && (isChangingPassword || parameters.size() > 0)) {
						if (isChangingPassword) {
							String hashedPassword = passwordEncoder.encode(newPassword);
							String passwordUpdateQuery = "UPDATE Account SET password = ? WHERE id = ?";
							try (PreparedStatement passwordUpdateStatement = connection.prepareStatement(passwordUpdateQuery)) {
								passwordUpdateStatement.setString(1, hashedPassword);
								passwordUpdateStatement.setLong(2, account.getId());

								int passwordRowsUpdated = passwordUpdateStatement.executeUpdate();
								if (passwordRowsUpdated > 0) {
									connection.commit();

									User updatedUser = new User();
									updatedUser.setFirstName(newFirstName);
									updatedUser.setLastName(newLastName);
									updatedUser.setDateOfBirth(newDob);

									account.setUser(updatedUser);

									return account;
								}
							}
						} else {
							connection.commit();

							User updatedUser = new User();
							updatedUser.setFirstName(newFirstName);
							updatedUser.setLastName(newLastName);
							updatedUser.setDateOfBirth(newDob);

							account.setUser(updatedUser);

							return account;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}
		return null;
	}

	public boolean addSubscriberCardToAccount(Account account) {
		if (account != null) {
			try {
				String query = "SELECT * FROM Account WHERE Account.email = ?";
				try (PreparedStatement statementAccount = connection.prepareStatement(query)) {
					statementAccount.setString(1, account.getEmail());
					try (ResultSet resultSet = statementAccount.executeQuery()) {
						if (resultSet.next()) {
							if (resultSet.getString("is_subscriber").equals("N")) {
								return false;
							} else {
								Random random = new Random();
								SubscriberCard subscriberCard = new SubscriberCard();
								StringBuilder cardNumber = new StringBuilder();
								for (int i = 0; i < 12; i++) {
									int digit = random.nextInt(10);
									cardNumber.append(digit);
								}
								subscriberCard.setCardNumber(cardNumber.toString());
								subscriberCard.setAmount(0);
								String addQuery = "INSERT INTO SubscriberCard (card_number, amount) VALUES (?, ?)";
								try (PreparedStatement addStatement = connection.prepareStatement(addQuery, new String[] {"ID"})) {
									addStatement.setString(1, subscriberCard.getCardNumber());
									addStatement.setLong(2, 0);
									int rowsUpdated = addStatement.executeUpdate();

									if (rowsUpdated > 0) {
										try (ResultSet generatedKeys = addStatement.getGeneratedKeys();) {
											if (generatedKeys.next()) {
												long cardId = generatedKeys.getLong(1);
												subscriberCard.setId(cardId);
												String addAccountSubscriberCardQuery = "INSERT INTO AccountSubscriberCard (id_account, id_subscriber_card) " +
														"VALUES (?, ?)";
												try (PreparedStatement addAccountSubscriberCardStatement = connection.prepareStatement(addAccountSubscriberCardQuery)) {
													addAccountSubscriberCardStatement.setLong(1, account.getId());
													addAccountSubscriberCardStatement.setLong(2, subscriberCard.getId());

													rowsUpdated = addStatement.executeUpdate();

													if (rowsUpdated > 0) {
														((SubscriberAccount)account).addSubscriberCard(subscriberCard);
														connection.commit();
														return true;
													}
												}	                                        
											}
										}
									} else {
										connection.rollback();
									}
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean addCreditCardToAccount(Account account) {
		if (account != null) {
			try {
				Random random = new Random();
				CreditCard creditCard = new CreditCard();
				StringBuilder cardNumber = new StringBuilder();
				for (int i = 0; i < 12; i++) {
					int digit = random.nextInt(10);
					cardNumber.append(digit);
				}
				creditCard.setCardNumber(cardNumber.toString());
				String addQuery = "INSERT INTO SubscriberCard (card_number) VALUES (?)";
				try (PreparedStatement addStatement = connection.prepareStatement(addQuery, new String[] {"ID"})) {
					addStatement.setString(1, creditCard.getCardNumber());
					int rowsUpdated = addStatement.executeUpdate();

					if (rowsUpdated > 0) {
						try (ResultSet generatedKeys = addStatement.getGeneratedKeys();) {
							if (generatedKeys.next()) {
								long cardId = generatedKeys.getLong(1);
								creditCard.setId(cardId);
								String addAccountSubscriberCardQuery = "INSERT INTO AccountSubscriberCard (id_account, id_subscriber_card) " +
										"VALUES (?, ?)";
								try (PreparedStatement addAccountSubscriberCardStatement = connection.prepareStatement(addAccountSubscriberCardQuery)) {
									addAccountSubscriberCardStatement.setLong(1, account.getId());
									addAccountSubscriberCardStatement.setLong(2, creditCard.getId());

									rowsUpdated = addStatement.executeUpdate();

									if (rowsUpdated > 0) {
										account.addCreditCard(creditCard);
										connection.commit();
										return true;
									}
								}                                        
							}
						}
					} else {
						connection.rollback();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
		}
		return false;
	}

	public boolean processPaymentByCreditCard(CreditCard creditCard) {
		return true;
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
