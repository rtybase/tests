package com.test.anagram.service.resource;

import java.util.Objects;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;
import com.test.anagram.service.core.AnagramWordDictionary;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(AnagramResource.PATH)
public class AnagramResource {
	static final String PATH = "/v1/anagram/";

	private final AnagramWordDictionary dictionary;

	public AnagramResource(AnagramWordDictionary dictionary) {
		this.dictionary = Objects.requireNonNull(dictionary, "dictionary must not be null!");
	}

	@GET
	@Timed
	public Set<String> searchFor(@QueryParam("word") String word) {
		return dictionary.searchFor(word);
	}

	@PUT
	@Timed
	public Response add(Set<String> words) {
		if (words != null) {
			words.forEach(dictionary::add);
		}
		return Response.noContent().build();
	}

	@DELETE
	@Timed
	public Response delete(@QueryParam("word") String word) {
		if (dictionary.remove(word)) {
			return Response.noContent().build();
		}
		throw new WebApplicationException(Status.NOT_FOUND);
	}
}
