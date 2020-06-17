package csci5408.catme.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import csci5408.catme.dao.IQuestionDao;
import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionType;
import csci5408.catme.service.IQuestionService;

@Service
public class QuestionServiceImpl implements IQuestionService {

	final IQuestionDao questionDao;

	public QuestionServiceImpl(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	};

	@Override
	public List<QuestionType> getQuestionTypes() {
		List<QuestionType> qTypes = new ArrayList<QuestionType>();
		for (QuestionType q : QuestionType.values()) {
			qTypes.add(q);
		}
		return qTypes;
	}

	@Override
	public Long getQuestionTypeIdByName(String questionType) {
		Long typeId = null;
		typeId = questionDao.getTypeIdByName(questionType);
		return typeId;
	}

	@Override
	public Question addQuestion(Question question) {
		Question addedQuestion = questionDao.save(question);
		return addedQuestion;
	}

	@Override
	public Boolean removeQuestion(Question question) {
		boolean removedQuestion = questionDao.delete(question);
		return removedQuestion;
	}

	@Override
	public List<Question> questionsByUser(Long userId, String typeOfSort) {

		Map<String, List<Question>> sortedMaps = questionDao.getQuestionsByUser(userId);
		List<Question> allQuestions = sortedMaps.get("creationDateDESC");

		if (typeOfSort.compareToIgnoreCase("sort-cd-asc") == 0) {
			allQuestions = sortedMaps.get("creationDateASC");
		}
		if (typeOfSort.compareToIgnoreCase("sort-cd-desc") == 0) {
			allQuestions = sortedMaps.get("creationDateDESC");
		}
		if (typeOfSort.compareToIgnoreCase("sort-title-asc") == 0) {
			allQuestions = sortedMaps.get("titleASC");
		}
		if (typeOfSort.compareToIgnoreCase("sort-title-desc") == 0) {
			allQuestions = sortedMaps.get("titleDESC");
		}

		return allQuestions;
	}

}
