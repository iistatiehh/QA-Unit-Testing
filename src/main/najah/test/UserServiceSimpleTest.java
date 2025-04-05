package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

	private UserService userService;

	@BeforeAll
	static void initAll() {
		System.out.println("Setup complete - Before All");
	}

	@BeforeEach
	void init() {
		userService = new UserService();
		System.out.println("Setup complete - Before Each");
	}

	@Test
	@DisplayName("Valid Email Test")
	void testValidEmail() {
		assertTrue(userService.isValidEmail("istatieh@gmail.com"), "Valid email should pass");
	}

	@Test
	@DisplayName("Invalid Email Test - Missing @")
	void testInvalidEmailMissingAt() {
		assertFalse(userService.isValidEmail("userexample.com"), "Email without '@' should fail");
	}

	@Test
	@DisplayName("Invalid Email Test - Missing gmail/hotmail(domain)")
	void testInvalidEmailMissingDomain() {
		assertFalse(userService.isValidEmail("user@com"), "Email with missing domain should fail");
	}

	@ParameterizedTest
	@DisplayName("Parameterized Test for Email Validation")
	@CsvSource({"user@example.com, true", "userexample.com, false", "user@com, false"})
	void testEmailValidation(String email, boolean expectedResult) {
		assertEquals(expectedResult, userService.isValidEmail(email), "Incorrect email validation");
	}

	@Test
	@DisplayName("Authenticate Valid User")
	void testAuthenticateValidUser() {
		assertTrue(userService.authenticate("admin", "1234"), "Valid credentials should pass");
	}

	@Test
	@DisplayName("Authenticate Invalid User")
	void testAuthenticateInvalidUser() {
		assertFalse(userService.authenticate("user", "password"), "Invalid credentials should fail");
	}

	@Test
	@Timeout(2)
	@DisplayName("Timeout Test - Authentication")
	void testTimeout() {
		assertDoesNotThrow(() -> userService.authenticate("admin", "1234"));
	}

	@Test
	@Disabled("This test fails because of incorrect password")
	void testFailingAuthentication() {
		assertFalse(userService.authenticate("admin", "wrongpassword"), "Test failed due to incorrect password");
	}

	@AfterEach
	void tearDown() {
		System.out.println("Tear down - After Each");
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("Tear down - After All");
	}
}

@Execution(ExecutionMode.CONCURRENT)
class ConcurrentUserServiceTest {
	@Test
	void concurrentTest1() {
		assertTrue(true);
	}

	@Test
	void concurrentTest2() {
		assertTrue(true);
	}
}

@Suite
@SelectClasses({UserServiceTest.class, ConcurrentUserServiceTest.class})
class UserServiceTestSuite {
}
