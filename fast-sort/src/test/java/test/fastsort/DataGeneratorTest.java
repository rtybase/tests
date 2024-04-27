package test.fastsort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.junit.Test;

public class DataGeneratorTest {
	@Test
	public void testGenerateRandomString() {
		assertEquals(10, DataGenerator.generateRandomString(10).length());
	}

	@Test
	public void testGenerateRandomStrings() {
		final List<String> randomStrings = DataGenerator.generateRandomStrings(10, 2);
		assertEquals(10, randomStrings.size());

		for (String value : randomStrings) {
			assertEquals(2, value.length());
		}
	}

	@Test
	public void testGenerateSortedBucketsOneLevel() {
		final String[] result = DataGenerator.generateSortedBuckets(1);
		assertArrayEquals(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }, result);
	}

	@Test
	public void testGenerateSortedBucketsTwoLevels() {
		final String[] result = DataGenerator.generateSortedBuckets(2);
		assertEquals("AA", result[0]);
		assertEquals("AB", result[1]);
		assertEquals("AC", result[2]);
		assertEquals("ZY", result[result.length - 2]);
		assertEquals("ZZ", result[result.length - 1]);
	}
}
