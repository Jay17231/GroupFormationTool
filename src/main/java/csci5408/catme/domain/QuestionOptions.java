package csci5408.catme.domain;

public class QuestionOptions {

	private Long id;

	private Long storedAs;

	private Long questionId;

	private String optionText;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStoredAs() {
		return storedAs;
	}

	public void setStoredAs(Long storedAs) {
		this.storedAs = storedAs;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
}
