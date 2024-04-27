package test.fastsort;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testPrintHeadWithSmallList() throws Exception {
		final String result = printHead(3, Arrays.asList("111", "222"));
		assertEquals(extractedContent(), result);
	}

	@Test
	public void testPrintHeadWithLargeList() throws Exception {
		final String result = printHead(2, Arrays.asList("111", "222", "333"));
		assertEquals(extractedContent(), result);
	}

	@Test
	public void testPrintTailWithSmallList() throws Exception {
		final String result = printTail(3, Arrays.asList("111", "222"));
		assertEquals(extractedContent(), result);
	}

	@Test
	public void testPrintTailWithLargeList() throws Exception {
		final String result = printTail(2, Arrays.asList("000", "111", "222"));
		assertEquals(extractedContent(), result);
	}

	private static String printHead(int size, List<String> list) throws Exception {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(outputStream)) {
			Utils.printHead(size, list, printStream);
			return outputStream.toString();
		}
	}

	private static String printTail(int size, List<String> list) throws Exception {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(outputStream)) {
			Utils.printTail(size, list, printStream);
			return outputStream.toString();
		}
	}

	private static String extractedContent() {
		return String.format("111%s222%s", System.lineSeparator(), System.lineSeparator());
	}
}
