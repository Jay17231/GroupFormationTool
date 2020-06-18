package csci5408.catme.service.impl;

import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.dto.PasswordPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import csci5408.catme.dto.PasswordValidationResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class PasswordValidationServiceImplTest {
	
	private PasswordPolicyDaoImpl passwordPolicyDao;
	private PasswordValidationServiceImpl passwordValidationService;
	
	
	
	public PasswordValidationServiceImplTest() {
		this.passwordPolicyDao = mock(PasswordPolicyDaoImpl.class);
	}
	
	@BeforeEach
	public void setup()
	{
		passwordValidationService = new PasswordValidationServiceImpl(passwordPolicyDao); 
	}
	
	@Test
	public void checkMinimumTest_True() {
		int count = 5;
		int minCount = 3;
		assertTrue(passwordValidationService.checkMinimum(count, minCount));
		
	}

	@Test
	public void checkMinimumTest_False() {
		int count = 5;
		int minCount = 7;
		assertFalse(passwordValidationService.checkMinimum(count, minCount));
	}
	
	@Test
	public void validatePassword_True()
	{
		String password = "K@patel";
		PasswordPolicy passwordPolicy = new PasswordPolicy() ;
		passwordPolicy.setMinLength(3);
		passwordPolicy.setBlockChar("!#$%^&*()_+~");
		passwordPolicy.setMaxLength(12);
		passwordPolicy.setMinUpperCase(1);
		passwordPolicy.setMinLowerCase(1);
		passwordPolicy.setMinSymbol(1);
		
		when(passwordPolicyDao.find()).thenReturn(passwordPolicy);
		PasswordValidationResult result = new PasswordValidationResult();
		result = passwordValidationService.validatePassword(password);
		assertTrue(result.isValidated());
	}
	
	@Test
	public void validatePassword_FalseMinLength()
	{
		String password = "K@patel";
		PasswordPolicy passwordPolicy = new PasswordPolicy() ;
		passwordPolicy.setMinLength(8);
		passwordPolicy.setBlockChar("!#$%^&*()_@+~");
		passwordPolicy.setMaxLength(12);
		passwordPolicy.setMinUpperCase(3);
		passwordPolicy.setMinLowerCase(6);
		passwordPolicy.setMinSymbol(4);
		
		when(passwordPolicyDao.find()).thenReturn(passwordPolicy);
		PasswordValidationResult result = new PasswordValidationResult();
		result = passwordValidationService.validatePassword(password);
		assertFalse(result.isValidated());
	}
	
	@Test
	public void validatePassword_FalseMaxLength()
	{
		String password = "K?patel";
		PasswordPolicy passwordPolicy = new PasswordPolicy() ;
		passwordPolicy.setMinLength(8);
		passwordPolicy.setBlockChar("!#$%^&*()_@+~");
		passwordPolicy.setMaxLength(5);
		passwordPolicy.setMinUpperCase(3);
		passwordPolicy.setMinLowerCase(6);
		passwordPolicy.setMinSymbol(4);
		
		when(passwordPolicyDao.find()).thenReturn(passwordPolicy);
		PasswordValidationResult result = new PasswordValidationResult();
		result = passwordValidationService.validatePassword(password);
		assertFalse(result.isValidated());
	}
	
	@Test
	public void validatePassword_TruePasswordPolicyDisable()
	{
		String password = "K?patel";
		PasswordPolicy passwordPolicy = new PasswordPolicy() ;
		passwordPolicy.setMinLength(-1);
		passwordPolicy.setBlockChar("-1");
		passwordPolicy.setMaxLength(-1);
		passwordPolicy.setMinUpperCase(-1);
		passwordPolicy.setMinLowerCase(-1);
		passwordPolicy.setMinSymbol(-1);
		when(passwordPolicyDao.find()).thenReturn(passwordPolicy);
		PasswordValidationResult result = new PasswordValidationResult();
		result = passwordValidationService.validatePassword(password);
		assertTrue(result.isValidated());
	}
	
	
	

}
