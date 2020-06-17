package csci5408.catme.domain;

public enum QuestionType {
	FREE_TEXT("Free Text"), MCQ_CHOOSE_ONE("Multiple Choice, Choose One"),
	MCQ_CHOOSE_MULTIPLE("Multiple Choice, Choose Multiple");

	public final String label;

	private QuestionType(String label) {
		this.label = label;
	}
}
