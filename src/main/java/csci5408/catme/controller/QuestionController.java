/**
 * 
 */
package csci5408.catme.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionType;
import csci5408.catme.service.IQuestionService;
import csci5408.catme.service.impl.AuthenticationServiceImpl;

/**
 * @author Jay Gajjar
 */

@Controller
public class QuestionController {

	AuthenticationServiceImpl auth;
	IQuestionService questionService;

	public QuestionController(AuthenticationServiceImpl auth, IQuestionService questionService) {
		this.auth = auth;
		this.questionService = questionService;
	}

	@GetMapping("/question-manager")
	public String allQuestions(Model model) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionService.questionsByUser(userId);

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
		Long typeId = questionService.getQuestionTypeIdByName(questionType);

		model.addAttribute("thisid", questionType);

		createQuestion.setQuestionTypeId(typeId);
		createQuestion.setUserId(userId);
		createQuestion.setCreationDate(creationDateTime);
		Question question = questionService.addQuestion(createQuestion);
		return "redirect:/question-manager";
	}

	@GetMapping("/deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable("id") String id) {
		Question question = new Question();
		int questionId = Integer.parseInt(id);
		question.setId(Long.valueOf(questionId));
		questionService.removeQuestion(question);
		return "redirect:/question-manager";
	}

	@PostMapping("/deleteQuestion/{id}")
	public String deleteQuestionPost() {

		return "redirect:/question-manager";
	}

}
