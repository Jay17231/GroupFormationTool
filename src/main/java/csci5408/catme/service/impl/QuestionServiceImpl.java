package csci5408.catme.service.impl;

import csci5408.catme.dao.IQuestionDao;
import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionType;
import csci5408.catme.service.IQuestionService;
import csci5408.catme.sql.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements IQuestionService {

	final IQuestionDao questionDao;

	public QuestionServiceImpl(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	};

	@Override
	public List<QuestionType> getQuestionTypes() {
		List<QuestionType> qTypes = new ArrayList<QuestionType>(Arrays.asList(QuestionType.values()));
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
	public Question getById(Long id) {
		Optional<Question> q = questionDao.findById(id);
		return q.orElse(null);
	}

	@Override
	public List<Question> questionsByUser(Long userId, String sortType, String sortField) {

		Sort order = Sort.DESC;
		String field = "creation_date";

		if (sortType.equalsIgnoreCase("ASC")) {
			order = Sort.ASC;
		}

		if (
				sortField.equalsIgnoreCase("question_title") ||
						sortField.equalsIgnoreCase("questionTitle")
		) {
			field = "question_title";
		}

		List<Question> questions = questionDao.getQuestionsByUser(userId, field, order);
		return questions;
	}

}
