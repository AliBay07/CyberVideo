-- Part to insert authors
-- Insert into Author Table
INSERT INTO Author (first_name, last_name) VALUES ('Christopher', 'Nolan');
INSERT INTO Author (first_name, last_name) VALUES ('Quentin', 'Tarantino');
INSERT INTO Author (first_name, last_name) VALUES ('Steven', 'Spielberg');

COMMIT;

-- Part to insert actors
-- Insert into Actor Table
INSERT INTO Actor (first_name, last_name) VALUES ('Tom', 'Hanks');
INSERT INTO Actor (first_name, last_name) VALUES ('Brad', 'Pitt');
INSERT INTO Actor (first_name, last_name) VALUES ('Meryl', 'Streep');

COMMIT;

-- Part to insert category
-- Insert into Category Table
INSERT INTO Category (CATEGORY_NAME) VALUES ('Action');
INSERT INTO Category (CATEGORY_NAME) VALUES ('Drama');
INSERT INTO Category (CATEGORY_NAME) VALUES ('Comedy');

COMMIT;

-- Part to insert films
-- Insert into Film Table
INSERT INTO Film (name, image_path, duration, description) VALUES ('Inception', 'inception.jpg', 148, 'A mind-bending thriller.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Pulp Fiction', 'pulp_fiction.jpg', 154, 'Crime, thrill, and dark humor.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('The Dark Knight', 'dark_knight.jpg', 152, 'A dark and gripping Batman tale.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Saving Private Ryan', 'private_ryan.jpg', 169, 'War drama directed by Spielberg.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Django Unchained', 'django.jpg', 165, 'A revenge-driven western.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Interstellar', 'interstellar.jpg', 169, 'Space exploration and time dilation.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Forrest Gump', 'forrest_gump.jpg', 142, 'Life is like a box of chocolates.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('The Shawshank Redemption', 'shawshank_redemption.jpg', 142, 'Hope amidst imprisonment.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Kill Bill: Vol. 1', 'kill_bill_1.jpg', 111, 'Revenge saga.');
INSERT INTO Film (name, image_path, duration, description) VALUES ('Jurassic Park', 'jurassic_park.jpg', 127, 'Dinosaurs on the loose.');

COMMIT;

-- Part to link authors as directors to films
-- Insert into FilmAuthor Table
INSERT INTO FilmAuthor (id_film, id_author) VALUES (1, 1); -- Inception by Christopher Nolan
INSERT INTO FilmAuthor (id_film, id_author) VALUES (2, 2); -- Pulp Fiction by Quentin Tarantino
INSERT INTO FilmAuthor (id_film, id_author) VALUES (3, 1); -- The Dark Knight by Christopher Nolan
INSERT INTO FilmAuthor (id_film, id_author) VALUES (4, 3); -- Saving Private Ryan by Steven Spielberg
INSERT INTO FilmAuthor (id_film, id_author) VALUES (5, 2); -- Django Unchained by Quentin Tarantino
INSERT INTO FilmAuthor (id_film, id_author) VALUES (6, 1); -- Interstellar by Christopher Nolan
INSERT INTO FilmAuthor (id_film, id_author) VALUES (7, 3); -- Forrest Gump by Steven Spielberg
INSERT INTO FilmAuthor (id_film, id_author) VALUES (8, 1); -- The Shawshank Redemption by Christopher Nolan
INSERT INTO FilmAuthor (id_film, id_author) VALUES (9, 2); -- Kill Bill: Vol. 1 by Quentin Tarantino
INSERT INTO FilmAuthor (id_film, id_author) VALUES (10, 3); -- Jurassic Park by Steven Spielberg

COMMIT;

-- Part to assign actors to films
-- Insert into FilmActor Table
INSERT INTO FilmActor (id_film, id_actor) VALUES (1, 1); -- Inception - Tom Hanks
INSERT INTO FilmActor (id_film, id_actor) VALUES (2, 2); -- Pulp Fiction - Brad Pitt
INSERT INTO FilmActor (id_film, id_actor) VALUES (3, 1); -- The Dark Knight - Tom Hanks
INSERT INTO FilmActor (id_film, id_actor) VALUES (4, 2); -- Saving Private Ryan - Brad Pitt
INSERT INTO FilmActor (id_film, id_actor) VALUES (5, 3); -- Django Unchained - Meryl Streep
INSERT INTO FilmActor (id_film, id_actor) VALUES (6, 1); -- Interstellar - Tom Hanks
INSERT INTO FilmActor (id_film, id_actor) VALUES (7, 3); -- Forrest Gump - Meryl Streep
INSERT INTO FilmActor (id_film, id_actor) VALUES (8, 2); -- The Shawshank Redemption - Brad Pitt
INSERT INTO FilmActor (id_film, id_actor) VALUES (9, 3); -- Kill Bill: Vol. 1 - Meryl Streep
INSERT INTO FilmActor (id_film, id_actor) VALUES (10, 1); -- Jurassic Park - Tom Hanksx

COMMIT;

-- Part to link films to categories
-- Insert into FilmCategory Table
INSERT INTO FilmCategory (id_film, id_category) VALUES (1, 1); -- Inception - Action
INSERT INTO FilmCategory (id_film, id_category) VALUES (2, 3); -- Pulp Fiction - Comedy
INSERT INTO FilmCategory (id_film, id_category) VALUES (3, 1); -- The Dark Knight - Action
INSERT INTO FilmCategory (id_film, id_category) VALUES (4, 2); -- Saving Private Ryan - Drama
INSERT INTO FilmCategory (id_film, id_category) VALUES (5, 3); -- Django Unchained - Comedy
INSERT INTO FilmCategory (id_film, id_category) VALUES (6, 1); -- Interstellar - Action
INSERT INTO FilmCategory (id_film, id_category) VALUES (7, 2); -- Forrest Gump - Drama
INSERT INTO FilmCategory (id_film, id_category) VALUES (8, 2); -- The Shawshank Redemption - Drama
INSERT INTO FilmCategory (id_film, id_category) VALUES (9, 1); -- Kill Bill: Vol. 1 - Action
INSERT INTO FilmCategory (id_film, id_category) VALUES (10, 2); -- Jurassic Park - Drama

COMMIT;

-- Part to insert Blu-ray availability
-- Insert into BlueRay Table
INSERT INTO BlueRay (id_film, available) VALUES (1, 1); -- Blu-ray for Inception
INSERT INTO BlueRay (id_film, available) VALUES (2, 1); -- Blu-ray for Pulp Fiction
INSERT INTO BlueRay (id_film, available) VALUES (3, 1); -- Blu-ray for The Dark Knight
INSERT INTO BlueRay (id_film, available) VALUES (4, 1); -- Blu-ray for Saving Private Ryan
INSERT INTO BlueRay (id_film, available) VALUES (5, 1); -- Blu-ray for Django Unchained
INSERT INTO BlueRay (id_film, available) VALUES (6, 1); -- Blu-ray for Interstellar
INSERT INTO BlueRay (id_film, available) VALUES (7, 1); -- Blu-ray for Forrest Gump
INSERT INTO BlueRay (id_film, available) VALUES (8, 1); -- Blu-ray for The Shawshank Redemption
INSERT INTO BlueRay (id_film, available) VALUES (9, 1); -- Blu-ray for Kill Bill: Vol. 1
INSERT INTO BlueRay (id_film, available) VALUES (10, 1); -- Blu-ray for Jurassic Park

COMMIT;

-- Part to insert users and cards
-- Insert into Users Table
INSERT INTO Users (first_name, last_name) VALUES ('John', 'Doe');
INSERT INTO Users (first_name, last_name) VALUES ('Jane', 'Smith');
INSERT INTO Users (first_name, last_name) VALUES ('Michael', 'Johnson');

COMMIT;

-- Insert into Account Table (with users having accounts)
INSERT INTO Account (id_users, email, password, is_subscriber, nb_allowed_reservations)
VALUES (1, 'john@example.com', '$2a$10$jyT0stzpL5DWAw3YSu7d3u5LQ5ker9oBhr1cji4QlFD5GVNJPNkXm', 'Y', 10);
INSERT INTO Account (id_users, email, password, is_subscriber, nb_allowed_reservations)
VALUES (2, 'jane@example.com', '$2a$10$8ie3EhqLKWEUdxaKPsaHsuGA/Qxm7Y32tKFGBmAm5vrLy4Ots2yeW', 'Y', 8);
INSERT INTO Account (id_users, email, password, is_subscriber, nb_allowed_reservations)
VALUES (3, 'michael@example.com', '$2a$10$j6IY.3dcPL4Hxowo25C5zuIGF0LEAHh8uKO0PAaXX.mYkcDEhyTbS', 'N', 5);

COMMIT;

-- Insert into SubscriberCard Table (for subscribers)
INSERT INTO SubscriberCard (card_number, amount) VALUES ('1111-1111-1111-1111', 50.00);
INSERT INTO SubscriberCard (card_number, amount) VALUES ('2222-2222-2222-2222', 45.00);

COMMIT;

-- Insert into CreditCard Table (for all users)
INSERT INTO CreditCard (card_number) VALUES ('1111-1111-1111-1111');
INSERT INTO CreditCard (card_number) VALUES ('2222-2222-2222-2222');
INSERT INTO CreditCard (card_number) VALUES ('3333-3333-3333-3333');

COMMIT;

-- Insert into AccountSubscriberCard Table (linking subscribers with subscriber cards)
INSERT INTO AccountSubscriberCard (id_account, id_subscriber_card) VALUES (1, 1); -- John's subscriber card
INSERT INTO AccountSubscriberCard (id_account, id_subscriber_card) VALUES (2, 2); -- Jane's subscriber card

COMMIT;

-- Insert into AccountCreditCard Table (linking all users with credit cards)
INSERT INTO AccountCreditCard (id_account, id_credit_card) VALUES (1, 1); -- John's credit card
INSERT INTO AccountCreditCard (id_account, id_credit_card) VALUES (2, 2); -- Jane's credit card
INSERT INTO AccountCreditCard (id_account, id_credit_card) VALUES (3, 3); -- Michael's credit card

COMMIT;

-- John's reservations
INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (1, 1, 1, 1, CURRENT_DATE); -- John's Reservation 1 for Inception (Blu-ray ID: 1)

INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (1, 1, 1, 3, CURRENT_DATE); -- John's Reservation 2 for The Dark Knight (Blu-ray ID: 3)

INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (1, 1, 1, 6, CURRENT_DATE); -- John's Reservation 3 for Interstellar (Blu-ray ID: 6)

-- Jane's reservations
INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (2, 2, 2, 2, CURRENT_DATE); -- Jane's Reservation 1 for Pulp Fiction (Blu-ray ID: 2)

INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (2, 2, 2, 5, CURRENT_DATE); -- Jane's Reservation 2 for Django Unchained (Blu-ray ID: 5)

INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (2, 2, 2, 8, CURRENT_DATE); -- Jane's Reservation 3 for The Shawshank Redemption (Blu-ray ID: 8)

-- Michael's reservations
INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (3, NULL, 3, 4, CURRENT_DATE); -- Michael's Reservation 1 for Saving Private Ryan (Blu-ray ID: 4)

INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (3, NULL, 3, 7, CURRENT_DATE); -- Michael's Reservation 2 for Forrest Gump (Blu-ray ID: 7)

INSERT INTO CurrentReservations (id_account, id_subscriber_card, id_credit_card, id_blueray, reservation_start_date)
VALUES (3, NULL, 3, 9, CURRENT_DATE); -- Michael's Reservation 3 for Kill Bill: Vol. 1 (Blu-ray ID: 9)

COMMIT;

-- Top films for the week
INSERT INTO TopFilmsWeek (id_film, number_reservations) VALUES (3, 8); -- The Dark Knight - 8 reservations
INSERT INTO TopFilmsWeek (id_film, number_reservations) VALUES (6, 7); -- Interstellar - 7 reservations
INSERT INTO TopFilmsWeek (id_film, number_reservations) VALUES (2, 6); -- Pulp Fiction - 6 reservations

COMMIT;

-- Top films for the month
INSERT INTO TopFilmsMonth (id_film, number_reservations) VALUES (3, 18); -- The Dark Knight - 18 reservations
INSERT INTO TopFilmsMonth (id_film, number_reservations) VALUES (6, 15); -- Interstellar - 15 reservations
INSERT INTO TopFilmsMonth (id_film, number_reservations) VALUES (2, 12); -- Pulp Fiction - 12 reservations

COMMIT;

