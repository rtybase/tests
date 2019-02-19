package com.test.randprime.commons.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

public class ByteBufferPackagingUtilTest {
	private static final int WRONG_BUFFER_SIZE = 10;
	private static final int TEST_INT_VALUE = 20;

	private PrimeTestResult testResult;

	@Before
	public void setup() {
		testResult = new PrimeTestResult(2, true);
	}

	@Test
	public void testConvertTestResultWithPrime() {
		ByteBuffer buffer = ByteBufferPackagingUtil.testResultToByteBuffer(testResult);
		PrimeTestResult anotherTestResult = ByteBufferPackagingUtil.byteBufferToTestResult(buffer);

		assertEquals(anotherTestResult.getNumberTested(), 2);
		assertTrue(anotherTestResult.isPrime());
	}

	@Test
	public void testConvertTestResultWithoutPrime() {
		testResult = new PrimeTestResult(4, false);
		ByteBuffer buffer = ByteBufferPackagingUtil.testResultToByteBuffer(testResult);
		PrimeTestResult anotherTestResult = ByteBufferPackagingUtil.byteBufferToTestResult(buffer);

		assertEquals(anotherTestResult.getNumberTested(), 4);
		assertFalse(anotherTestResult.isPrime());

		// testing the position reset
		anotherTestResult = ByteBufferPackagingUtil.byteBufferToTestResult(buffer);
		assertEquals(anotherTestResult.getNumberTested(), 4);
		assertFalse(anotherTestResult.isPrime());
	}

	@Test
	public void testConvertNullTestResult() {
		assertNull(ByteBufferPackagingUtil.testResultToByteBuffer(null));
	}

	@Test
	public void testConvertTestResultFromNull() {
		assertNull(ByteBufferPackagingUtil.byteBufferToTestResult(null));
	}

	@Test
	public void testConvertTestResultFromWrongSizeBuffer() {
		assertNull(ByteBufferPackagingUtil.byteBufferToTestResult(ByteBuffer.allocate(WRONG_BUFFER_SIZE)));
	}

	@Test
	public void testConvertInt() {
		ByteBuffer buffer = ByteBufferPackagingUtil.intToByteBuffer(TEST_INT_VALUE);
		int result = ByteBufferPackagingUtil.byteBufferToInt(buffer);
		assertEquals(result, TEST_INT_VALUE);

		// testing the position reset
		result = ByteBufferPackagingUtil.byteBufferToInt(buffer);
		assertEquals(result, TEST_INT_VALUE);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertIntFromNull() {
		assertNull(ByteBufferPackagingUtil.byteBufferToInt(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertIntFromWrongSizeBuffer() {
		assertNull(ByteBufferPackagingUtil.byteBufferToInt(ByteBuffer.allocate(WRONG_BUFFER_SIZE)));
	}
}
