package com.test.auctionbidtracker.service.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private static final String TEST_NAME = "test-name";

	private User user;

	@Before
	public void setup() {
		user = new User(TEST_NAME);
	}

	@Test
	public void testEquals() {
		assertTrue(user.equals(user));
		assertTrue(user.equals(new User(TEST_NAME)));
	}

	@Test
	public void testNotEquals() {
		assertFalse(user.equals(null));
		assertFalse(user.equals(new String("other-name")));
		assertFalse(user.equals(new User("other-name")));
	}
}
