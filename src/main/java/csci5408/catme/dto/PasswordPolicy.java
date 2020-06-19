/**
 * 
 */
package csci5408.catme.dto;

/**
 * @author krupa
 *
 */
public class PasswordPolicy {
	
	int minLength;
	int maxLength;
	int minUpperCase;
	int minLowerCase;
	int minSymbol;
	Long passwordHistoryCount;
	String blockChar;
	
	
	
	
	public Long getPasswordHistoryCount() {
		return passwordHistoryCount;
	}
	public void setPasswordHistoryCount(Long passwordHistoryCount) {
		this.passwordHistoryCount = passwordHistoryCount;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public int getMinUpperCase() {
		return minUpperCase;
	}
	public void setMinUpperCase(int minUpperCase) {
		this.minUpperCase = minUpperCase;
	}
	public int getMinLowerCase() {
		return minLowerCase;
	}
	public void setMinLowerCase(int minLowerCase) {
		this.minLowerCase = minLowerCase;
	}
	public int getMinSymbol() {
		return minSymbol;
	}
	public void setMinSymbol(int minSymbol) {
		this.minSymbol = minSymbol;
	}
	public String getBlockChar() {
		if(blockChar.isEmpty())
		{
			return "-1";
		}
		return blockChar;
	}
	public void setBlockChar(String blockChar) {
		this.blockChar = blockChar;
	}
	
	
	
	
	

}
