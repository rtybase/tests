package com.test.auctionbidtracker.service.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.test.auctionbidtracker.service.core.Bid;
import com.test.auctionbidtracker.service.core.Item;
import com.test.auctionbidtracker.service.core.User;

public class AuctionBidTrackerImplTest {
	private static final double BID_HIGH_VALUE = 0.5D;
	private static final double BID_LOW_VALUE = 0.1D;

	private static final String BAD_ID = "DOESN'T EXISTS";
	private static final String TEST_NAME = "test-name";

	private AuctionBidTrackerImpl auctionBidTracker;

	@Before
	public void setup() {
		auctionBidTracker = new AuctionBidTrackerImpl();
	}

	@Test
	public void testAddNewUser() {
		User user1 = new User(TEST_NAME);
		assertEquals(auctionBidTracker.addUser(user1), user1);
		assertEquals(auctionBidTracker.allUsersCount(), 1);

		User user2 = new User(TEST_NAME);
		assertEquals(auctionBidTracker.addUser(user2), user2);
		assertEquals(auctionBidTracker.allUsersCount(), 1);

		assertTrue(auctionBidTracker.allUsers().contains(user2));
	}

	@Test
	public void testUserFound() throws Exception {
		User user = new User(TEST_NAME);
		assertEquals(auctionBidTracker.addUser(user), user);

		assertEquals(auctionBidTracker.getUserById(user.getUserId()), user);
		assertTrue(auctionBidTracker.getUserBidItems(user).isEmpty());
	}

	@Test(expected = UserNotFoundException.class)
	public void testUserNotFound() throws Exception {
		assertEquals(auctionBidTracker.allUsersCount(), 0);
		auctionBidTracker.getUserById(BAD_ID);
	}

	@Test
	public void testAddNewItem() throws Exception {
		Item item1 = auctionBidTracker.addItem(new Item(TEST_NAME));
		assertEquals(auctionBidTracker.allItemsCount(), 1);

		Item item2 = auctionBidTracker.addItem(new Item(TEST_NAME));
		assertEquals(auctionBidTracker.allItemsCount(), 2);

		assertTrue(auctionBidTracker.allItems().contains(item1));
		assertTrue(auctionBidTracker.allItems().contains(item2));
	}

