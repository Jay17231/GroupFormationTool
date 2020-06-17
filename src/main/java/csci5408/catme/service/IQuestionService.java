package csci5408.catme.service;

import java.util.List;

import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionType;

public interface IQuestionService {

	List<QuestionType> getQuestionTypes();

	List<Question> questionsByUser(Long userId);

	Long getQuestionTypeIdByName(String questionType);

	Question addQuestion(Question question);

	Boolean removeQuestion(Question question);

}
