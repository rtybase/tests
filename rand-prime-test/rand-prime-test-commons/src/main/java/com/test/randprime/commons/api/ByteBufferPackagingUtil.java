package com.test.randprime.commons.api;

import java.nio.ByteBuffer;
import java.util.Objects;

public class ByteBufferPackagingUtil {
	// 4 bytes for "int"
	public static final int INPUT_PACKET_SIZE = 4;

	// 4 bytes for "int" and one byte for "boolean"
	public static final int OUTPUT_PACKET_SIZE = 5;

	private static final byte B_TRUE = 1;
	private static final byte B_FALSE = 0;

	private ByteBufferPackagingUtil() {
		// it's a utility class with static methods only!
	}

	/**
	 * Converts an "int" to {@link ByteBuffer} ready to be sent to wire.
	 */
	public static ByteBuffer intToByteBuffer(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(INPUT_PACKET_SIZE);
		buffer.putInt(value);
		buffer.flip();
		return buffer;
	}

	/**
	 * Converts {@link ByteBuffer} received from wire into an "int".
	 * 
	 * Throws NPE if the input buffer is null. Throws IAE if the capacity of the
	 * input buffer is different than 4 bytes (one integer!).
	 */
	public static int byteBufferToInt(ByteBuffer buffer) {
		Objects.requireNonNull(buffer, "buffer must not be null!");
		if (buffer.capacity() != INPUT_PACKET_SIZE) {
			throw new IllegalArgumentException("Capacity of the bytebuffer is less than expected!");
		}

		buffer.position(0);
		int numberTested = buffer.getInt();
		buffer.position(0);
		return numberTested;
	}

	/**
	 * Converts an instance of {@link PrimeTestResult} to {@link ByteBuffer}
	 * ready to be sent to wire.
	 */
	public static ByteBuffer testResultToByteBuffer(PrimeTestResult primeTestResult) {
		if (primeTestResult == null) {
			return null;
		} else {
			ByteBuffer buffer = ByteBuffer.allocate(OUTPUT_PACKET_SIZE);
			buffer.putInt(primeTestResult.getNumberTested());
			byte isPrimeValue = primeTestResult.isPrime() ? B_TRUE : B_FALSE;
			buffer.put(isPrimeValue);
			buffer.flip();
			return buffer;
		}
	}

	/**
	 * Converts {@link ByteBuffer} received from wire into an instance of
	 * {@link PrimeTestResult}. null is returned in case of a wrong format. To
	 * avoid wasting heap, no Java 8 Optional's are used.
	 */
	public static PrimeTestResult byteBufferToTestResult(ByteBuffer buffer) {
		if (buffer == null) {
			return null;
		} else {
			if (buffer.capacity() == OUTPUT_PACKET_SIZE) {
				buffer.position(0);
				int numberTested = buffer.getInt();
				boolean isPrime = buffer.get() > B_FALSE;
				buffer.position(0);
				return new PrimeTestResult(numberTested, isPrime);
			} else {
				return null;
			}
		}
	}
}
