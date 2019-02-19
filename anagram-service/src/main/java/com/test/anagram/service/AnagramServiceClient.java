package com.test.anagram.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import com.test.anagram.service.client.Operation;
import com.test.anagram.service.client.ServiceClient;

public class AnagramServiceClient {

	public static void main(String[] args) throws Exception {
		ServiceClient client = new ServiceClient(readServiceUrl(args));

		showOptions();

		Scanner scanner = new Scanner(System.in);

		Operation op = askForOperation(scanner);
		if (op == Operation.UNKNOWN) {
			System.out.println("Undefined operation. Try again later!");
			System.exit(-1);
		}

		String word = askForWord(scanner, op);
		System.out.println("processing " + word);

		try {
			op.execute(client, word);
		} catch (Exception ex) {
			System.out.println("An error occured, please pass the following to the Development team!");
			System.out.println("--------------------------------------------------------------------");
			ex.printStackTrace();
		}
		client.close();
	}

	private static String readServiceUrl(String[] args) throws FileNotFoundException, IOException {
		if (args.length < 1) {
			System.out.println("Properties file not provided in the command line!");
			System.exit(-1);
		}
		File file = new File(args[0]);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		String serviceUrl = properties.getProperty("service_url");
		fileInput.close();
		return serviceUrl;
	}

	private static String askForWord(Scanner scanner, Operation op) {
		System.out.println("Please enter the word to " + op.name().toLowerCase() + ": ");
		String result = scanner.nextLine();
		return result.trim();
	}

	private static Operation askForOperation(Scanner scanner) throws IOException {
		System.out.println("Your choice: ");
		Operation op = Operation.UNKNOWN;

		String operation = scanner.nextLine();
		switch (operation) {
		case "a":
		case "A":
			op = Operation.ADD;
			break;
		case "d":
		case "D":
			op = Operation.DELETE;
			break;
		case "p":
		case "P":
			op = Operation.PRINT;
			break;
		}
		return op;
	}

	private static void showOptions() {
		System.out.println("Please choose from the following operations:");
		System.out.println("\t[A] - Add a word to the anagram dictionary.");
		System.out.println("\t[D] - Delete a word from the anagram dictionary.");
		System.out.println("\t[P] - Print anagrams from the dictionary.");
	}
}
