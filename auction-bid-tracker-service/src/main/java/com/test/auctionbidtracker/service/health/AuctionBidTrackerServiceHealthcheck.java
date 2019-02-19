package com.test.auctionbidtracker.service.health;

import java.util.Objects;

import com.codahale.metrics.health.HealthCheck;
import com.test.auctionbidtracker.service.api.AuctionBidTracker;

public class AuctionBidTrackerServiceHealthcheck extends HealthCheck {
	private final AuctionBidTracker auctionBidTracker;

	public AuctionBidTrackerServiceHealthcheck(AuctionBidTracker auctionBidTracker) {
		this.auctionBidTracker = Objects.requireNonNull(auctionBidTracker, "auctionBidTracker must not be null!");
	}

	@Override
	protected Result check() throws Exception {
		return Result.healthy("Operating with '%d' items and '%d' users",
				auctionBidTracker.allItemsCount(),
				auctionBidTracker.allUsersCount());
	}
}
