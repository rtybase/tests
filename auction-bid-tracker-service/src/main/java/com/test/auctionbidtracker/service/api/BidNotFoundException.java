package com.test.auctionbidtracker.service.api;

public class BidNotFoundException extends Exception {
	private static final long serialVersionUID = 1000003L;

	public BidNotFoundException(String message) {
		super(message);
	}
}
