package test.fastsort;

//import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
	// This is already sorted
	private static final String CHARS_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Random RAND = new Random();
	//private static final SecureRandom RAND = new SecureRandom();

	private DataGenerator() {
	}

	public static List<String> generateRandomStrings(int listSize, int stringSize) {
		List<String> result = new ArrayList<>(listSize);
		for (int i = 0; i < listSize; i++) {
			result.add(generateRandomString(stringSize));
		}
		return result;
	}

	public static String generateRandomString(int stringSize) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stringSize; i++) {
			sb.append(CHARS_SET.charAt(RAND.nextInt(CHARS_SET.length())));
		}
		return sb.toString();
	}

	public static String[] generateSortedBuckets(int charSize) {
		final int actualSize = charSize < 1 ? 1 : charSize;
		final int arraySize = (int) Math.pow(CHARS_SET.length(), actualSize);

		final String[] result = new String[arraySize];
		if (actualSize == 1) {
			for (int i = 0; i < CHARS_SET.length(); i++) {
				result[i] = Character.toString(CHARS_SET.charAt(i));
			}
		} else {
			final String[] lower = generateSortedBuckets(charSize - 1);
			int count = 0;
			for (int i = 0; i < CHARS_SET.length(); i++) {
				for (int j = 0; j < lower.length; j++) {
					result[count++] = Character.toString(CHARS_SET.charAt(i)) + lower[j];
				}
			}
		}

		return result;
	}
}
