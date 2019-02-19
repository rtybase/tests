package com.test.auctionbidtracker.service.core;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.auctionbidtracker.service.api.BidNotFoundException;

public class ItemAndBids {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemAndBids.class);

	private final Item item;
	private final Set<Bid> bids;

	public ItemAndBids(Item item) {
		this.item = Objects.requireNonNull(item, "item must not be null!");
		this.bids = ConcurrentHashMap.newKeySet();
	}

	public void addBid(Bid bid) {
		bids.remove(bid);
		bids.add(bid);
	}

	public Item getItem() {
		return item;
	}

	public Set<Bid> getBids() {
		TreeSet<Bid> orderedSet = new TreeSet<>(Collections.reverseOrder());
		orderedSet.addAll(bids);
		return Collections.unmodifiableSet(orderedSet);
	}

	public Bid getWinningBids() throws BidNotFoundException {
		Set<Bid> currentBids = getBids();
		if (currentBids.size() > 0) {
			return getBids().iterator().next();
		} else {
			LOGGER.info("Item with id '{}' has no bids!", item.getItemId());
			throw new BidNotFoundException(String.format("Item with id '%s' has no bids.",
					item.getItemId()));
		}
	}
}
