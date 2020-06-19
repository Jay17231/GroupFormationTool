/**
 * 
 */
package csci5408.catme.service;

import csci5408.catme.dto.PasswordValidationResult;

/**
 * @author krupa
 */
public interface IPasswordValidationService {

	PasswordValidationResult validatePassword(String password);

	boolean isOldPassword(String email, String password);

}
