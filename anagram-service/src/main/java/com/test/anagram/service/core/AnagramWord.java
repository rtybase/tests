package com.test.anagram.service.core;

import java.util.Arrays;
import java.util.Objects;

public class AnagramWord {
	private final String word;
	private final String sorted;

	public AnagramWord(String word) {
		this.word = Objects.requireNonNull(word, "word must not be null!");
		this.sorted = computeSortedString();
	}

	private String computeSortedString() {
		char[] characters = word.toLowerCase().toCharArray();
		Arrays.sort(characters);
		return new String(characters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sorted);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		AnagramWord other = (AnagramWord) o;
		return Objects.equals(this.sorted, other.sorted);
	}
}
