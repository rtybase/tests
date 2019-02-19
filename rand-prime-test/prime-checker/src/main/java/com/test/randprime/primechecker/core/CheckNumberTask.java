package com.test.randprime.primechecker.core;

import java.nio.ByteBuffer;

import com.test.randprime.commons.api.ByteBufferPackagingUtil;
import com.test.randprime.primechecker.io.UdpClient;

/**
 * A thread pool task class which checks if the number received is a prime and
 * sends the answer back to the client
 */
public class CheckNumberTask implements Runnable {
	private final UdpClient client;
	private final PrimeNumberValidator validator;

	CheckNumberTask(UdpClient client, PrimeNumberValidator validator) {
		this.client = client;
		this.validator = validator;
	}

	@Override
	public void run() {
		try {
			ByteBuffer dataFromClient = client.read();
			int valueToCheck = ByteBufferPackagingUtil.byteBufferToInt(dataFromClient);
			client.write(ByteBufferPackagingUtil.testResultToByteBuffer(validator.check(valueToCheck)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
