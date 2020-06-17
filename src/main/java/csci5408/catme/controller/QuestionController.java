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
import csci5408.catme.domain.QuestionOptions;
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
		List<Question> questions = questionService.questionsByUser(userId, "sort-cd-desc");

		model.addAttribute("allquestions", questions);
		model.addAttribute("sortStatus", true);

		return "question-manager";
	}

	@GetMapping("/create-question")
	public String createQuestion(Model model) {
		model.addAttribute("createQuestion", new Question());
		List<QuestionType> qTypes = questionService.getQuestionTypes();
		model.addAttribute("qtypes", qTypes);
		model.addAttribute("step1", true);
		return "create-question";
	}

	@PostMapping("/createQuestion")
	public String createQuestionPost(@ModelAttribute Question createQuestion,
			@RequestParam(value = "questionType") String questionType, Model model) {

		Long userId = auth.getLoggedInUser().getId();
		LocalDateTime creationDateTime = LocalDateTime.now();
		Long typeId = questionService.getQuestionTypeIdByName(questionType);

		createQuestion.setQuestionTypeId(typeId);
		createQuestion.setUserId(userId);
		createQuestion.setCreationDate(creationDateTime);

		QuestionOptions option = new QuestionOptions();
		// createQuestion.addOption(option);

		if (questionType.compareTo(QuestionType.MCQ_CHOOSE_ONE.name()) == 0) {

			model.addAttribute("createQuestion", createQuestion);
			model.addAttribute("type", QuestionType.valueOf(questionType).label);
			model.addAttribute("createQuestionList", createQuestion.getQuestionOptions());
			model.addAttribute("step2", true);
			model.addAttribute("step1", false);
			return "create-question";
		}

		Question question = questionService.addQuestion(createQuestion);

		return "redirect:/question-manager";
	}

	@PostMapping("/createQuestion/add-new-option")
	public String createQuestionOptionPost(@ModelAttribute("createQuestion") Question createQuestion,
			@RequestParam(value = "optionText") String optionText, @RequestParam(value = "storedAs") Long storedAs,
			Model model) {

		// List<QuestionOptions> listOptions = new ArrayList<QuestionOptions>();
		Question question = new Question();
		QuestionOptions option = new QuestionOptions();
		question.setQuestionOptions(createQuestion.getQuestionOptions());
		option.setOptionText(optionText);
		option.setStoredAs(storedAs);
		question.addOption(option);
		// createQuestion.getQuestionOptions().addAll(question.getQuestionOptions());

		System.out.println("-------");
		for (QuestionOptions questionOptions : createQuestion.getQuestionOptions()) {
			System.out.println(questionOptions.getOptionText());
		}
		// question.setQuestionOptions(createQuestion.getQuestionOptions());

		model.addAttribute("createQuestion", question);
		model.addAttribute("createQuestionList", question.getQuestionOptions());
		model.addAttribute("step2", true);
		model.addAttribute("step1", false);

		return "create-question";
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

	@GetMapping("/questions/sort-cd-asc")
	public String sortByCreationDateASC(Model model) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionService.questionsByUser(userId, "sort-cd-asc");

		model.addAttribute("sortedquestions", questions);
		model.addAttribute("sortStatus", true);
		return "sort-manager";
	}

	@GetMapping("/questions/sort-title-asc")
	public String sortByTitleASC(Model model) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionService.questionsByUser(userId, "sort-title-asc");

		model.addAttribute("sortedquestions", questions);
		model.addAttribute("sortStatus", true);
		return "sort-manager";
	}

	@GetMapping("/questions/sort-cd-desc")
	public String sortByCreationDateDESC(Model model) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionService.questionsByUser(userId, "sort-cd-desc");

		model.addAttribute("sortStatus", true);
		model.addAttribute("sortedquestions", questions);
		return "sort-manager";
	}

	@GetMapping("/questions/sort-title-desc")
	public String sortByTitleDESC(Model model) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionService.questionsByUser(userId, "sort-title-desc");

		model.addAttribute("sortedquestions", questions);
		model.addAttribute("sortStatus", true);
		return "sort-manager";
	}

	@GetMapping("/sort-manager")
	public String sortManager() {
		return "sort-manager";
	}

}
