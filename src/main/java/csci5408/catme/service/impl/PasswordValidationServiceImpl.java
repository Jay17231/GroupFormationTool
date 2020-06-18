package csci5408.catme.service.impl;

import csci5408.catme.dao.IPasswordPolicyDao;

import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.dto.PasswordPolicy;
import csci5408.catme.dto.PasswordPolicyRule;
import csci5408.catme.service.IPasswordValidationService;

public class PasswordValidationServiceImpl implements IPasswordValidationService{
	
	
	final IPasswordPolicyDao passwordPolicyDao;
	PasswordPolicy passwordPolicy = new PasswordPolicy();
	PasswordPolicyRule failedRule = new PasswordPolicyRule();
	
	
	
	PasswordValidationServiceImpl(PasswordPolicyDaoImpl passwordPolicyDao)
	{
		
		this.passwordPolicyDao = passwordPolicyDao;	
	}
	

	@Override
	public boolean validatePassword() {
		
		passwordPolicy = passwordPolicyDao.find();
		return false;
	}
	
	
	
	public boolean checkMinLength(String password, PasswordPolicy passwordPloicy)
	{
		if(password.length() >= passwordPolicy.getMinLength()){
			return true;
		}
		else{
			failedRule.setMinLength(false);
			return false;
		}
		
	}
	
	public boolean checkMaxLength(String password, PasswordPolicy passwordPloicy){
		if(password.length() <= passwordPloicy.getMaxLength()){
			return true;
		}
		else {
			failedRule.setMaxLength(false);
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
		
		if(upper> passwordPloicy.getMinUpperCase()) {
			return true;
		}else {
			failedRule.setMinUpperCase(false);
		return false;
		}
	}
	
	public boolean checkMinLowerCase(String password, PasswordPolicy passwordPloicy)
	{
		int upper=0;
		for (int i=0; i < password.length();i++) {
			if( Character.isLowerCase(password.charAt(i))) {
				upper++;
			}
		}
		
		if(upper> passwordPloicy.getMinUpperCase()) {
			return true;
		}else {
			failedRule.setMinLowerCase(false);
		return false;
		}
	}
	
	public boolean checkMinSymbol(String password, PasswordPolicy passwordPloicy)
	{
		int symbol=0;
		for (int i=0; i < password.length();i++) {
			if( (int)password.charAt(i) >= 33 && (int)password.charAt(i) < 48 || (int)password.charAt(i) >= 58 && (int)password.charAt(i) < 64 ) {
				symbol++;
			}
		}
		
		if(symbol> passwordPloicy.getMinUpperCase()) {
			return true;
		}else {
			failedRule.setMinSymbol(false);
			return false;
		}
	}
	
	
	
	
	
	

}
