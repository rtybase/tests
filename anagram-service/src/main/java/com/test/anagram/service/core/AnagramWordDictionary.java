package com.test.anagram.service.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This dictionary assumes that search operations will be more frequent than
 * add/remove operations. Thus, it uses ReentrantReadWriteLock for thread
 * safety, as a quick implementation, still allowing for concurrent searches to
 * be lock free, assuming no ongoing add/remove operations are scheduled.
 */
public class AnagramWordDictionary {
	private final Map<AnagramWord, Set<String>> dictionary = new HashMap<>();
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public void add(String word) {
		Objects.requireNonNull(word, "word must not be null!");
		lock.writeLock().lock();
		try {
			AnagramWord aw = new AnagramWord(word);
			// this approach, although not Java 8 merge() style,
			// saves from creating an unnecessary Set<String> instance
			// if there is a key match
			Set<String> value = dictionary.get(aw);
			if (value == null) {
				value = new HashSet<>();
				dictionary.put(aw, value);
			}
			value.add(word);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public boolean remove(String word) {
		Objects.requireNonNull(word, "word must not be null!");
		boolean result = false;
		lock.writeLock().lock();
		try {
			AnagramWord aw = new AnagramWord(word);
			Set<String> value = dictionary.get(aw);
			if (value != null) {
				result = value.remove(word);
				if (value.size() == 0) {
					dictionary.remove(aw);
				}
			}
		} finally {
			lock.writeLock().unlock();
		}
		return result;
	}

	public Set<String> searchFor(String word) {
		Objects.requireNonNull(word, "word must not be null!");
		lock.readLock().lock();
		try {
			Set<String> result = dictionary.get(new AnagramWord(word));
			if (result != null) {
				// returning a copy, to guaranty the integrity of the
				// "dictionary", aka encapsulation. Immutable Set won't help,
				// since iterating through it may still throw an exception if
				// the original Set is modified by add/remove (see how
				// "dictionary" is declared, none synchronized).
				return new HashSet<>(result);
			} else {
				return Collections.emptySet();
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	public int size() {
		lock.readLock().lock();
		try {
			return dictionary.size();
		} finally {
			lock.readLock().unlock();
		}
	}
}