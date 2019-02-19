package com.test.anagram.service.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DictionaryLoader {
	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryLoader.class);

	public static AnagramWordDictionary loadFrom(File file) {
		Objects.requireNonNull(file, "file must not be null!");
		AnagramWordDictionary result = new AnagramWordDictionary();
		LOGGER.debug("Loading from '{}'", file);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.add(line.trim());
			}
		} catch (Exception ex) {
			LOGGER.error(
					"Failed to load anything from '{}', empty or partially loaded dictionary is created as a result!",
					file, ex);
		}
		return result;
	}
}
