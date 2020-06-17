package csci5408.catme.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csci5408.catme.dao.IQuestionDao;
import csci5408.catme.domain.QuestionType;

public class QuestionServiceImplTest {

	final IQuestionDao questionDao;

	final AuthenticationServiceImpl authenticationServiceImpl;

	QuestionServiceImpl questionServiceImpl;

	public QuestionServiceImplTest() {

		this.authenticationServiceImpl = mock(AuthenticationServiceImpl.class);
		this.questionDao = mock(IQuestionDao.class);
		this.questionServiceImpl = mock(QuestionServiceImpl.class);

	}

	@BeforeEach
	public void setup() {
		questionServiceImpl = new QuestionServiceImpl(questionDao);
	}

	@Test
	public void getQuestionTypesTest() {
		assertEquals("FREE_TEXT", QuestionType.FREE_TEXT.name());
		assertEquals("MCQ_CHOOSE_ONE", QuestionType.MCQ_CHOOSE_ONE.name());
		assertEquals("MCQ_CHOOSE_MULTIPLE", QuestionType.MCQ_CHOOSE_MULTIPLE.name());
	}

}
