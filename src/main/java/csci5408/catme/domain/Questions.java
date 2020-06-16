package csci5408.catme.domain;

import java.sql.Date;

public class Questions {

	private Long id;

	private String questionTitle;

	private String questionText;

	private Date creationDate;

	public Questions(Long id, String questionTitle, String questionText, Date creationDate) {

		this.creationDate = creationDate;
		this.id = id;
		this.questionText = questionText;
		this.questionTitle = questionTitle;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
