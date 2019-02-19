package com.test.randprime.primechecker.core;

import java.util.HashSet;
import java.util.Set;

import com.test.randprime.commons.api.PrimeTestResult;

public class PrimeNumberValidator {
	// all the values <= 100000000 will requires max
	// sqrt(100000000)/2=5000 iterations (even values checking is fast)
	private static int INITIAL_VALUE = 100000000 + 1;
	private static int MAX_RANGE = 1024 * 100 * 2;
	// Once populated by the static initializer, these sets are
	// read-only.
	private static final Set<Integer> PRIME_CACHE = new HashSet<>();
	private static final Set<Integer> NON_PRIME_CACHE = new HashSet<>();

	static {
		// caching odd values only, since checking
		// even values is fast
		for (int i = INITIAL_VALUE; i <= INITIAL_VALUE + MAX_RANGE; i += 2) {
			if (checkIsPrime(i)) {
				PRIME_CACHE.add(i);
			} else {
				NON_PRIME_CACHE.add(i);
			}
		}
	}

	/**
	 * Checks if the "value" provided is prime or not.
	 */
	public PrimeTestResult check(int value) {
		int positiveValue = Math.abs(value);

		if (PRIME_CACHE.contains(positiveValue)) {
			return new PrimeTestResult(value, true);
		}

		if (NON_PRIME_CACHE.contains(positiveValue)) {
			return new PrimeTestResult(value, false);
		}

		if (checkIsPrime(positiveValue)) {
			return new PrimeTestResult(value, true);
		} else {
			return new PrimeTestResult(value, false);
		}
	}

	private static boolean checkIsPrime(int positiveValue) {
		if (positiveValue < 2) {
			return false;
		}

		if (positiveValue == 2) {
			return true;
		}

		if ((positiveValue & 1) == 0) {
			// even value, thus not prime;
			return false;
		}

		double sqrtValue = Math.sqrt(positiveValue);
		// checking only the for the odd values, since checking
		// even values is fast
		for (int i = 3; i <= sqrtValue; i += 2) {
			if (positiveValue % i == 0)
				return false;
		}
		return true;
	}
}
