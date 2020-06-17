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
	
	public boolean checkMaxLength(String password, PasswordPolicy passwordPloicy)
	{
		if(password.length() <= passwordPloicy.getMaxLength())
		{
			return true;
		}
		else {
			failedRule.setMaxLength(false);
			return false;
		}
		
		
	}
	
	

}
