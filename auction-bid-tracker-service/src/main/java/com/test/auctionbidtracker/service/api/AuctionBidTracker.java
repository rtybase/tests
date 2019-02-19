package com.test.auctionbidtracker.service.api;

import java.util.Collection;

import com.test.auctionbidtracker.service.core.Bid;
import com.test.auctionbidtracker.service.core.Item;
import com.test.auctionbidtracker.service.core.User;

public interface AuctionBidTracker {
	User addUser(User newUser);

	Collection<User> allUsers();

	int allUsersCount();

	User getUserById(String userId) throws UserNotFoundException;

	Collection<Item> getUserBidItems(User user) throws UserNotFoundException;

	Item addItem(Item newItem);

	Collection<Item> allItems();

	int allItemsCount();

	Item getItemById(String itemId) throws ItemNotFoundException;

	Collection<Bid> getItemBids(Item item) throws ItemNotFoundException;

	Bid getItemWinningBid(Item item) throws ItemNotFoundException, BidNotFoundException;

	void addBid(Bid bid) throws ItemNotFoundException, UserNotFoundException;
}
