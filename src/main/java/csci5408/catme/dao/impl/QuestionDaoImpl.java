package csci5408.catme.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import csci5408.catme.dao.IQuestionDao;
import csci5408.catme.domain.Question;
import csci5408.catme.sql.impl.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;

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
					"Insert INTO questions (user_id, question_title, question_text, question_type_id, creation_date) values (:userid, :question_title, :question_text, :question_type_id, :creation_date)");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Question question) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder("DELETE FROM question_options WHERE id = :questionId");
			builder.setParameter("questionId", question.getId());

			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);

			builder = new QueryBuilder("DELETE FROM questions WHERE id = :questionId");
			builder.setParameter("questionId", question.getId());

			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);

			rs = s.getGeneratedKeys();
			if (rs.next()) {
				question.setId(rs.getLong(1));
			}

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
	public Map<String, List<Question>> getQuestionsByUser(Long userId) {
		Map<String, List<Question>> sortedListsMap = new HashMap<String, List<Question>>();

		List<Question> creationDateASC = new ArrayList<Question>();
		List<Question> creationDateDESC = new ArrayList<Question>();
		List<Question> titleASC = new ArrayList<Question>();
		List<Question> titleDESC = new ArrayList<Question>();

		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"SELECT * FROM questions WHERE user_id = :user_id ORDER BY creation_date ASC");
			builder.setParameter("user_id", userId);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					Question question = new Question();
					question.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
					question.setId(rs.getLong("id"));
					question.setUserId(userId);
					question.setQuestionTypeId(rs.getLong("question_type_id"));
					question.setQuestionText(rs.getString("question_text"));
					question.setQuestionTitle(rs.getString("question_title"));

					creationDateASC.add(question);
				}
			}

			dataSource.close(rs);

			rs = null;
			builder = new QueryBuilder("SELECT * FROM questions WHERE user_id = :user_id ORDER BY creation_date DESC");
			builder.setParameter("user_id", userId);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					Question question = new Question();
					question.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
					question.setId(rs.getLong("id"));
					question.setUserId(userId);
					question.setQuestionTypeId(rs.getLong("question_type_id"));
					question.setQuestionText(rs.getString("question_text"));
					question.setQuestionTitle(rs.getString("question_title"));

					creationDateDESC.add(question);
				}
			}

			dataSource.close(rs);

			rs = null;
			builder = new QueryBuilder("SELECT * FROM questions WHERE user_id = :user_id ORDER BY question_title ASC");
			builder.setParameter("user_id", userId);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					Question question = new Question();
					question.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
					question.setId(rs.getLong("id"));
					question.setUserId(userId);
					question.setQuestionTypeId(rs.getLong("question_type_id"));
					question.setQuestionText(rs.getString("question_text"));
					question.setQuestionTitle(rs.getString("question_title"));

					titleASC.add(question);
				}
			}

			dataSource.close(rs);

			rs = null;
			builder = new QueryBuilder("SELECT * FROM questions WHERE user_id = :user_id ORDER BY question_title DESC");
			builder.setParameter("user_id", userId);

			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					Question question = new Question();
					question.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
					question.setId(rs.getLong("id"));
					question.setUserId(userId);
					question.setQuestionTypeId(rs.getLong("question_type_id"));
					question.setQuestionText(rs.getString("question_text"));
					question.setQuestionTitle(rs.getString("question_title"));

					titleDESC.add(question);
				}
			}

			sortedListsMap.put("creationDateASC", creationDateASC);
			sortedListsMap.put("creationDateDESC", creationDateDESC);
			sortedListsMap.put("titleASC", titleASC);
			sortedListsMap.put("titleDESC", titleDESC);

			for (Question q : creationDateDESC) {
				System.out.println(q.getQuestionTitle());
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		} finally {
			dataSource.close(s);
			dataSource.close(rs);
			dataSource.close(con);
		}

		return sortedListsMap;
	}

}
