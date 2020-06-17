package csci5408.catme.dao;

import java.util.List;
import java.util.Map;

import csci5408.catme.domain.Question;

public interface IQuestionDao extends IDao<Question, Long> {

	Map<String, List<Question>> getQuestionsByUser(Long userId);

	Long getTypeIdByName(String questionType);

}
