package dao.classes;

import java.sql.Connection;
import java.util.List;

public abstract class Dao<T> {
	
	protected Connection connection = null;
	
	public Dao (Connection connection) {
		this.connection = connection;
	}
	
}
