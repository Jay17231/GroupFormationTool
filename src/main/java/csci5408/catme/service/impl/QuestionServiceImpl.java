package csci5408.catme.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import csci5408.catme.dao.IQuestionDao;
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

}
