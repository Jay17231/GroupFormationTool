package csci5408.catme.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {

	private Long id;

	private Long userId;

	private Long questionTypeId;

	private String questionTitle;

	private String questionText;

	private LocalDateTime creationDate;

	private List<QuestionOptions> questionOptions = new ArrayList<QuestionOptions>();

	private QuestionType type;

	public Question() {
	}

	;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public void addOption(QuestionOptions questionOption) {
		this.questionOptions.add(questionOption);
	}

	public List<QuestionOptions> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(List<QuestionOptions> questionOptions) {
		this.questionOptions = questionOptions;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}
}
