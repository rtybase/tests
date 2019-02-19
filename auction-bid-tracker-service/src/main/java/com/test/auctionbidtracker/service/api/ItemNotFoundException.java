package com.test.auctionbidtracker.service.api;

public class ItemNotFoundException extends Exception {
	private static final long serialVersionUID = 1000001L;

	public ItemNotFoundException(String message) {
		super(message);
	}
}
