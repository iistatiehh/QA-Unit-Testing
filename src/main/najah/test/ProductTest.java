package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
	import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Disabled;


import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

	private Product product;

	@BeforeAll
	static void initAll() {
		System.out.println("Setup complete - Before All");
	}

	@BeforeEach
	void init() {
		product = new Product("Laptop", 1000);
		System.out.println("Setup complete - Before Each");
	}

	@Test
	@DisplayName("Valid Discount Application")
	void testApplyValidDiscount() {
		product.applyDiscount(20);
		assertEquals(800, product.getFinalPrice(), "Final price calculation failed");
		assertEquals(20, product.getDiscount(), "Discount not set correctly");
	}

	@Test
	@DisplayName("Invalid Discount")
	void testApplyInvalidDiscount() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(60));
		String exceptionMessage = exception.getMessage();
		System.out.println("Exception message: " + exceptionMessage); // Print exception message
		assertEquals("Invalid discount", exceptionMessage, "Exception message mismatch");
	}


	@ParameterizedTest
	@DisplayName("Parameterized Test for Multiple Discount Values")
	@CsvSource({"10,900", "20,800", "30,700"})
	void testDiscountValues(double discount, double expectedPrice) {
		product.applyDiscount(discount);
		assertEquals(expectedPrice, product.getFinalPrice(), "Incorrect final price");
	}

	@Test
	@Timeout(2)
	@DisplayName("Timeout Test - Ensuring Fast Execution")
	void testTimeout() {
		assertDoesNotThrow(() -> product.applyDiscount(10));
	}

	@Test
	void testFailingDiscount() {
		// Trying to apply a discount greater than 50% (invalid discount)
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			product.applyDiscount(55);});
		// Verify that the exception message is exactly as expected
		assertEquals("Invalid discount", exception.getMessage(), "Exception message mismatch");
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
class ConcurrentProductTest {
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
@SelectClasses({ProductTest.class, ConcurrentProductTest.class})
class ProductTestSuite {
}
