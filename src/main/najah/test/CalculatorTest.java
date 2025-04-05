package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator Tests")
public class CalculatorTest {

	private Calculator calculator;

	@BeforeAll
	static void setupBeforeAll() {
		System.out.println("Starting Calculator Tests...");
	}

	@AfterAll
	static void cleanupAfterAll() {
		System.out.println("All calculator tests done");
	}

	@BeforeEach
	void setup() {
		calculator = new Calculator();
		System.out.println("Setting up test case...");
	}

	@AfterEach
	void tearDown() {
		System.out.println("Cleaning up test case...");
	}

	@Test
	@Order(1)
	@DisplayName("Addition test")
	void testAddition() {
		assertAll("Addition tests",
				() -> assertEquals(5, calculator.add(2, 3)),
				() -> assertEquals(10, calculator.add(2, 3, 5)),
				() -> assertEquals(0, calculator.add())
		);
	}

	@ParameterizedTest
	@Order(2)
	@DisplayName("Parameterized Addition Test")
	@CsvSource({
			"1,2,3",
			"10,5,15",
			"-2,-2,-4"
	})
	void testParameterizedAddition(int a, int b, int expected) {
		assertEquals(expected, calculator.add(a, b));
	}

	@Test
	@Order(3)
	@DisplayName("Division test (true input)")
	void testDivision() {
		assertEquals(5, calculator.divide(10, 2));
	}

	@Test
	@Order(4)
	@DisplayName("Division by 0")
	void Divisionbyzero() {
		assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
	}

	@Test
	@Order(5)
	@DisplayName("Factorial Test valid")
	void testFactorial() {
		assertAll("Factorial tests",
				() -> assertEquals(120, calculator.factorial(5)),
				() -> assertEquals(1, calculator.factorial(0))
		);
	}

	@Test
	@Order(6)
	@DisplayName("Factorial Test negative")
	void testNegativeFact() {
		assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
	}

	@Test
	@Order(7)
	@Disabled("Intentionally failing test should be 50 instead of 100")
	@DisplayName("Disabled Failing Test")
	void testDisabledFailingTest() {
		assertEquals(100, calculator.add(20, 30));
	}

	@Test
	@Order(8)
	@DisplayName("Factorial Performance Test")
	void testFactorialTimeout() {
		assertTimeout(Duration.ofMillis(100), () -> calculator.factorial(10));
	}
}
