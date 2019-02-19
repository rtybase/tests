package com.test.anagram.service;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class AnagramServiceConfiguration extends Configuration {
	@NotEmpty
	@JsonProperty
	private String wordsFilePath;

	public String getWordsFilePath() {
		return wordsFilePath;
	}
}
