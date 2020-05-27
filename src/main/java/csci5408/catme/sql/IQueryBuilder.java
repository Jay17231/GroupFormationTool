package csci5408.catme.sql;

import java.util.Collection;
import java.util.Date;

public interface IQueryBuilder {
    void setParameter(String key, Integer value);
    void setParameter(String key, String value);
    void setParameter(String key, Date value);
    void setParameter(String key, Double value);
    void setLikeParameter(String key, String value);
    void setParameter(String key, Float value);
    void setParameter(String key, Collection<?> value);
    String query();
}
