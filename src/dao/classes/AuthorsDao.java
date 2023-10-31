package dao.classes;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import beans.Author;

public class AuthorsDao extends Dao<Author> {

    private ArrayList<Author> authorList;

    public AuthorsDao(Connection connection) {
    	super(connection);
    }

}

