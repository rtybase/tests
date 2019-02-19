package com.test.anagram.service.resource;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.test.anagram.service.core.AnagramWordDictionary;

import io.dropwizard.testing.junit.ResourceTestRule;

public class AnagramResourceTest {
	private static final HashSet<String> EXPECTED_CONTENT = new HashSet<>(Arrays.asList("aa", "AA"));

	private static final AnagramWordDictionary DICTIONARY = new AnagramWordDictionary();

	@Rule
	public final ResourceTestRule resources = ResourceTestRule.builder().addResource(new AnagramResource(DICTIONARY))
			.build();

	@Before
	public void setup() {
		DICTIONARY.remove("aa");
		DICTIONARY.remove("AA");
	}

	@Test
	public void testAdd() {
		assertEquals(DICTIONARY.size(), 0);
		Response response = resources.target(AnagramResource.PATH)
				.request()
				.put(Entity.json(EXPECTED_CONTENT),
						new GenericType<Response>() {});
		assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
		assertEquals(DICTIONARY.searchFor("aa"), EXPECTED_CONTENT);
	}

	@Test
	public void testRemove() {
		assertEquals(DICTIONARY.size(), 0);
		DICTIONARY.add("aa");
		Response response = resources.target(AnagramResource.PATH)
				.queryParam("word", "aa")
				.request()
				.delete(new GenericType<Response>() {});
		assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
		assertEquals(DICTIONARY.size(), 0);
	}

	@Test
	public void testRemoveNotFound() {
		assertEquals(DICTIONARY.size(), 0);
		Response response = resources.target(AnagramResource.PATH)
				.queryParam("word", "aa")
				.request()
				.delete(new GenericType<Response>() {});
		assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void testSearch() {
		assertEquals(DICTIONARY.size(), 0);
		DICTIONARY.add("aa");
		DICTIONARY.add("AA");
		Set<String> result = resources.target(AnagramResource.PATH)
				.queryParam("word", "aa")
				.request()
				.get(new GenericType<Set<String>>() {});
		assertEquals(result, EXPECTED_CONTENT);
	}
}