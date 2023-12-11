-- Users Table
CREATE TABLE Users (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  first_name VARCHAR2(255) NOT NULL,
  last_name VARCHAR2(255) NOT NULL
);

-- Category Table
CREATE TABLE Category (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  category_name VARCHAR2(255) NOT NULL
);

-- Author Table
CREATE TABLE Author (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  first_name VARCHAR2(255) NOT NULL,
  last_name VARCHAR2(255) NOT NULL
);

-- Actor Table
CREATE TABLE Actor (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  first_name VARCHAR2(255) NOT NULL,
  last_name VARCHAR2(255) NOT NULL
);

-- Film Table
CREATE TABLE Film (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  name VARCHAR2(255) NOT NULL,
  image_path VARCHAR(255),
  duration NUMBER NOT NULL,
  description VARCHAR2(1000) NOT NULL
);

-- Account Table
CREATE TABLE Account (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_users NUMBER,
  email VARCHAR2(255) UNIQUE NOT NULL,
  password VARCHAR2(255) NOT NULL, 
  is_subscriber CHAR(1) CHECK (is_subscriber IN ('Y','N')) NOT NULL,
  nb_allowed_reservations NUMBER NOT NULL,
  FOREIGN KEY (id_users) REFERENCES Users(id) ON DELETE CASCADE
);

-- BlueRay Table
CREATE TABLE BlueRay (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_film NUMBER NOT NULL,
  available NUMBER NOT NULL,
  FOREIGN KEY (id_film) REFERENCES Film(id)
);

-- SubscriberCard Table
CREATE TABLE SubscriberCard (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  card_number VARCHAR2(255) NOT NULL,
  amount FLOAT
);

-- CreditCard Table
CREATE TABLE CreditCard (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  card_number VARCHAR2(255) NOT NULL
);

-- FilmAuthor Table
CREATE TABLE FilmAuthor (
  id_film NUMBER,
  id_author NUMBER,
  PRIMARY KEY (id_film, id_author),
  FOREIGN KEY (id_film) REFERENCES Film(id) ON DELETE CASCADE,
  FOREIGN KEY (id_author) REFERENCES Author(id) ON DELETE CASCADE
);

-- FilmActor Table
CREATE TABLE FilmActor (
  id_film NUMBER,
  id_actor NUMBER,
  PRIMARY KEY (id_film, id_actor),
  FOREIGN KEY (id_film) REFERENCES Film(id) ON DELETE CASCADE,
  FOREIGN KEY (id_actor) REFERENCES Actor(id) ON DELETE CASCADE
);

-- FilmCategory Table
CREATE TABLE FilmCategory (
  id_film NUMBER,
  id_category NUMBER,
  PRIMARY KEY (id_film, id_category),
  FOREIGN KEY (id_film) REFERENCES Film(id) ON DELETE CASCADE,
  FOREIGN KEY (id_category) REFERENCES Category(id) ON DELETE CASCADE
);

-- CurrentReservations Table
CREATE TABLE CurrentReservations (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_account NUMBER NOT NULL,
  id_subscriber_card NUMBER,
  id_credit_card NUMBER,
  id_blueray NUMBER NOT NULL,
  reservation_start_date DATE NOT NULL,
  FOREIGN KEY (id_account) REFERENCES Account(id),
  FOREIGN KEY (id_subscriber_card) REFERENCES SubscriberCard(id) ON DELETE CASCADE,
  FOREIGN KEY (id_blueray) REFERENCES BlueRay(id)
);

-- Transactions Table
CREATE TABLE Transactions (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_account NUMBER NOT NULL,
  id_subscriber_card NUMBER,
  id_credit_card NUMBER,
  amount NUMBER,
  date_of_transaction DATE,
  FOREIGN KEY (id_account) REFERENCES Account(id),
  FOREIGN KEY (id_subscriber_card) REFERENCES SubscriberCard(id),
  FOREIGN KEY (id_credit_card) REFERENCES CreditCard(id)
);

