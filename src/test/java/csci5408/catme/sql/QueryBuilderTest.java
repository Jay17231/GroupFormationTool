package csci5408.catme.sql;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
public class QueryBuilderTest {

    @Test
    public void queryTest() {
        QueryBuilder builder = new QueryBuilder("SELECT * from ABC");
        assertNotNull(builder.query());

    }

    @Test
    public void setParameterTest() {
        setParameterTestNumbers();
        setParameterTestStrings();
        setParameterTestBoolean();
        setParameterTestDate();
        setParameterTestCollections();
        setParameterTestNull();
        setParameterTestEmptyCollection();
    }

    private void setParameterTestEmptyCollection() {
        QueryBuilder builder1;
        String q1;

        builder1 = new QueryBuilder("SELECT * from ABC where i in (:abc)");
        builder1.setParameter("abc", new ArrayList<>());
        assertThrows(RuntimeException.class, builder1::query);
    }

    private void setParameterTestCollections() {
        QueryBuilder builder1;
        String q1;

        builder1 = new QueryBuilder("SELECT * from ABC where i in (:abc)");
        builder1.setParameter("abc", Arrays.asList(1,2.0f,3.2d, '1', true));
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i in (1, 2.0, 3.2, '1', true)", q1);
    }

    private void setParameterTestNumbers() {
        QueryBuilder builder1;  
        String q1;

        builder1 = new QueryBuilder("SELECT * from ABC where i > :abc and k < :def");
        builder1.setParameter("abc", 1);
        builder1.setParameter("def", 1L);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i > 1 and k < 1", q1);

        builder1 = new QueryBuilder("SELECT * from ABC where i > :abc and k < :def");
        builder1.setParameter("abc", 1f);
        builder1.setParameter("def", 2.1f);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i > 1.0 and k < 2.1", q1);

        builder1 = new QueryBuilder("SELECT * from ABC where i > :abc and k < :def");
        builder1.setParameter("abc", 1d);
        builder1.setParameter("def", 2.1d);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i > 1.0 and k < 2.1", q1);
    }

    private void setParameterTestStrings() {
        QueryBuilder builder1;
        String q1;
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

    private void setParameterTestBoolean() {
        QueryBuilder builder1;
        String q1;
        builder1 = new QueryBuilder("SELECT * from ABC where i is :abc and k = :def");
        builder1.setParameter("abc", true);
        builder1.setParameter("def", false);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i is true and k = false", q1);
    }

    private void setParameterTestNull() {
        QueryBuilder builder1;
        String q1;
        builder1 = new QueryBuilder("SELECT * from ABC where i is :abc");
        builder1.setParameter("abc", (String) null);
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i is null", q1);
    }

    private void setParameterTestDate() {
        QueryBuilder builder1;
        String q1;


        builder1 = new QueryBuilder("SELECT * from ABC where i > :abc and k < :def");
        builder1.setParameter("abc", LocalDate.of(2020, 5,27));
        builder1.setParameter("def", LocalDateTime.of(2020, 5, 27, 23, 0, 1));
        q1 = builder1.query();
        assertNotNull(q1);
        assertEquals("SELECT * from ABC where i > '2020-05-27' and k < '2020-05-27 23:00:01'", q1);
    }
}
