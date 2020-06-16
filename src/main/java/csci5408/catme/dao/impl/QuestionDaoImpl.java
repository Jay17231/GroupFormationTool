package csci5408.catme.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			dataSource.close(s);
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
		Statement s = null;
		assert con != null;

		try {

			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder("DELETE FROM questions WHERE id = :questionId");
			builder.setParameter("questionId", question.getId());

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			dataSource.close(s);
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
	public List<Question> getQuestionsByUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question findQuestionById(Long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionType(Long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
