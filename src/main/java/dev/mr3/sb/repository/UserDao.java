package dev.mr3.sb.repository;
import dev.mr3.sb.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    private final static String jdbcURL = "jdbc:mysql://localhost:3306/mydb";
    private final static String jdbcUsername = "root";
    private final static String jdbcPassword = "root";

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

    static public boolean registerUser(User user) {
        boolean isSaved = false;

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                isSaved = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }
}