	@Test
	public void testItemFound() throws Exception {
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));

		assertEquals(auctionBidTracker.getItemById(item.getItemId()), item);
		assertTrue(auctionBidTracker.getItemBids(item).isEmpty());
	}

	@Test(expected = ItemNotFoundException.class)
	public void testItemNotFound() throws Exception {
		assertEquals(auctionBidTracker.allItemsCount(), 0);
		auctionBidTracker.getItemById(BAD_ID);
	}

	@Test(expected = BidNotFoundException.class)
	public void testNoWinningBidFound() throws Exception {
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));
		assertEquals(auctionBidTracker.allItemsCount(), 1);
		auctionBidTracker.getItemWinningBid(item);
	}

	@Test(expected = UserNotFoundException.class)
	public void testAddBidWithMissingUser() throws Exception {
		assertEquals(auctionBidTracker.allUsersCount(), 0);
		assertEquals(auctionBidTracker.allItemsCount(), 0);

		User user = new User(TEST_NAME);
		Item item = new Item(TEST_NAME);

		auctionBidTracker.addBid(new Bid(item, user, BID_HIGH_VALUE));
	}

	@Test(expected = ItemNotFoundException.class)
	public void testAddBidWithMissingItem() throws Exception {
		User user = auctionBidTracker.addUser(new User(TEST_NAME));

		assertEquals(auctionBidTracker.allUsersCount(), 1);
		assertEquals(auctionBidTracker.allItemsCount(), 0);

		Item item = new Item(TEST_NAME);

		auctionBidTracker.addBid(new Bid(item, user, BID_HIGH_VALUE));
	}

	@Test
	public void testAddBid() throws Exception {
		User user = auctionBidTracker.addUser(new User(TEST_NAME));
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));

		assertEquals(auctionBidTracker.allUsersCount(), 1);
		assertEquals(auctionBidTracker.allItemsCount(), 1);

		Bid bid = new Bid(item, user, BID_HIGH_VALUE);

		auctionBidTracker.addBid(bid);
		assertEquals(auctionBidTracker.getItemWinningBid(item), bid);
		assertEquals(auctionBidTracker.getItemBids(item).size(), 1);
		assertTrue(auctionBidTracker.getItemBids(item).contains(bid));

		assertEquals(auctionBidTracker.getUserBidItems(user).size(), 1);
		assertTrue(auctionBidTracker.getUserBidItems(user).contains(item));
	}

	@Test
	public void testUserReBids() throws Exception {
		User user = auctionBidTracker.addUser(new User(TEST_NAME));
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));

		auctionBidTracker.addBid(new Bid(item, user, BID_HIGH_VALUE));
		assertEquals(auctionBidTracker.getItemBids(item).size(), 1);
		assertEquals(auctionBidTracker.getUserBidItems(user).size(), 1);

		auctionBidTracker.addBid(new Bid(item, user, BID_LOW_VALUE));
		assertEquals(auctionBidTracker.getItemBids(item).size(), 1);
		assertEquals(auctionBidTracker.getUserBidItems(user).size(), 1);
		assertEquals(auctionBidTracker.getItemWinningBid(item).getBidValue(), BID_LOW_VALUE, 0.000D);
	}

	@Test
	public void testTwoBidsHigh1st() throws Exception {
		User user1 = auctionBidTracker.addUser(new User(TEST_NAME));
		User user2 = auctionBidTracker.addUser(new User(TEST_NAME + 2));
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));

		auctionBidTracker.addBid(new Bid(item, user1, BID_HIGH_VALUE));
		auctionBidTracker.addBid(new Bid(item, user2, BID_LOW_VALUE));

		assertEquals(auctionBidTracker.getItemBids(item).size(), 2);
		assertEquals(auctionBidTracker.getUserBidItems(user1).size(), 1);
		assertEquals(auctionBidTracker.getUserBidItems(user2).size(), 1);

		assertEquals(auctionBidTracker.getItemWinningBid(item).getBidValue(), BID_HIGH_VALUE, 0.000D);
	}

	@Test
	public void testTwoBidsLow1st() throws Exception {
		User user1 = auctionBidTracker.addUser(new User(TEST_NAME));
		User user2 = auctionBidTracker.addUser(new User(TEST_NAME + 2));
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));

		auctionBidTracker.addBid(new Bid(item, user1, BID_LOW_VALUE));
		auctionBidTracker.addBid(new Bid(item, user2, BID_HIGH_VALUE));

		assertEquals(auctionBidTracker.getItemBids(item).size(), 2);
		assertEquals(auctionBidTracker.getUserBidItems(user1).size(), 1);
		assertEquals(auctionBidTracker.getUserBidItems(user2).size(), 1);

		assertEquals(auctionBidTracker.getItemWinningBid(item).getBidValue(), BID_HIGH_VALUE, 0.000D);
	}

	@Test
	public void testThreeBids() throws Exception {
		User user1 = auctionBidTracker.addUser(new User(TEST_NAME));
		User user2 = auctionBidTracker.addUser(new User(TEST_NAME + 2));
		Item item = auctionBidTracker.addItem(new Item(TEST_NAME));

		auctionBidTracker.addBid(new Bid(item, user1, BID_LOW_VALUE));
		auctionBidTracker.addBid(new Bid(item, user2, BID_LOW_VALUE));
		auctionBidTracker.addBid(new Bid(item, user2, BID_HIGH_VALUE));

		assertEquals(auctionBidTracker.getUserBidItems(user1).size(), 1);
		assertEquals(auctionBidTracker.getUserBidItems(user2).size(), 1);
		assertEquals(auctionBidTracker.getItemBids(item).size(), 2);

		assertEquals(auctionBidTracker.getItemWinningBid(item).getBidValue(), BID_HIGH_VALUE, 0.000D);
	}
}
