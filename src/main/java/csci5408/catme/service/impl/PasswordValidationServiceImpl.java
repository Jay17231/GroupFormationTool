package csci5408.catme.service.impl;

import org.springframework.stereotype.Service;

import csci5408.catme.dao.IPasswordPolicyDao;

import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.dto.PasswordPolicy;
import csci5408.catme.dto.PasswordValidationResult;
import csci5408.catme.service.IPasswordValidationService;

@Service
public class PasswordValidationServiceImpl implements IPasswordValidationService{
	
	
	final IPasswordPolicyDao passwordPolicyDao;
	PasswordPolicy passwordPolicy = new PasswordPolicy();
	
	
	PasswordValidationServiceImpl(PasswordPolicyDaoImpl passwordPolicyDao)
	{
		this.passwordPolicyDao = passwordPolicyDao;		
	}
	

	@Override
	public PasswordValidationResult validatePassword(String password) {
		PasswordValidationResult result = new PasswordValidationResult();
		
		passwordPolicy = passwordPolicyDao.find();
		
		if(passwordPolicy.getMinLength() != -1){
			if(!checkMinLength(password, passwordPolicy)) {
				result.setMinLength(false);
			}	
		}
		if(passwordPolicy.getMaxLength() != -1){
			if(!checkMaxLength(password, passwordPolicy)) {
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
		if(passwordPolicy.getMinSymbol() != -1){
			if(!checkMinSymbol(password, passwordPolicy)) {
				
				result.setMinSymbol(false);
			}	
		}
		if(passwordPolicy.getBlockChar() != "-1"){
			if(!checkBlockSymbol(password, passwordPolicy)) {
				
				result.setBlockChar(false);
			}	
		}
		return result;
	}
	
	
	
	public boolean checkMinLength(String password, PasswordPolicy passwordPolicy)
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
		int symbol=0;
		for (int i=0; i < password.length();i++) {
			if( (int)password.charAt(i) >= 33 && (int)password.charAt(i) <= 64 ) {
				symbol++;
			}
		}
		return checkMinimum(symbol , passwordPloicy.getMinSymbol());
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
	
	public boolean checkMinimum(int count, int minCount)
	{
		if(count >= minCount) {
			return true;
		}else {
			
			return false;
		}
	}


	
	
	
	
	
	

}
