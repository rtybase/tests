package com.test.auctionbidtracker.service;

import com.test.auctionbidtracker.service.api.AuctionBidTracker;
import com.test.auctionbidtracker.service.api.AuctionBidTrackerImpl;
import com.test.auctionbidtracker.service.health.AuctionBidTrackerServiceHealthcheck;
import com.test.auctionbidtracker.service.mappers.BidNotFoundExceptionMapper;
import com.test.auctionbidtracker.service.mappers.ItemNotFoundExceptionMapper;
import com.test.auctionbidtracker.service.mappers.UserNotFoundExceptionMapper;
import com.test.auctionbidtracker.service.resource.ItemResource;
import com.test.auctionbidtracker.service.resource.UserResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class AuctionBidTrackerService extends Application<AuctionBidTrackerServiceConfiguration> {
	public static void main(String[] args) throws Exception {
		new AuctionBidTrackerService().run(args);
	}

	@Override
	public String getName() {
		return "auction-bid-tracker-service";
	}

	@Override
	public void run(AuctionBidTrackerServiceConfiguration configuration, Environment environment) {
		environment.jersey().register(new ItemNotFoundExceptionMapper());
		environment.jersey().register(new UserNotFoundExceptionMapper());
		environment.jersey().register(new BidNotFoundExceptionMapper());

		final AuctionBidTracker tracker = new AuctionBidTrackerImpl();
		environment.healthChecks().register("db_state", new AuctionBidTrackerServiceHealthcheck(tracker));
		environment.jersey().register(new ItemResource(tracker));
		environment.jersey().register(new UserResource(tracker));
	}
}
