package com.test.auctionbidtracker.service.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BidTest {
	private static final String OTHER_TEST_NAME = "other-name";
	private static final String TEST_NAME = "test-name";
	private static final double TEST_HIGH_BID_VALUE = 0.5D;
	private static final double TEST_LOW_BID_VALUE = 0.1D;

	private User user;
	private Item item;
	private Bid bid;

	@Before
	public void setup() {
		item = new Item(TEST_NAME);
		user = new User(TEST_NAME);
		bid = new Bid(item, user, TEST_HIGH_BID_VALUE);
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullUser() {
		new Bid(item, null, TEST_HIGH_BID_VALUE);
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullItem() {
		new Bid(null, user, TEST_HIGH_BID_VALUE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithZeroBidValue() {
		new Bid(item, user, 0.0D);
	}

	@Test
	public void testEquals() {
		assertTrue(bid.equals(bid));
		assertTrue(bid.equals(new Bid(item, user, 0.1D)));
	}

	@Test
	public void testNotEquals() {
		assertFalse(bid.equals(null));
		assertFalse(bid.equals(new String(OTHER_TEST_NAME)));
		assertFalse(bid.equals(new Bid(item, new User(OTHER_TEST_NAME), TEST_LOW_BID_VALUE)));
		assertFalse(bid.equals(new Bid(new Item(OTHER_TEST_NAME), user, TEST_LOW_BID_VALUE)));
	}

	@Test
	public void testCompareToLowerValue() {
		assertTrue(bid.compareTo(new Bid(item, new User(OTHER_TEST_NAME), TEST_LOW_BID_VALUE)) > 0);
	}

	@Test
	public void testCompareToHiherValue() {
		Bid lowerValueBid = new Bid(item, new User(OTHER_TEST_NAME), TEST_LOW_BID_VALUE);
		assertTrue(lowerValueBid.compareTo(bid) < 0);
	}

	@Test
	public void testCompareToSameValue() {
		assertTrue(bid.compareTo(new Bid(item, new User(OTHER_TEST_NAME), TEST_HIGH_BID_VALUE)) > 0);
	}
}
