package com.test.anagram.service.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnagramWordTest {

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNull() {
		new AnagramWord(null);
	}

	@Test
	public void testNotEqualWithNull() {
		AnagramWord aw = new AnagramWord("aba");
		assertFalse(aw.equals(null));
	}

	@Test
	public void testEqualsToSelf() {
		AnagramWord aw = new AnagramWord("aba");
		assertTrue(aw.equals(aw));
	}

	@Test
	public void testNotEqual() {
		AnagramWord aw1 = new AnagramWord("aba");
		AnagramWord aw2 = new AnagramWord("abba");
		assertFalse(aw1.equals(aw2));
		assertFalse(aw2.equals(aw1));
	}

	@Test
	public void testNotEqualWithDifferentSize() {
		AnagramWord aw1 = new AnagramWord("aa");
		AnagramWord aw2 = new AnagramWord("aaa");
		assertFalse(aw1.equals(aw2));
		assertFalse(aw2.equals(aw1));
	}

	@Test
	public void testWithEmptyStrings() {
		AnagramWord aw1 = new AnagramWord("");
		AnagramWord aw2 = new AnagramWord("");
		assertTrue(aw1.equals(aw2));
	}

	@Test
	public void testEquals() {
		AnagramWord aw1 = new AnagramWord("abba");
		AnagramWord aw2 = new AnagramWord("abab");
		assertTrue(aw1.equals(aw2));
		assertTrue(aw2.equals(aw1));
	}

	@Test
	public void testEqualsCaseInsensitive() {
		AnagramWord aw1 = new AnagramWord("aaa");
		AnagramWord aw2 = new AnagramWord("AAA");
		assertTrue(aw1.equals(aw2));
		assertTrue(aw2.equals(aw1));
	}
}
