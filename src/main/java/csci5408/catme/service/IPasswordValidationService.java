/**
 * 
 */
package csci5408.catme.service;

import csci5408.catme.dto.PasswordPolicyRule;

/**
 * @author krupa
 *
 */
public interface IPasswordValidationService {

	PasswordPolicyRule validatePassword(String password);
	boolean isValidated();
}
