package com.test.anagram.service.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServiceClient {
	private static final GenericType<Set<String>> SET_OF_STRINGS_TYPE = new GenericType<Set<String>>() {
	};
	private static final GenericType<Response> RESPONSE_TYPE = new GenericType<Response>() {
	};

	private final Client client;
	private final String url;

	public ServiceClient(String url) {
		this.url = Objects.requireNonNull(url, "url must not be null!");
		this.client = ClientBuilder.newClient();
	}

	public void add(String word) {
		Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
				.put(Entity.json(new HashSet<>(Arrays.asList(word))), RESPONSE_TYPE);
		int status = response.getStatus();
		if (status != Response.Status.NO_CONTENT.getStatusCode()) {
			throw new ClientErrorException("Add operation failed!", status);
		}
	}

	public Set<String> searchFor(String word) {
		Response response = client.target(url)
				.queryParam("word", word)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		int status = response.getStatus();
		if (status == Response.Status.OK.getStatusCode()) {
			return response.readEntity(SET_OF_STRINGS_TYPE);
		} else {
			throw new ClientErrorException("Search operation failed!", status);
		}
	}

	public boolean remove(String word) {
		Response response = client.target(url)
				.queryParam("word", word)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.delete(RESPONSE_TYPE);
		int status = response.getStatus();
		if (status == Response.Status.NO_CONTENT.getStatusCode()) {
			return true;
		} else if (status == Response.Status.NOT_FOUND.getStatusCode()) {
			return false;
		} else {
			throw new ClientErrorException("Remove operation failed!", status);
		}
	}

	public void close() {
		client.close();
	}
}
