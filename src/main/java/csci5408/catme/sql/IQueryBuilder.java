package csci5408.catme.sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
public interface IQueryBuilder {
    void setParameter(String key, Integer value);
    void setParameter(String key, String value);
    void setParameter(String key, LocalDate value);
    void setParameter(String key, LocalDateTime value);
    void setParameter(String key, Double value);
    void setParameter(String key, Float value);
    void setParameter(String key, Collection<?> value);
    void setParameter(String key, Boolean value);
    void setParameter(String key, Long value);
    void setLikeParameter(String key, String value);
    String query();
}
