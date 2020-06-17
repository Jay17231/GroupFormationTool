package csci5408.catme.service;

import java.util.List;

import csci5408.catme.domain.QuestionType;

public interface IQuestionService {

	List<QuestionType> getQuestionTypes();

	Long getQuestionTypeIdByName(String questionType);

}
