package com.test.auctionbidtracker.service.core;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

// multiple bids on a single item from the same user are not allowed
public class Bid implements Comparable<Bid> {
	@JsonProperty
	private final Item item;
	@JsonProperty
	private final User user;
	@JsonProperty
	private final double bidValue;

	public Bid(Item item, User user, double bidValue) {
		this.item = Objects.requireNonNull(item, "item must not be null!");
		this.user = Objects.requireNonNull(user, "user must not be null!");
		if (Double.compare(bidValue, 0.0D) <= 0) {
			throw new IllegalArgumentException("bidValue must be positive!");
		}
		this.bidValue = bidValue;
	}

	public Item getItem() {
		return item;
	}

	public User getUser() {
		return user;
	}

	public double getBidValue() {
		return bidValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Bid other = (Bid) obj;
		return Objects.equals(item, other.item) && Objects.equals(user, other.user);
	}

	@Override
	public int compareTo(Bid other) {
		Objects.requireNonNull(other, "other must not be null!");

		int bidResult = Double.compare(bidValue, other.bidValue);
		if (bidResult == 0) {
			return user.getName().compareTo(other.getUser().getName());
		} else {
			return bidResult;
		}
	}
}
