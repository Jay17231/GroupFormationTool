package csci5408.catme.dao;

import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionOptions;
import csci5408.catme.sql.Sort;

import java.util.List;

public interface IQuestionDao extends IDao<Question, Long> {

	List<Question> getQuestionsByUser(Long userId, String attribute, Sort sort);

	Long getTypeIdByName(String questionType);

	QuestionOptions saveOption(QuestionOptions options);

}
