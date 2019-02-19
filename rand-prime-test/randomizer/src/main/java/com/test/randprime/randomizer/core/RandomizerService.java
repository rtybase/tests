package com.test.randprime.randomizer.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomizerService {
	private final ExecutorService executor;
	private final DatagramChannel clientChannel;

	public RandomizerService(String host, int port) throws IOException {
		Objects.requireNonNull(host, "host must not be null!");
		if (port <= 0) {
			throw new IllegalArgumentException("port must be positive!");
		}

		this.clientChannel = createAndConnectSocket(host, port);
		this.executor = Executors.newFixedThreadPool(2);
	}

	private DatagramChannel createAndConnectSocket(String host, int port) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.connect(new InetSocketAddress(host, port));
		channel.configureBlocking(false);
		return channel;
	}

	public void start() throws Exception {
		executor.submit(new Receiver(clientChannel));
		executor.submit(new Sender(clientChannel));
	}
}
