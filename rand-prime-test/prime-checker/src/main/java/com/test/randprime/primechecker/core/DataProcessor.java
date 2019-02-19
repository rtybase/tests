package com.test.randprime.primechecker.core;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.test.randprime.commons.api.ByteBufferPackagingUtil;
import com.test.randprime.primechecker.io.UdpClient;
import com.test.randprime.primechecker.io.UdpServer;

/**
 * This isn't an actual queue, but it uses a thread pool, which internally uses
 * a task queue.
 */
public class DataProcessor {
	public static final int TIMEOUT = 3000;

	private final int maximumQueueSize;
	private final ThreadPoolExecutor pool;
	private final UdpServer server;
	private final PrimeNumberValidator validator = new PrimeNumberValidator();

	public DataProcessor(int port, int maximumQueueSize, int maxThreads) throws IOException {
		if (maximumQueueSize <= 0) {
			throw new IllegalArgumentException("maximumQueueSize must be positive!");
		}

		this.server = new UdpServer(port, ByteBufferPackagingUtil.INPUT_PACKET_SIZE);

		this.maximumQueueSize = maximumQueueSize;
		if (maxThreads <= 0) {
			this.pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		} else {
			this.pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxThreads);
		}
	}

	public void start() throws IOException {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				UdpClient client = server.waitForDataFromClient(TIMEOUT);
				if (client != null) {
					handleClientRequest(client);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void handleClientRequest(UdpClient client) throws InterruptedException {
		CheckNumberTask task = new CheckNumberTask(client, validator);
		if (pool.getQueue().size() > maximumQueueSize) {
			// rather than wasting an entire thread, take the head task and
			// execute as part of the main thread.
			System.out.println("Reached queue limit! Processing in main thread.");
			Runnable headTask = pool.getQueue().poll();
			if (headTask != null) {
				headTask.run();
			}
		}
		pool.submit(task);
	}
}
