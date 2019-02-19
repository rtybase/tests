package com.test.anagram.service.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class DictionaryLoaderTest {
	@Test
	public void testLoad() {
		AnagramWordDictionary dictionary = DictionaryLoader
				.loadFrom(new File("src/test/resources/test-data/words.txt"));

		assertEquals(dictionary.searchFor("aaa"), new HashSet<>(Arrays.asList("aaa", "AAA")));
		assertEquals(dictionary.searchFor("abba"), new HashSet<>(Arrays.asList("ABBA", "aBAb")));
		assertEquals(dictionary.searchFor("aa"), new HashSet<>(Arrays.asList("aa")));
	}

	@Test(expected = NullPointerException.class)
	public void testLoadWithNull() {
		DictionaryLoader.loadFrom(null);
	}
}
