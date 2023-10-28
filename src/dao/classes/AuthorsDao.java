package dao.classes;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import beans.Author;

public class AuthorsDao extends Dao<Author> {

    private ArrayList<Author> authorList;

    public AuthorsDao(Connection connection) {
    	super(connection);
        authorList = new ArrayList<>();
        authorList.add(new Author((long) 1, "John Doe"));
        authorList.add(new Author((long) 2, "Jane Smith"));
    }

    @Override
    public void create(Author author) {
        authorList.add(author);
    }

    @Override
    public Author getById(Long id) {
        for (Author author : authorList) {
            if (author.getId().equals(id)) {
                return author;
            }
        }
        return null;
    }

    @Override
    public void update(Author author) {
        for (int i = 0; i < authorList.size(); i++) {
            Author existingAuthor = authorList.get(i);
            if (existingAuthor.getId().equals(author.getId())) {
                authorList.set(i, author);
                return;
            }
        }
    }

    @Override
    public void delete(Author author) {
        authorList.removeIf(a -> a.getId().equals(author.getId()));
    }

    @Override
    public List<Author> getAll() {
        return new ArrayList<>(authorList);
    }
}

