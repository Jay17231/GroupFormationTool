package csci5408.catme.sql;

import java.sql.Connection;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
public interface IConnectionManager {
    Connection getConnection();
}
