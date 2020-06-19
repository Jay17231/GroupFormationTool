package csci5408.catme.service.impl;

import csci5408.catme.dao.IPasswordHistoryDao;
import csci5408.catme.dao.impl.PasswordPolicyDaoImpl;
import csci5408.catme.domain.PasswordHistory;
import csci5408.catme.dto.PasswordPolicy;
import csci5408.catme.dto.PasswordValidationResult;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PasswordValidationServiceImplTest {

	private final PasswordPolicyDaoImpl passwordPolicyDao;
	private PasswordValidationServiceImpl passwordValidationService;
	final private IUserService userService;
	final private IPasswordHistoryDao passwordHistoryDao;
	final private BCryptPasswordEncoder bCryptPasswordEncoder;


	public PasswordValidationServiceImplTest() {
		this.userService = mock(IUserService.class);
		this.passwordPolicyDao = mock(PasswordPolicyDaoImpl.class);
		this.passwordHistoryDao = mock(IPasswordHistoryDao.class);
		this.bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
	}

	@BeforeEach
	public void setup() {
		passwordValidationService = new PasswordValidationServiceImpl(
				passwordPolicyDao,
				userService,
				passwordHistoryDao,
				bCryptPasswordEncoder
		);
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
		String password = "K@patel";
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


	@Test
	public void isOldPasswordTest_True() {
		String email = "a@g.c";
		Long userId = 1L;
		UserSummary summary = new UserSummary();
		summary.setEmailId(email);
		summary.setId(userId);
		PasswordHistory passwordHistory = new PasswordHistory(1L, "ABC", null, 1L);
		ArrayList<PasswordHistory> histories = new ArrayList<>();
		histories.add(passwordHistory);
		PasswordPolicy policy = new PasswordPolicy();
		policy.setPasswordHistoryCount(1L);

		when(userService.getUserByEmailId(email)).thenReturn(summary);
		when(passwordHistoryDao.getPasswordsByUserId(userId, 1L)).thenReturn(histories);
		when(bCryptPasswordEncoder.matches("ABC", "ABC")).thenReturn(true);
		when(passwordPolicyDao.find()).thenReturn(policy);

		assertTrue(passwordValidationService.isOldPassword(email, "ABC"));
	}

	@Test
	public void isOldPasswordTest_False() {
		String email = "a@g.c";
		Long userId = 1L;
		UserSummary summary = new UserSummary();
		summary.setEmailId(email);
		summary.setId(userId);
		PasswordHistory passwordHistory = new PasswordHistory(1L, "ABC", null, 1L);
		ArrayList<PasswordHistory> histories = new ArrayList<>();
		histories.add(passwordHistory);
		PasswordPolicy policy = new PasswordPolicy();
		policy.setPasswordHistoryCount(1L);


		when(userService.getUserByEmailId(email)).thenReturn(summary);
		when(passwordHistoryDao.getPasswordsByUserId(userId, 1L)).thenReturn(histories);
		when(bCryptPasswordEncoder.matches("ABC", "ABC")).thenReturn(false);
		when(passwordPolicyDao.find()).thenReturn(policy);

		assertFalse(passwordValidationService.isOldPassword(email, "ABC"));
	}

	@Test
	public void isOldPasswordTest_Disabled() {
		String email = "a@g.c";
		String password = "123";

		PasswordPolicy policy = new PasswordPolicy();
		policy.setPasswordHistoryCount(0L);

		when(passwordPolicyDao.find()).thenReturn(policy);
		assertFalse(passwordValidationService.isOldPassword(email, password));
	}

}
