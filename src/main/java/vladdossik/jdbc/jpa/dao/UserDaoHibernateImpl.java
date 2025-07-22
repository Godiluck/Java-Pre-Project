package vladdossik.jdbc.jpa.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vladdossik.jdbc.jpa.model.User;
import vladdossik.jdbc.jpa.util.HibernateSession;
import vladdossik.jdbc.jpa.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(50)," +
                "lastName VARCHAR(50)," +
                "age INT" +
                ")";
        try (HibernateSession hibernateSession = new HibernateSession(Util.getSessionFactory().openSession())) {
            Session session = hibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (HibernateSession hibernateSession = new HibernateSession(Util.getSessionFactory().openSession())) {
            Session session = hibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (HibernateSession hibernateSession = new HibernateSession(Util.getSessionFactory().openSession())) {
            Session session = hibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (HibernateSession hibernateSession = new HibernateSession(Util.getSessionFactory().openSession())) {
            Session session = hibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (HibernateSession hibernateSession = new HibernateSession(Util.getSessionFactory().openSession())) {
            Session session = hibernateSession.getSession();
            users = session.createQuery("from User").list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (HibernateSession hibernateSession = new HibernateSession(Util.getSessionFactory().openSession())) {
            Session session = hibernateSession.getSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}
