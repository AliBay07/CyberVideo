package dao.tools;

import java.util.HashMap;
import java.util.Map;

import dao.classes.Dao;

public class DaoFactory {

	private static DaoFactory instance;
	private Map<Class<?>, Dao<?>> daoMap;

	private DaoFactory() {
		daoMap = new HashMap<>();
	}

	public static DaoFactory getInstance() {
		if (instance == null) {
			synchronized (DaoFactory.class) {
				if (instance == null) {
					instance = new DaoFactory();
				}
			}
		}
		return instance;
	}

	public <T> void registerDao(Class<T> clazz, Dao<T> dao) {
		daoMap.put(clazz, dao);
	}
	
	public void registerDaos(Map<Class<?>, Dao<?>> daos) {
        for (Map.Entry<Class<?>, Dao<?>> entry : daos.entrySet()) {
        	daoMap.put(entry.getKey(), entry.getValue());
        }
    }

	public <T> Dao<T> getDao(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		Dao<T> dao = (Dao<T>) daoMap.get(clazz);
		if (dao == null) {
			throw new IllegalArgumentException("No DAO registered for class " + clazz.getName());
		}
		return dao;
	}

}
