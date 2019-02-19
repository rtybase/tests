package com.test.randprime.randomizer;

import com.test.randprime.randomizer.core.RandomizerService;

public class Randomizer {
	private static final String PORT_INPUT_PARAMETER_NAME = "--port=";
	private static final String HOST_INPUT_PARAMETER_NAME = "--host=";

	private static final String HOST = "127.0.0.1";
	private static final int PORT = 2345;

	public static void main(String[] args) throws Exception {
		int port = PORT;
		String host = HOST;

		for (int i = 0; i < args.length; i++) {
			if (args[i].toLowerCase().startsWith(PORT_INPUT_PARAMETER_NAME)) {
				port = Integer.parseInt(args[i].substring(PORT_INPUT_PARAMETER_NAME.length()));
			} else if (args[i].toLowerCase().startsWith(HOST_INPUT_PARAMETER_NAME)) {
				host = args[i].substring(HOST_INPUT_PARAMETER_NAME.length());
			}
		}

		System.out.println("Connecting to " + host + ":" + port);
		new RandomizerService(host, port).start();
	}
}
