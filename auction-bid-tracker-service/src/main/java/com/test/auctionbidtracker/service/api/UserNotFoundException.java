package com.test.auctionbidtracker.service.api;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1000002L;
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
