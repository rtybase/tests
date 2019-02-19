package com.test.auctionbidtracker.service.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserAndItems {
	private final User user;
	private final Set<Item> items;

	public UserAndItems(User user) {
		this.user = Objects.requireNonNull(user, "user must not be null!");
		this.items = ConcurrentHashMap.newKeySet();
	}

	public User getUser() {
		return user;
	}

	public void addBid(Bid bid) {
		items.remove(bid.getItem());
		items.add(bid.getItem());
	}

	public Collection<Item> getBidItems() {
		return Collections.unmodifiableSet(items);
	}
}
