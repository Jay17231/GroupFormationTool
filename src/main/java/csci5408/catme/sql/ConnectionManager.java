package csci5408.catme.sql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
@Service
public class ConnectionManager {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(Statement s) {
        try {
            s.close();
        } catch (Exception ignored) {

        }
    }
    public void close(Connection c) {
        try {
            c.close();
        } catch (Exception ignored) {

        }
    }

    public void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception ignored) {

        }
    }

}
