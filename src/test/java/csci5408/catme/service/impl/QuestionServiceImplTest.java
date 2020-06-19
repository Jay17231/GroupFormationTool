package csci5408.catme.service.impl;

import csci5408.catme.dao.IQuestionDao;
import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionOptions;
import csci5408.catme.sql.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceImplTest {

	final IQuestionDao questionDao;

	QuestionServiceImpl questionServiceImpl;

	public QuestionServiceImplTest() {

		this.questionDao = mock(IQuestionDao.class);
		this.questionServiceImpl = mock(QuestionServiceImpl.class);

	}

	@BeforeEach
	public void setup() {
		questionServiceImpl = new QuestionServiceImpl(questionDao);
	}

	@Test
	public void getQuestionTypesTest() {
		assertNotNull(questionServiceImpl.getQuestionTypes());
	}

	@Test
	public void getQuestionTypeIdByNameTest() {
		when(questionDao.getTypeIdByName("MCQ")).thenReturn(1L);
		assertEquals(questionServiceImpl.getQuestionTypeIdByName("MCQ"), 1L);
	}

	@Test
	public void addQuestionTest() {
		Question question = new Question();
		when(questionDao.save(question)).thenReturn(question);
		assertEquals(questionServiceImpl.addQuestion(question), question);
	}

	@Test
	public void addOptionTest() {
		Long questionId = 1L;
		QuestionOptions options = new QuestionOptions();
		when(questionDao.saveOption(options)).thenReturn(options);
		assertEquals(options, questionServiceImpl.addOption(questionId, options));
	}

	@Test
	public void removeQuestionTest() {
		Question question = new Question();
		when(questionDao.delete(question)).thenReturn(true);
		assertTrue(questionServiceImpl.removeQuestion(question));
	}

	@Test
	public void getQuestionByIdTest() {
		Long id = 1L;
		when(questionDao.findById(id)).thenReturn(Optional.empty());
		assertNull(questionServiceImpl.getQuestionById(id));
	}

	@Test
	public void questionsByUserTest() {
		when(questionDao.getQuestionsByUser(1L, "questionTitle", Sort.ASC))
				.thenReturn(new ArrayList<>());
		assertNotNull(questionServiceImpl.questionsByUser(1L, "ASC", "questionTitle"));
	}

}
