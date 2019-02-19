package com.test.anagram.service.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class AnagramWordDictionaryTest {
	private AnagramWordDictionary dictionary;

	@Before
	public void setup() {
		dictionary = new AnagramWordDictionary();

		dictionary.add("AAA");
		dictionary.add("aaa");
		dictionary.add("ABBA");
		dictionary.add("aBAb");
		dictionary.add("aa");
	}

	@Test
	public void testContent() {
		assertEquals(dictionary.size(), 3);
		assertEquals(dictionary.searchFor("aaa"), new HashSet<>(Arrays.asList("aaa", "AAA")));
		assertEquals(dictionary.searchFor("abba"), new HashSet<>(Arrays.asList("ABBA", "aBAb")));
		assertEquals(dictionary.searchFor("aa"), new HashSet<>(Arrays.asList("aa")));
	}

	@Test
	public void testEmptySearchResult() {
		assertEquals(dictionary.searchFor(""), Collections.emptySet());
	}

	@Test
	public void testNothingToRemove() {
		assertFalse(dictionary.remove(""));
	}

	@Test
	public void testMatchingButNothingToRemove() {
		assertFalse(dictionary.remove("AA"));
		assertEquals(dictionary.searchFor("aa"), new HashSet<>(Arrays.asList("aa")));
	}

	@Test
	public void testRemove() {
		assertTrue(dictionary.remove("aa"));
		assertEquals(dictionary.searchFor("aa"), Collections.emptySet());

		assertTrue(dictionary.remove("AAA"));
		assertEquals(dictionary.searchFor("aaa"), new HashSet<>(Arrays.asList("aaa")));
	}

	@Test(expected = NullPointerException.class)
	public void testAddNull() {
		dictionary.add(null);
	}

	@Test(expected = NullPointerException.class)
	public void testSearchForNull() {
		dictionary.searchFor(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNull() {
		dictionary.remove(null);
	}
}
