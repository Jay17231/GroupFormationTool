package csci5408.catme.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QueryBuilderTest {

    @Test
    public void queryTest() {
        QueryBuilder builder = new QueryBuilder("SELECT * from ABC");
        assertNotNull(builder.query());

    }

    @Test
    public void setParameterTest() {
        QueryBuilder builder1;
        String q1;

        builder1 = new QueryBuilder("SELECT * from ABC where i > :abc");
        builder1.setParameter("abc", 1);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i > 1", q1);

        builder1 = new QueryBuilder("SELECT * from ABC where i > :abc and k < :def");
        builder1.setParameter("abc", 1f);
        builder1.setParameter("def", 2.1f);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i > 1.0 and k < 2.1", q1);

        builder1 = new QueryBuilder(
                "SELECT * from ABC " +
                    "where i = :abc and j = :def and k = :lmn " +
                    "and l like :xyz "
        );
        builder1.setParameter("abc", "1");
        builder1.setParameter("def", "'1");
        builder1.setParameter("lmn", "%1");
        builder1.setLikeParameter("xyz", "%1");
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC " +
                "where i = '1' and j = '''1' and k = '%%1' " +
                "and l like '%1' ", q1);
    }
}
