package csci5408.catme.service.impl;

import org.springframework.stereotype.Service;

import csci5408.catme.dao.IPasswordPolicyDao;

import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.dto.PasswordPolicy;
import csci5408.catme.dto.PasswordPolicyRule;
import csci5408.catme.service.IPasswordValidationService;

@Service
public class PasswordValidationServiceImpl implements IPasswordValidationService{
	
	
	final IPasswordPolicyDao passwordPolicyDao;
	PasswordPolicy passwordPolicy = new PasswordPolicy();
	PasswordPolicyRule failedRule = new PasswordPolicyRule();
	PasswordPolicyRule passedRule = new PasswordPolicyRule();
	boolean flag;
	
	
	
	PasswordValidationServiceImpl(PasswordPolicyDaoImpl passwordPolicyDao)
	{
		this.passwordPolicyDao = passwordPolicyDao;	
		this.flag = true;
	}
	

	@Override
	public PasswordPolicyRule validatePassword(String password) {
		
		passwordPolicy = passwordPolicyDao.find();
		
		if(passwordPolicy.getMinLength() != -1){
			if(!checkMinLength(password, passwordPolicy)) {
				flag = false;
				failedRule.setMinLength(flag);
			}	
		}
		if(passwordPolicy.getMaxLength() != -1){
			if(!checkMaxLength(password, passwordPolicy)) {
				flag = false;
				failedRule.setMaxLength(flag);
			}	
		}
		if(passwordPolicy.getMinUpperCase() != -1){
			if(!checkMinUpperCase(password, passwordPolicy)) {
				flag = false;
				failedRule.setMinUpperCase(flag);
			}	
		}
		if(passwordPolicy.getMinLowerCase() != -1){
			if(!checkMinLowerCase(password, passwordPolicy)) {
				flag = false;
				failedRule.setMinLowerCase(flag);
			}	
		}
		if(passwordPolicy.getMinSymbol() != -1){
			if(!checkMinSymbol(password, passwordPolicy)) {
				flag = false;
				failedRule.setMinSymbol(flag);
			}	
		}
		if(passwordPolicy.getBlockChar() != null){
			if(!checkBlockSymbol(password, passwordPolicy)) {
				flag = false;
				failedRule.setBlockChar(flag);
			}	
		}
		return failedRule;
	}
	
	@Override
	public boolean isValidated() {
		return flag;
	}
	
	public boolean checkMinLength(String password, PasswordPolicy passwordPloicy)
	{
		if(password.length() >= passwordPolicy.getMinLength()){
			return true;
		}
		else{
			
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
		
		if(upper> passwordPloicy.getMinUpperCase()) {
			return true;
		}else {
			
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
		
		if(upper> passwordPloicy.getMinLowerCase()) {
			return true;
		}else {
			
		return false;
		}
	}
	
	public boolean checkMinSymbol(String password, PasswordPolicy passwordPloicy)
	{
		int symbol=0;
		for (int i=0; i < password.length();i++) {
			if( (int)password.charAt(i) >= 33 && (int)password.charAt(i) < 64 ) {
				symbol++;
			}
		}
		
		if(symbol> passwordPloicy.getMinSymbol()) {
			return true;
		}else {
			
			return false;
		}
	}
	
	
	public boolean checkBlockSymbol(String password, PasswordPolicy passwordPloicy)
	{
		String blockSymbol = passwordPolicy.getBlockChar();
		for(int i=0;i<password.length();i++){
			for (int j=0;j<blockSymbol.length();j++){
				if(password.charAt(i) == blockSymbol.charAt(j)) {
					return false;
				}
			}
		}
		return true;	
	}


	
	
	
	
	
	

}
