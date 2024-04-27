package test.fastsort;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FastSortTest {
	private FastSort fastSort;

	@Before
	public void setup() {
		fastSort = new FastSort();
	}

	@Test
	public void testSort() {
		List<String> strings = new ArrayList<>();
		strings.add("BBBB");
		strings.add("BBAA");
		strings.add("AAAA");

		fastSort.sort(strings);

		assertEquals(3, strings.size());
		assertEquals("AAAA", strings.get(0));
		assertEquals("BBAA", strings.get(1));
		assertEquals("BBBB", strings.get(2));
	}
}
