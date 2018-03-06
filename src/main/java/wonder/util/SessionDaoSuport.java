package wonder.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionDaoSuport {
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session session() {
		return sessionFactory.openSession();
	}

	public Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
}
