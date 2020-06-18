package csci5408.catme.sql.impl;

import csci5408.catme.sql.IQueryBuilder;
import csci5408.catme.sql.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
public class QueryBuilder implements IQueryBuilder {

	private String sql;

	private Map<String, Object> paramMap;

	private Map<String, Sort> sortOrderMap;

	private Map<String, String> sortParamMap;

	private Set<String> likeSet;

	public QueryBuilder(String sql) {
		this.sql = sql;
		this.paramMap = new HashMap<>();
		this.sortParamMap = new HashMap<>();
		this.sortOrderMap = new HashMap<>();
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
	public void setSortType(String key, Sort value) {
		sortOrderMap.put(key, value);
	}

	@Override
	public void setSortByAttribute(String key, String value) {
		sortParamMap.put(key, value);
	}

	@Override
	public void setParameter(String key, LocalDateTime value) {
		this.setParameterDefault(key, value);
	}

	@Override
	public void setParameter(String key, LocalDate value) {
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
	public void setParameter(String key, Boolean value) {
		this.setParameterDefault(key, value);
	}

	@Override
	public void setParameter(String key, Long value) {
		this.setParameterDefault(key, value);
	}

	@Override
	public String query() {
		String sql = this.sql;
		sql = getParameterizedQuery(sql);
		sql = getSortedQuery(sql);
		return sql;
	}

	private String getSortedQuery(String sql) {
		for (Map.Entry<String, Sort> entry : sortOrderMap.entrySet()) {
			sql = sql.replace(":" + entry.getKey(), entry.getValue().name());
		}

		for (Map.Entry<String, String> entry : sortParamMap.entrySet()
		) {
			sql = sql.replace(":" + entry.getKey(), entry.getValue());
		}
		return sql;
	}


	private String getParameterizedQuery(String sql) {
		for (Map.Entry<String, Object> obj : this.paramMap.entrySet()) {
			String key = ":" + obj.getKey();
			Object val = obj.getValue();
			if (val instanceof String) {
				sql = sql.replace(key, this.sanitize(String.valueOf(val), !likeSet.contains(key)));
			} else if (val instanceof Integer || val instanceof LocalDate || val instanceof Float
					|| val instanceof LocalDateTime || val instanceof Double || val instanceof Long
					|| val instanceof Boolean || val == null) {

				sql = sql.replace(key, this.getObjString(val));
			} else if (val instanceof List || val instanceof Set) {
				if (((Collection) val).size() == 0) {
					throw new RuntimeException("List size of zero");
				}
				List<String> arr = new ArrayList<>();
				for (Object o : (Collection) val) {
					arr.add(this.getObjString(o));
				}
				sql = sql.replace(key, String.join(", ", arr));

			}
		}
		return sql;
	}

	private String getObjString(Object object) {
		if (object instanceof Integer || object instanceof Float || object instanceof Double || object instanceof Long
				|| object instanceof Boolean) {
			return String.valueOf(object);
		} else if (object instanceof LocalDate) {
			return "'" + object + "'";
		} else if (object instanceof LocalDateTime) {
			String val1 = String.join(" ", object.toString().split("T"));
			return "'" + val1 + "'";
		} else if (object instanceof String || object instanceof Character) {
			return this.sanitize(String.valueOf(object), true);
		}
		return String.valueOf(object);
	}

	private String sanitize(String data, Boolean escapePercentage) {
		data = data.replace("\n", "\\n'");
		data = data.replace("\\", "\\\\");
		data = data.replace("'", "''");
		if (escapePercentage) {
			data = data.replace("%", "%%");
		}
		return "'" + data + "'";
	}

	private void setParameterDefault(String key, Object value) {
		if (this.paramMap.containsKey(key)) {
			// log warning ?
		}
		this.paramMap.put(key, value);
	}
}
