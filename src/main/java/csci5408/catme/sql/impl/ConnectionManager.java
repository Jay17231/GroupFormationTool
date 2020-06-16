package csci5408.catme.sql.impl;

import csci5408.catme.configuration.ConfigProperties;
import csci5408.catme.sql.IConnectionManager;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
@Service
public class ConnectionManager implements IConnectionManager {

    private ConfigProperties configProperties;

    public ConnectionManager(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(configProperties.getDbUrl(),
                    configProperties.getDbUsername(), configProperties.getDbPassword());
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
