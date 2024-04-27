package test.fastsort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastSort {
	private static final int CHARS_TO_INDEX = 2;
	private static final String[] SORTED_BUCKET = DataGenerator.generateSortedBuckets(CHARS_TO_INDEX);;

	private final Map<String, List<String>> valueBuckets;

	public FastSort() {
		valueBuckets = prepareValueBuckets(SORTED_BUCKET);
	}

	public void sort(List<String> strings) {
		for (String str : strings) {
			valueBuckets.get(str.substring(0, CHARS_TO_INDEX)).add(str);
		}

		for (int i = 0; i < SORTED_BUCKET.length; i++) {
			List<String> subList = valueBuckets.get(SORTED_BUCKET[i]);
			if (!subList.isEmpty()) {
				Collections.sort(subList);
			}
		}

		strings.clear();

		for (int i = 0; i < SORTED_BUCKET.length; i++) {
			List<String> subList = valueBuckets.get(SORTED_BUCKET[i]);
			if (!subList.isEmpty()) {
				strings.addAll(subList);
			}
		}
	}

	private static Map<String, List<String>> prepareValueBuckets(String[] sortedBuckets) {
		Map<String, List<String>> valueBuckets = new HashMap<>(sortedBuckets.length);
		for (int i = 0; i < sortedBuckets.length; i++) {
			valueBuckets.put(sortedBuckets[i], new ArrayList<>(256));
		}
		return valueBuckets;
	}
}
