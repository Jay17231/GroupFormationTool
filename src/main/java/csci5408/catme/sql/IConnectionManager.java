package csci5408.catme.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
public interface IConnectionManager {
    Connection getConnection();

    void close(Statement s);

    void close(ResultSet rs);

    void close(Connection c);
}
