package com.test.auctionbidtracker.service.core;

import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

// allows for multiple items with the same name.
// DTO and DAO at the same time
public class Item {
	@JsonProperty
	private String itemId;

	@Valid
	@NotEmpty
	@JsonProperty
	private String name;

	private Item() {
		// for deserialisation
	}

	public Item(String name) {
		this();
		this.name = Objects.requireNonNull(name, "name must not be null!");
		this.itemId = UUID.randomUUID().toString();
	}

	public Item(String itemId, String name) {
		this();
		this.name = Objects.requireNonNull(name, "name must not be null!");
		this.itemId = Objects.requireNonNull(itemId, "itemId must not be null!");
	}

	public String getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Item other = (Item) obj;
		return Objects.equals(itemId, other.itemId) && Objects.equals(name, other.name);
	}
}
