package csci5408.catme.dao.impl;

import csci5408.catme.dao.IQuestionDao;
import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionOptions;
import csci5408.catme.domain.QuestionType;
import csci5408.catme.sql.Sort;
import csci5408.catme.sql.impl.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionDaoImpl implements IQuestionDao {

	final ConnectionManager dataSource;

	public QuestionDaoImpl(ConnectionManager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Question save(Question question) {

		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"Insert INTO questions " +
							"(user_id, question_title, question_text, question_type_id, creation_date) " +
							"values (:userid, :question_title, :question_text, :question_type_id, :creation_date)");
			builder.setParameter("userid", question.getUserId());
			builder.setParameter("question_title", question.getQuestionTitle());
			builder.setParameter("question_text", question.getQuestionText());
			builder.setParameter("question_type_id", question.getQuestionTypeId());
			builder.setParameter("creation_date", question.getCreationDate());

			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);

			rs = s.getGeneratedKeys();
			if (rs.next()) {
				question.setId(rs.getLong(1));
			}
			Optional<Question> q = findById(question.getId());
			return q.orElse(null);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}

		return question;
	}

	@Override
	public Question update(Question t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Question> findById(Long id) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;
		Question question = null;
		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"SELECT q.id as id, q.user_id, question_title, question_text, question_type_id, " +
							"creation_date, qt.question_type as question_type " +
							"from questions q " +
							"inner join question_type qt on q.question_type_id = qt.id " +
							"where q.id = :id");
			builder.setParameter("id", id);


			s.execute(builder.query());

			rs = s.getResultSet();
			if (rs.next()) {
				question = getQuestion(rs);
				List<QuestionOptions> options = getOptionByQuestionId(id);
				question.setQuestionOptions(options);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}
		if (question == null) {
			return Optional.empty();
		}
		return Optional.of(question);
	}

	@Override
	public boolean delete(Question question) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder("DELETE FROM question_options WHERE question_id = :questionId");
			builder.setParameter("questionId", question.getId());

			s.execute(builder.query());

			builder = new QueryBuilder("DELETE FROM questions WHERE id = :questionId");
			builder.setParameter("questionId", question.getId());

			s.execute(builder.query());

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}

		return true;
	}

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getTypeIdByName(String questionType) {
		Long typeId = null;

		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder("SELECT * FROM question_type WHERE question_type = :questionType");
			builder.setParameter("questionType", questionType);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					typeId = rs.getLong("id");
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}

		return typeId;
	}

	@Override
	public QuestionOptions saveOption(QuestionOptions options) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"Insert INTO question_options " +
							"(question_id, storedas, option_value) " +
							"values (:question_id, :storedas, :option_value)");
			builder.setParameter("question_id", options.getQuestionId());
			builder.setParameter("storedas", options.getStoredAs());
			builder.setParameter("option_value", options.getOptionText());

			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);

			rs = s.getGeneratedKeys();
			if (rs.next()) {
				options.setId(rs.getLong(1));
			}

			return options;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}
	}

	@Override
	public List<Question> getQuestionsByUser(Long userId, String attribute, Sort sort) {

		List<Question> questions = new ArrayList<>();
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"SELECT q.id as id, q.user_id, question_title, question_text, question_type_id, " +
							"creation_date, qt.question_type as question_type " +
							"from questions q " +
							"inner join question_type qt on q.question_type_id = qt.id " +
							"WHERE user_id = :user_id " +
							"ORDER BY :sortField :sortType");
			builder.setSortByAttribute("sortField", attribute);
			builder.setSortType("sortType", sort);
			builder.setParameter("user_id", userId);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					questions.add(getQuestion(rs));
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return questions;
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}

		return questions;
	}

	public List<QuestionOptions> getOptionByQuestionId(Long questionId) {
		List<QuestionOptions> options = new ArrayList<>();
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;
		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"SELECT id, question_id, storedas, option_value " +
							"from question_options " +
							"WHERE question_id = :question_id ");
			builder.setParameter("question_id", questionId);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					options.add(getOption(rs));
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return options;
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}

		return options;
	}

	private QuestionOptions getOption(ResultSet rs) throws SQLException {
		QuestionOptions option = new QuestionOptions();
		option.setQuestionId(rs.getLong("question_id"));
		option.setId(rs.getLong("id"));
		option.setOptionText(rs.getString("option_value"));
		option.setStoredAs(rs.getLong("storedas"));
		return option;
	}

	private Question getQuestion(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
		question.setId(rs.getLong("id"));
		question.setUserId(rs.getLong("user_id"));
		question.setQuestionTypeId(rs.getLong("question_type_id"));
		question.setQuestionText(rs.getString("question_text"));
		question.setQuestionTitle(rs.getString("question_title"));
		question.setType(QuestionType.valueOf(rs.getString("question_type")));
		return question;
	}

}