-- QRCodeHistory Table
CREATE TABLE QRCodeHistory (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_account NUMBER NOT NULL,
  id_film NUMBER NOT NULL,
  reservation_start_date DATE NOT NULL,
  expiration_date DATE NOT NULL,
  link VARCHAR2(500) NOT NULL,
  FOREIGN KEY (id_account) REFERENCES Account(id) ON DELETE CASCADE,
  FOREIGN KEY (id_film) REFERENCES Film(id) ON DELETE CASCADE
);

-- AccountCreditCard Table
CREATE TABLE AccountCreditCard (
  id_account NUMBER NOT NULL,
  id_credit_card NUMBER NOT NULL,
  PRIMARY KEY (id_account, id_credit_card),
  FOREIGN KEY (id_account) REFERENCES Account(id) ON DELETE CASCADE,
  FOREIGN KEY (id_credit_card) REFERENCES CreditCard(id) ON DELETE CASCADE
);

-- AccountSubscriberCard Table
CREATE TABLE AccountSubscriberCard (
  id_account NUMBER NOT NULL,
  id_subscriber_card NUMBER NOT NULL,
  PRIMARY KEY (id_account, id_subscriber_card),
  FOREIGN KEY (id_account) REFERENCES Account(id) ON DELETE CASCADE,
  FOREIGN KEY (id_subscriber_card) REFERENCES SubscriberCard(id) ON DELETE CASCADE
);

-- AccountFilterCategory Table
CREATE TABLE AccountFilterCategory (
  id_account NUMBER NOT NULL,
  id_category NUMBER NOT NULL,
  PRIMARY KEY (id_account, id_category),
  FOREIGN KEY (id_account) REFERENCES Account(id) ON DELETE CASCADE,
  FOREIGN KEY (id_category) REFERENCES Category(id)
);

-- FrozenSubscriberCard Table
CREATE TABLE FrozenSubscriberCard (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_subscriber_card NUMBER,
  dateF DATE NOT NULL,
  FOREIGN KEY (id) REFERENCES SubscriberCard(id) ON DELETE CASCADE
);

-- ReservationHistory Table
CREATE TABLE ReservationHistory (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_account NUMBER NOT NULL,
  id_blueray NUMBER NOT NULL,
  id_subscriber_card NUMBER,
  reservation_start_date DATE NOT NULL,
  reservation_end_date DATE NOT NULL,
  FOREIGN KEY (id_account) REFERENCES Account(id) ON DELETE CASCADE,
  FOREIGN KEY (id_blueray) REFERENCES BlueRay(id) ON DELETE CASCADE
);

-- FilmRequestsFromUser Table
CREATE TABLE FilmRequestsFromUser (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_account NUMBER NOT NULL,
  film_name VARCHAR2(255) NOT NULL,
  request_date DATE NOT NULL,
  FOREIGN KEY (id_account) REFERENCES Account(id) ON DELETE CASCADE
);

-- TopFilmsMonth Table
CREATE TABLE TopFilmsMonth (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_film NUMBER NOT NULL,
  number_reservations NUMBER NOT NULL,
  FOREIGN KEY (id_film) REFERENCES Film(id) ON DELETE CASCADE
);

-- TopFilmsWeek Table
CREATE TABLE TopFilmsWeek (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_film NUMBER NOT NULL,
  number_reservations NUMBER NOT NULL,
  FOREIGN KEY (id_film) REFERENCES Film(id) ON DELETE CASCADE
);

-- LostBlueRay Table 
CREATE TABLE LostBlueRay (
  id NUMBER GENERATED by default on null as IDENTITY PRIMARY KEY,
  id_account NUMBER NOT NULL,
  id_blueray NUMBER NOT NULL,
  FOREIGN KEY (id_account) REFERENCES Account(id),
  FOREIGN KEY (id_blueray) REFERENCES BlueRay(id)
);

COMMIT;
