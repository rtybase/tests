package com.test.auctionbidtracker.service.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	private static final String TEST_NAME = "test-name";

	private Item item;

	@Before
	public void setup() {
		item = new Item(TEST_NAME);
	}

	@Test
	public void testEquals() {
		assertTrue(item.equals(item));
		assertTrue(item.equals(new Item(item.getItemId(), item.getName())));
	}

	@Test
	public void testNotEquals() {
		assertFalse(item.equals(null));
		assertFalse(item.equals(new String("other-name")));
		assertFalse(item.equals(new Item(item.getItemId(), "other-name")));
		assertFalse(item.equals(new Item("other-name")));
		assertFalse(item.equals(new Item(TEST_NAME)));
	}
}