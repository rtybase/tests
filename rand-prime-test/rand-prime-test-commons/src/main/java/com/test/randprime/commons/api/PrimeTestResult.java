package com.test.randprime.commons.api;

public class PrimeTestResult {

	private final int numberTested;
	private final boolean prime;

	public PrimeTestResult(int numberTested, boolean isPrime) {
		this.numberTested = numberTested;
		this.prime = isPrime;
	}

	public int getNumberTested() {
		return numberTested;
	}

	public boolean isPrime() {
		return prime;
	}

	@Override
	public String toString() {
		return String.format("Number '%d' %s prime.",
				numberTested,
				prime ? "is" : "is not");
	}

	@Override
	public int hashCode() {
		return numberTested;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		PrimeTestResult other = (PrimeTestResult) obj;
		return numberTested == other.numberTested;
	}
}
