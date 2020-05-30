package csci5408.catme.sql;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class MySQLDataSourceTest {
    @Test
    public void todo() throws SQLException {
        ConnectionManager manager = mock(ConnectionManager.class);
        Mockito.when(manager.getConnection()).then(MockConnection::getConnection);
        MySQLDataSource ds = new MySQLDataSource(manager);
        assertNotNull(ds);
        Connection c = ds.getConnection();
        assertEquals(9, ds.poolSize());
        assertTrue(ds.recycle(c));
        assertEquals(10, ds.poolSize());
        c = ds.getConnection();
        c.close();
        assertFalse(ds.recycle(c));
        assertEquals(9, ds.poolSize());
    }
}
