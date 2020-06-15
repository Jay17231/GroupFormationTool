package csci5408.catme.sql;

import csci5408.catme.sql.impl.ConnectionManager;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 * @deprecated in favour of {@link IConnectionManager}
 */
@Component
public class MySQLDataSource {

    Integer numberOfConnections = 1;
    Set<Connection> pool = new HashSet<>();

    final ConnectionManager manager;

    public MySQLDataSource(ConnectionManager manager) {
        this.manager = manager;
    }

    private void initializePool() {
        Connection conn;
        for(int i=0; i<numberOfConnections; i++) {
            conn = manager.getConnection();
            assert conn != null;
            pool.add(conn);
        }
    }

    public Integer poolSize() {
        return pool.size();
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            synchronized (this) {
                while (con == null || con.isClosed()) {
                    if(pool.size() == 0) {
                        initializePool();
                    }
                    con = pool.iterator().next();
                    pool.remove(con);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public boolean recycle(Connection con) {
        try {
            if(con.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        pool.add(con);
        return true;
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
