package com.test.randprime.randomizer.core;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Random;

import com.test.randprime.commons.api.ByteBufferPackagingUtil;

public class Sender extends ChannelWithSelector implements Runnable {
	private final Random generator = new Random();

	// package protected on purpose
	Sender(DatagramChannel channel) throws IOException {
		super(channel, SelectionKey.OP_WRITE);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				SelectionKey key = findReadyKey();
				write(key);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	protected boolean keyIsReady(SelectionKey key) {
		return key.isWritable();
	}

	private void write(SelectionKey key) throws IOException {
		if (key != null) {
			DatagramChannel channel = (DatagramChannel) key.channel();
			int randomInt = generator.nextInt();
			int bytesSent = channel.write(ByteBufferPackagingUtil.intToByteBuffer(randomInt));
			if (bytesSent <= 0) {
				System.out.println("SND: Failed to write " + randomInt);
			}
		}
	}
}
