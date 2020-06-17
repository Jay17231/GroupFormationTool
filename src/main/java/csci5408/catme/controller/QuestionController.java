/**
 * 
 */
package csci5408.catme.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csci5408.catme.dao.impl.QuestionDaoImpl;
import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionType;
import csci5408.catme.service.impl.AuthenticationServiceImpl;
import csci5408.catme.service.impl.QuestionServiceImpl;

/**
 * @author Jay Gajjar
 */

@Controller
@Validated
public class QuestionController {

	AuthenticationServiceImpl auth;
	QuestionDaoImpl questionDao;
	QuestionServiceImpl questionService;

	public QuestionController(AuthenticationServiceImpl auth, QuestionDaoImpl questionDao,
			QuestionServiceImpl questionService) {
		this.auth = auth;
		this.questionDao = questionDao;
		this.questionService = questionService;
	}

	@GetMapping("/question-manager")
	public String allQuestions(Model model) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionDao.getQuestionsByUser(userId);

		model.addAttribute("allquestions", questions);
		return "question-manager";
	}

	@GetMapping("/create-question")
	public String createQuestion(Model model) {
		model.addAttribute("createQuestion", new Question());
		List<QuestionType> qTypes = questionService.getQuestionTypes();
		model.addAttribute("qtypes", qTypes);
		return "create-question";
	}

	@PostMapping("/createQuestion")
	public String createQuestionPost(@ModelAttribute Question createQuestion,
			@RequestParam(value = "questionType") String questionType, Model model) {

		Long userId = auth.getLoggedInUser().getId();
		LocalDateTime creationDateTime = LocalDateTime.now();
		Long typeId = questionDao.getTypeIdByName(questionType);

		model.addAttribute("thisid", questionType);

		createQuestion.setQuestionTypeId(typeId);
		createQuestion.setUserId(userId);
		createQuestion.setCreationDate(creationDateTime);
		Question question = questionDao.save(createQuestion);
		return "redirect:/question-manager";
	}

	@GetMapping("/deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable("id") String id) {
		Question question = new Question();
		int questionId = Integer.parseInt(id);
		question.setId(Long.valueOf(questionId));
		questionDao.delete(question);
		return "redirect:/question-manager";
	}

	@PostMapping("/deleteQuestion/{id}")
	public String deleteQuestionPost() {

		return "redirect:/question-manager";
	}

}
