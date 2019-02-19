package com.test.randprime.primechecker.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PrimeNumberValidatorTest {
	private PrimeNumberValidator validator = new PrimeNumberValidator();

	@Test
	public void testForPrimes() {
		int[] primes = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 1000003, -1000003, 2147483647 };
		for (int p : primes) {
			assertTrue(validator.check(p).isPrime());
		}
	}

	@Test
	public void testForNonPrimes() {
		int[] nonPrimes = new int[] { 0, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 100, 1000001, 10000000,
				-10000000 };
		for (int np : nonPrimes) {
			assertFalse(validator.check(np).isPrime());
		}
	}
}
