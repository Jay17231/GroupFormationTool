package csci5408.catme.service.impl;

import csci5408.catme.dao.IPasswordHistoryDao;
import csci5408.catme.dao.IPasswordPolicyDao;
import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.domain.PasswordHistory;
import csci5408.catme.dto.PasswordPolicy;
import csci5408.catme.dto.PasswordValidationResult;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IPasswordValidationService;
import csci5408.catme.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordValidationServiceImpl implements IPasswordValidationService {


	final IPasswordPolicyDao passwordPolicyDao;
	final IUserService userService;
	final IPasswordHistoryDao passwordHistoryDao;
	final BCryptPasswordEncoder bCryptPasswordEncoder;

	PasswordValidationServiceImpl(
			PasswordPolicyDaoImpl passwordPolicyDao,
			IUserService userService,
			IPasswordHistoryDao passwordHistoryDao,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.passwordPolicyDao = passwordPolicyDao;
		this.userService = userService;
		this.passwordHistoryDao = passwordHistoryDao;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	@Override
	public PasswordValidationResult validatePassword(String password) {
		PasswordValidationResult result = new PasswordValidationResult();

		PasswordPolicy passwordPolicy = passwordPolicyDao.find();

		if (passwordPolicy.getMinLength() != -1) {
			if (!checkMinLength(password, passwordPolicy)) {
				result.setMinLength(false);
			}
		}
		if (passwordPolicy.getMaxLength() != -1) {
			if (!checkMaxLength(password, passwordPolicy)) {
				result.setMaxLength(false);
			}
		}
		if(passwordPolicy.getMinUpperCase() != -1){
			if(!checkMinUpperCase(password, passwordPolicy)) {

				result.setMinUpperCase(false);
			}
		}
		if(passwordPolicy.getMinLowerCase() != -1){
			if(!checkMinLowerCase(password, passwordPolicy)) {

				result.setMinLowerCase(false);
			}
		}
		if (passwordPolicy.getMinSymbol() != -1) {
			if (!checkMinSymbol(password, passwordPolicy)) {

				result.setMinSymbol(false);
			}
		}
		if (!passwordPolicy.getBlockChar().equals("-1")) {
			if (!checkBlockSymbol(password, passwordPolicy)) {

				result.setBlockChar(false);
			}
		}
		
		return result;
	}

	@Override
	public boolean isOldPassword(String email, String password) {
		PasswordPolicy passwordPolicy = passwordPolicyDao.find();
		if (passwordPolicy.getPasswordHistoryCount() <= 0) {
			return false;
		}
		UserSummary userSummary = userService.getUserByEmailId(email);
		List<PasswordHistory> lists = passwordHistoryDao.getPasswordsByUserId(
				userSummary.getId(),
				passwordPolicy.getPasswordHistoryCount()
		);
		for (PasswordHistory list : lists) {
			if (bCryptPasswordEncoder.matches(password, list.getPassword())) {
				return true;
			}
		}
		return false;
	}


	public boolean checkMinLength(String password, PasswordPolicy passwordPolicy) {
		if (password.length() >= passwordPolicy.getMinLength()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkMaxLength(String password, PasswordPolicy passwordPloicy){
		if(password.length() <= passwordPloicy.getMaxLength()){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkMinUpperCase(String password, PasswordPolicy passwordPloicy)
	{
		int upper=0;
		for (int i=0; i < password.length();i++) {
			if( Character.isUpperCase(password.charAt(i))) {
				upper++;
			}
		}
		return checkMinimum(upper, passwordPloicy.getMinUpperCase()) ;		
	}
	
	public boolean checkMinLowerCase(String password, PasswordPolicy passwordPloicy)
	{
		int lower=0;
		for (int i=0; i < password.length();i++) {
			if( Character.isLowerCase(password.charAt(i))) {
				lower++;
			}
		}
		return checkMinimum(lower , passwordPloicy.getMinLowerCase());
	}
	
	public boolean checkMinSymbol(String password, PasswordPolicy passwordPloicy)
	{
		int symbol = 0;
		for (int i = 0; i < password.length(); i++) {
			if ((int) password.charAt(i) >= 33 && (int) password.charAt(i) <= 64) {
				symbol++;
			}
		}
		return checkMinimum(symbol, passwordPloicy.getMinSymbol());
	}


	public boolean checkBlockSymbol(String password, PasswordPolicy passwordPolicy) {
		String blockSymbol = passwordPolicy.getBlockChar();
		for (int i = 0; i < password.length(); i++) {
			for (int j = 0; j < blockSymbol.length(); j++) {
				if (password.charAt(i) == blockSymbol.charAt(j)) {
					return false;
				}
			}
		}
		return true;	
	}
	
	public boolean checkMinimum(int count, int minCount)
	{
		if(count >= minCount) {
			return true;
		}else {
			
			return false;
		}
	}
}
