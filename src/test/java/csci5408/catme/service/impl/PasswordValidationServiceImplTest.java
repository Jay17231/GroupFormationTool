package csci5408.catme.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.dto.PasswordPolicy;


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
	public void checkMinLength_True()
	{
		String password = "krupa";
		PasswordPolicy passwordPolicy = new PasswordPolicy() ;
		passwordPolicy.setMinLength(3);
		when(passwordPolicyDao.find()).thenReturn(passwordPolicy);
		System.out.println("test ln"+passwordPolicy.getMinLength());
		assertTrue(passwordValidationService.checkMinLength(password, passwordPolicy));
	}
	
	@Test
	public void checkMinLength_False()
	{
		String password = "k";
		PasswordPolicy passwordPolicy = new PasswordPolicy() ;
		passwordPolicy.setMinLength(3);
		when(passwordPolicyDao.find()).thenReturn(passwordPolicy);
		System.out.println("test ln"+passwordPolicy.getMinLength());
		assertFalse(passwordValidationService.checkMinLength(password, passwordPolicy));
		
	}
	

}
