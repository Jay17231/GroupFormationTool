package csci5408.catme.sql;

import java.util.*;

public class QueryBuilder implements IQueryBuilder {

    private String sql;

    private Map<String, Object> paramMap;

    private Set<String> likeSet;

    public QueryBuilder(String sql) {
        this.sql = sql;
        this.paramMap = new HashMap<>();
        this.likeSet = new HashSet<>();
    }

    @Override
    public void setParameter(String key, Integer value) {
        this.setParameterDefault(key, value);
    }

    @Override
    public void setParameter(String key, String value) {
        this.setParameterDefault(key, value);
    }

    @Override
    public void setParameter(String key, Date value) {
        this.setParameterDefault(key, value);
    }

    @Override
    public void setParameter(String key, Double value) {
        this.setParameterDefault(key, value);
    }

    @Override
    public void setLikeParameter(String key, String value) {
        likeSet.add(":" + key);
        setParameter(key, value);
    }

    @Override
    public void setParameter(String key, Float value) {
        this.setParameterDefault(key, value);
    }

    @Override
    public void setParameter(String key, Collection<?> value) {
        this.setParameterDefault(key, value);
    }

    @Override
    public String query() {
        String sql = this.sql;
        for (Map.Entry<String, Object> obj :
                this.paramMap.entrySet()) {
            String key = ":" + obj.getKey();
            Object val = obj.getValue();
            if(val instanceof Integer) {
                sql = sql.replaceAll(key, String.valueOf(val));
            } else if(val instanceof Float) {
                sql = sql.replaceAll(key, String.valueOf(val));
            } else if(val instanceof String) {
                sql = sql.replaceAll(key, this.sanitize(String.valueOf(val), !likeSet.contains(key)));
            }
        }
        return sql;
    }

    private String sanitize(String data, Boolean escapePercentage) {
        data = data.replace("\n","\\n'");
        data = data.replace("\\","\\\\");
        data = data.replace("'","''");
        if(escapePercentage) {
            data = data.replace("%","%%");
        }
        return "'" + data + "'";
    }

    private void setParameterDefault(String key, Object value) {
        if(this.paramMap.containsKey(key)) {
            // log warning ?
        }
        this.paramMap.put(key, value);
    }
}
