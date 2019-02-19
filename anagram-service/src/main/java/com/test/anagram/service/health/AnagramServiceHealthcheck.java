package com.test.anagram.service.health;

import java.util.Objects;

import com.codahale.metrics.health.HealthCheck;
import com.test.anagram.service.core.AnagramWordDictionary;

public class AnagramServiceHealthcheck extends HealthCheck {
	private final AnagramWordDictionary dictionary;

	public AnagramServiceHealthcheck(AnagramWordDictionary dictionary) {
		this.dictionary = Objects.requireNonNull(dictionary, "dictionary must not be null!");
	}

	@Override
	protected Result check() throws Exception {
		return Result.healthy("Operating with dictionary size '%d'", dictionary.size());
	}
}
