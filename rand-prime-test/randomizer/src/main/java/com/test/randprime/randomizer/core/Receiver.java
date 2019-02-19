package com.test.randprime.randomizer.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

import com.test.randprime.commons.api.ByteBufferPackagingUtil;
import com.test.randprime.commons.api.PrimeTestResult;

public class Receiver extends ChannelWithSelector implements Runnable {
	private final ByteBuffer dataFromChannel;

	// package protected on purpose
	Receiver(DatagramChannel channel) throws IOException {
		super(channel, SelectionKey.OP_READ);
		this.dataFromChannel = ByteBuffer.allocate(ByteBufferPackagingUtil.OUTPUT_PACKET_SIZE);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				SelectionKey key = findReadyKey();
				PrimeTestResult result = read(key);
				if (result != null) {
					System.out.println("RECV: " + result);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	protected boolean keyIsReady(SelectionKey key) {
		return key.isReadable();
	}

	private PrimeTestResult read(SelectionKey key) throws IOException {
		if (key != null) {
			DatagramChannel channel = (DatagramChannel) key.channel();
			dataFromChannel.clear();
			int bytesRead = channel.read(dataFromChannel);
			if (bytesRead > 0) {
				return ByteBufferPackagingUtil.byteBufferToTestResult(dataFromChannel);
			}
		}
		return null;
	}
}
