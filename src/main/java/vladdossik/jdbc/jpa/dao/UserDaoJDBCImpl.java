package vladdossik.jdbc.jpa.dao;

import vladdossik.jdbc.jpa.model.User;
import vladdossik.jdbc.jpa.util.Util;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    private Connection getConnection() throws SQLException {
        return Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(50)," +
                "lastName VARCHAR(50)," +
                "age SMALLINT" +
                ")";
        try (Connection connection = getConnection(); Statement statement  = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = getConnection(); Statement statement  = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement prepareStatement  = connection.prepareStatement(sql)) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setShort(3, age);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement prepareStatement  = connection.prepareStatement(sql)) {
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users";
        try (Connection connection = getConnection(); Statement statement  = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection connection = getConnection(); Statement statement  = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
