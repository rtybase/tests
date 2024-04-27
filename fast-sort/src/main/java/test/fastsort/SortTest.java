package test.fastsort;

import java.util.ArrayList;
import java.util.List;

public class SortTest {
	private static final int TESTS_NO = 100;

	public static void main(String[] args) {
		List<String> strings = DataGenerator.generateRandomStrings(Constants.LIST_SIZE, Constants.STRING_SIZE);

		fastSort(strings, true);
		standardSort(strings, true);

		long f_total = 0;
		long s_total = 0;

		for (int i = 0; i < TESTS_NO; i++) {
			f_total += fastSort(strings, false);
			s_total += standardSort(strings, false);
			System.out.println(i);
		}

		System.out.println();

		System.out.println("--------------------Results");
		System.out.println("Fast Sort avg: " + ((f_total * 1.0D) / TESTS_NO) + "ms");
		System.out.println("Std Sort avg: " + ((s_total * 1.0D) / TESTS_NO) + "ms");
	}

	private static long standardSort(List<String> strings, boolean showResult) {
		final List<String> toSort = new ArrayList<>(strings);
		final StandardSort ss = new StandardSort();

		final long start = System.currentTimeMillis();
		ss.sort(toSort);
		final long execTime = System.currentTimeMillis() - start;

		if (showResult) {
			System.out.println("--------------------Standard");
			content(toSort, execTime);
		}
		return execTime;
	}

	private static long fastSort(List<String> strings, boolean showResult) {
		final List<String> toSort = new ArrayList<>(strings);
		final FastSort fs = new FastSort();

		final long start = System.currentTimeMillis();
		fs.sort(toSort);
		final long execTime = System.currentTimeMillis() - start;

		if (showResult) {
			System.out.println("--------------------Fast");
			content(toSort, execTime);
		}
		return execTime;
	}

	private static void content(List<String> strings, final long execTime) {
		Utils.printHead(10, strings);
		System.out.println("...");
		Utils.printTail(10, strings);
		System.out.println("--------------------Total time: " + execTime + "msec");
		System.out.println();
	}
}
