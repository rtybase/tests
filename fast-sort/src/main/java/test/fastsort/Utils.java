package test.fastsort;

import java.io.PrintStream;
import java.util.List;

public class Utils {
	private Utils() {
	}

	public static void printHead(int size, List<String> list) {
		printHead(size, list, System.out);
	}

	public static void printHead(int size, List<String> list, PrintStream stream) {
		final int actualSize = size < list.size() ? size : list.size();
		for (int i = 0; i < actualSize; i++) {
			stream.println(list.get(i));
		}
	}

	public static void printTail(int size, List<String> list) {
		printTail(size, list, System.out);
	}

	public static void printTail(int size, List<String> list, PrintStream stream) {
		final int actualStart = size < list.size() ? list.size() - size : 0;

		for (int i = actualStart; i < list.size(); i++) {
			stream.println(list.get(i));
		}
	}
}
