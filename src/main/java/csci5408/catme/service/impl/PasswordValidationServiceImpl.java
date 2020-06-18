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
		if(passwordPolicy.getBlockChar() != null){
			if(!checkBlockSymbol(password, passwordPolicy)) {
				
				result.setBlockChar(false);
			}	
		}
		return result;
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
