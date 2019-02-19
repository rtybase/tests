package com.test.anagram.service;

import java.io.File;

import com.test.anagram.service.core.AnagramWordDictionary;
import com.test.anagram.service.core.DictionaryLoader;
import com.test.anagram.service.health.AnagramServiceHealthcheck;
import com.test.anagram.service.resource.AnagramResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class AnagramService extends Application<AnagramServiceConfiguration> {

	public static void main(String[] args) throws Exception {
		new AnagramService().run(args);
	}

	@Override
	public String getName() {
		return "anagram-service";
	}

	@Override
	public void run(AnagramServiceConfiguration configuration, Environment environment) {
		AnagramWordDictionary dictionary = DictionaryLoader.loadFrom(new File(configuration.getWordsFilePath()));
		environment.healthChecks().register("dictionary", new AnagramServiceHealthcheck(dictionary));
		environment.jersey().register(new AnagramResource(dictionary));
	}
}
