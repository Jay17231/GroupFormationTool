package csci5408.catme.dto;

public class PasswordPolicyRule {
	
	boolean isMinLength;
	boolean isMaxLength;
	boolean isMinUpperCase;
	boolean isMinLowerCase;
	boolean isMinSymbol;
	boolean isBlockChar;
	
	
	public boolean isMinLength() {
		return isMinLength;
	}
	public void setMinLength(boolean isMinLength) {
		this.isMinLength = isMinLength;
	}
	public boolean isMaxLength() {
		return isMaxLength;
	}
	public void setMaxLength(boolean isMaxLength) {
		this.isMaxLength = isMaxLength;
	}
	public boolean isMinUpperCase() {
		return isMinUpperCase;
	}
	public void setMinUpperCase(boolean isMinUpperCase) {
		this.isMinUpperCase = isMinUpperCase;
	}
	public boolean isMinLowerCase() {
		return isMinLowerCase;
	}
	
	public void setMinLowerCase(boolean isMinLowerCase) {
		this.isMinLowerCase = isMinLowerCase;
	}
	
	public boolean isMinSymbol() {
		return isMinSymbol;
	}
	public void setMinSymbol(boolean isMinSymbol) {
		this.isMinSymbol = isMinSymbol;
	}
	public boolean isBlockChar() {
		return isBlockChar;
	}
	public void setBlockChar(boolean isBlockChar) {
		this.isBlockChar = isBlockChar;
	}

}
