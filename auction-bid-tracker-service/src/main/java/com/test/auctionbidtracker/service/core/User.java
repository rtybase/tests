package com.test.auctionbidtracker.service.core;

import java.util.Objects;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.hash.Hashing;

// user name is unique.
// DTO and DAO at the same time.
public class User {
	@JsonProperty
	private String userId;

	@Valid
	@NotEmpty
	@JsonProperty
	private String name;

	private User() {
		// for deserialisation
	}

	public User(String name) {
		this();
		this.name = Objects.requireNonNull(name, "name must not be null!");
		this.userId = Hashing.sha256().hashBytes(name.getBytes()).toString();
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		User other = (User) obj;
		return Objects.equals(userId, other.userId);
	}

}
