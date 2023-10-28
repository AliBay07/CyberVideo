package dao.classes;

import java.sql.Connection;
import java.util.List;

public abstract class Dao<T> {
	
	private Connection connection = null;
	
	public Dao (Connection connection) {
		this.connection = connection;
	}
	
    abstract void create(T entity);

    abstract T getById(Long id);

    abstract void update(T entity);

    abstract void delete(T entity);

    abstract List<T> getAll();
}
