package vladdossik.jdbc.jpa.util;

import org.hibernate.Session;

public class HibernateSession implements  AutoCloseable {
    private final Session session;

    public HibernateSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void close() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
