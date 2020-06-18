/**
 * 
 */
package csci5408.catme.controller;

import csci5408.catme.domain.Question;
import csci5408.catme.domain.QuestionOptions;
import csci5408.catme.domain.QuestionType;
import csci5408.catme.service.IQuestionService;
import csci5408.catme.service.impl.AuthenticationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

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
		List<Question> questions = questionService.questionsByUser(userId, "", "");

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
	public ModelAndView createQuestionPost(@ModelAttribute Question createQuestion,
										   @RequestParam(value = "questionType") String questionType) {

		Long userId = auth.getLoggedInUser().getId();
		LocalDateTime creationDateTime = LocalDateTime.now();
		Long typeId = questionService.getQuestionTypeIdByName(questionType);

		createQuestion.setQuestionTypeId(typeId);
		createQuestion.setUserId(userId);
		createQuestion.setCreationDate(creationDateTime);

		QuestionOptions option = new QuestionOptions();
		// createQuestion.addOption(option);

		if (questionType.equalsIgnoreCase(QuestionType.MCQ_CHOOSE_ONE.name())) {
			Question question = questionService.addQuestion(createQuestion);
			return new ModelAndView("redirect:/question/" + question.getId() + "/edit");
		}

		Question question = questionService.addQuestion(createQuestion);

		return new ModelAndView("redirect:/question-manager");
	}

	@GetMapping("/question/{questionId}/edit")
	public ModelAndView createQuestionOptionPost(
			@PathVariable Long questionId
	) {
		Question question = questionService.getQuestionById(questionId);
		if (question == null) {
			throw new RuntimeException("Question Not found.");
		}
		ModelAndView modelAndView = new ModelAndView("add-options");
		modelAndView.addObject("question", question);
		return modelAndView;
	}

	@PostMapping("/question/{questionId}/edit/add")
	public ModelAndView addOptions(
			@PathVariable Long questionId,
			@RequestParam("optionText") String optionText,
			@RequestParam("storedAs") Long storedAs
	) {
		QuestionOptions options = new QuestionOptions();
		options.setStoredAs(storedAs);
		options.setOptionText(optionText);
		questionService.addOption(questionId, options);

		String redirectUrl = "/question/" + questionId + "/edit";
		ModelAndView modelAndView = new ModelAndView("redirect:" + redirectUrl);
		return modelAndView;
	}


	@GetMapping("/deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable("id") String id) {
		Question question = new Question();
		int questionId = Integer.parseInt(id);
		question.setId(Long.valueOf(questionId));
		questionService.removeQuestion(question);
		return "redirect:/question-manager";
	}

	/**
	 * Sorting feature
	 *
	 * @param model
	 * @param sortType
	 * @param sortField
	 * @return
	 * @author Jay
	 */
	@GetMapping("/questions")
	public String sortByCreationDateASC(
			Model model,
			@RequestParam(defaultValue = "DESC") String sortType,
			@RequestParam(defaultValue = "creation_date") String sortField
	) {

		Long userId = auth.getLoggedInUser().getId();
		List<Question> questions = questionService.questionsByUser(userId, sortType, sortField);

		model.addAttribute("sortedquestions", questions);
		model.addAttribute("sortStatus", true);
		return "sort-manager";
	}

}
