package gov.fbi.elabs.crossroads.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepository<T> implements GenericRepository<T> {

	private static final Logger logger = LoggerFactory.getLogger(BaseRepository.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public T create(T t) {
		Long seqId = (Long) getCurrentSession().save(t);
		return (T) getCurrentSession().get(t.getClass(), seqId);
	}

	@Override
	public T update(T t, boolean flushFirst) {
		getCurrentSession().update(t);
		if (flushFirst) {
			getCurrentSession().flush();
		}
		return t;
	}

	@Override
	public void delete(T t) {
		getCurrentSession().delete(t);
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Session openSession() {
		return sessionFactory.openSession();
	}

	public List<T> fireQuery(String query, Class<? extends T> clazz) {
		List<T> list = new ArrayList<>();
		try {
			SQLQuery sqlQuery = getCurrentSession().createSQLQuery(query);
			sqlQuery.addEntity(clazz);
			return sqlQuery.list();
		} catch (Exception e) {
			logger.error("Error occured while firing querty ", e);
			return list;
		}
	}

	public SQLQuery createSQLQuery(String query) {
		try {
			SQLQuery sqlQuery = getCurrentSession().createSQLQuery(query);
			return sqlQuery;
		} catch (Exception e) {
			logger.error("Error occured while firing querty ", e);
			return null;
		}
	}

}
