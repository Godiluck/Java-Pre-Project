package vladdossik.jdbc.jpa.service;

import vladdossik.jdbc.jpa.dao.UserDao;
import vladdossik.jdbc.jpa.dao.UserDaoHibernateImpl;
import vladdossik.jdbc.jpa.dao.UserDaoJDBCImpl;
import vladdossik.jdbc.jpa.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
//        this.userDao = new UserDaoJDBCImpl();
        this.userDao = new UserDaoHibernateImpl();
    }

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем – "+name+" добавлен в базу данных");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
