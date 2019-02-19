package com.test.randprime.primechecker;

import com.test.randprime.primechecker.core.DataProcessor;

public class PrimeChecker {
	private static final String PORT_INPUT_PARAMETER_NAME = "--port=";
	private static final String QUEUE_SIZE_INPUT_PARAMETER_NAME = "--queuesize=";
	private static final String THREADS_INPUT_PARAMETER_NAME = "--threads=";

	private static final int PORT = 2345;
	private static final int QUEUE_SIZE = 512;
	private static final int MAX_THREADS = -1; // disabled

	public static void main(String[] args) throws Exception {
		int port = PORT;
		int queueSize = QUEUE_SIZE;
		int maxThreads = MAX_THREADS;

		for (int i = 0; i < args.length; i++) {
			if (args[i].toLowerCase().startsWith(PORT_INPUT_PARAMETER_NAME)) {
				port = Integer.parseInt(args[i].substring(PORT_INPUT_PARAMETER_NAME.length()));
			} else if (args[i].toLowerCase().startsWith(QUEUE_SIZE_INPUT_PARAMETER_NAME)) {
				queueSize = Integer.parseInt(args[i].substring(QUEUE_SIZE_INPUT_PARAMETER_NAME.length()));
			} else if (args[i].toLowerCase().startsWith(THREADS_INPUT_PARAMETER_NAME)) {
				maxThreads = Integer.parseInt(args[i].substring(THREADS_INPUT_PARAMETER_NAME.length()));
			}
		}

		System.out.println("Listening on port: " + port);
		System.out.println("Maximum queue size: " + queueSize);
		System.out.println("Maximum threads: " + (maxThreads <= 0 ? "unbounded" : maxThreads));
		System.out.println("Use CTRL+C to stop!");

		new DataProcessor(port, queueSize, maxThreads).start();
	}
}
