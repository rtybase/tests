package com.test.randprime.commons.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class PrimeTestResultTest {
	private PrimeTestResult object;

	@Before
	public void setup() {
		object = new PrimeTestResult(2, true);
	}

	@Test
	public void testObjectsAreEqual() {
		// indeed, equals looks at the integer attribute only!
		PrimeTestResult anotherObject = new PrimeTestResult(2, false);
		assertEquals(object, object);
		assertEquals(object, anotherObject);
		assertEquals(object.hashCode(), anotherObject.hashCode());
	}

	@Test
	public void testObjectsNotEqual() {
		PrimeTestResult anotherObject = new PrimeTestResult(3, true);
		assertNotEquals(object, anotherObject);
		assertNotEquals(object.hashCode(), anotherObject.hashCode());
	}

	@Test
	public void testObjectNotEqualToNull() {
		assertFalse(object.equals(null));
	}

	@Test
	public void testObjectIsOfDifferentType() {
		assertFalse(object.equals(new String()));
	}

	@Test
	public void testToStringFormat() {
		assertEquals(object.toString(), "Number '2' is prime.");
		PrimeTestResult anotherObject = new PrimeTestResult(4, false);
		assertEquals(anotherObject.toString(), "Number '4' is not prime.");
	}
}
