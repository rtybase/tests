package com.test.auctionbidtracker.service.api;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.auctionbidtracker.service.core.Bid;
import com.test.auctionbidtracker.service.core.Item;
import com.test.auctionbidtracker.service.core.ItemAndBids;
import com.test.auctionbidtracker.service.core.User;
import com.test.auctionbidtracker.service.core.UserAndItems;

public class AuctionBidTrackerImpl implements AuctionBidTracker {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionBidTrackerImpl.class);

	private final Map<String, UserAndItems> users = new ConcurrentHashMap<>();
	private final Map<String, ItemAndBids> items = new ConcurrentHashMap<>();

	@Override
	public User addUser(User newUser) {
		Objects.requireNonNull(newUser, "newUser must not be null!");
		final User toBeAdded = new User(newUser.getName());
		UserAndItems uItems = users.computeIfAbsent(toBeAdded.getUserId(),
				k -> new UserAndItems(toBeAdded));
		return uItems.getUser();
	}

	@Override
	public Collection<User> allUsers() {
		return users.values().stream().map(UserAndItems::getUser).collect(Collectors.toSet());
	}

	@Override
	public User getUserById(String userId) throws UserNotFoundException {
		Objects.requireNonNull(userId, "userId must not be null!");
		UserAndItems uItems = findUserWithItems(userId);
		return uItems.getUser();
	}

	@Override
	public Collection<Item> getUserBidItems(User user) throws UserNotFoundException {
		Objects.requireNonNull(user, "user must not be null!");
		UserAndItems uItems = findUserWithItems(user.getUserId());
		return uItems.getBidItems();
	}

	@Override
	public Item addItem(Item newItem) {
		Objects.requireNonNull(newItem, "newItem must not be null!");
		final Item toBeAdded = new Item(newItem.getName());
		ItemAndBids iBids = items.computeIfAbsent(toBeAdded.getItemId(),
				k -> new ItemAndBids(toBeAdded));
		return iBids.getItem();
	}

	@Override
	public Collection<Item> allItems() {
		return items.values().stream().map(ItemAndBids::getItem).collect(Collectors.toSet());
	}

	@Override
	public Item getItemById(String itemId) throws ItemNotFoundException {
		Objects.requireNonNull(itemId, "itemId must not be null!");
		ItemAndBids iBids = findItemWithBids(itemId);
		return iBids.getItem();
	}

	@Override
	public Collection<Bid> getItemBids(Item item) throws ItemNotFoundException {
		Objects.requireNonNull(item, "item must not be null!");
		ItemAndBids iBids = findItemWithBids(item.getItemId());
		return iBids.getBids();
	}

	@Override
	public Bid getItemWinningBid(Item item) throws ItemNotFoundException, BidNotFoundException {
		Objects.requireNonNull(item, "item must not be null!");
		ItemAndBids iBids = findItemWithBids(item.getItemId());
		return iBids.getWinningBids();
	}

	@Override
	public void addBid(Bid bid) throws ItemNotFoundException, UserNotFoundException {
		Objects.requireNonNull(bid, "bid must not be null!");
		UserAndItems uItems = findUserWithItems(bid.getUser().getUserId());
		ItemAndBids iBids = findItemWithBids(bid.getItem().getItemId());

		uItems.addBid(bid);
		iBids.addBid(bid);
	}

	@Override
	public int allUsersCount() {
		return users.size();
	}

	@Override
	public int allItemsCount() {
		return items.size();
	}

	private UserAndItems findUserWithItems(String userId) throws UserNotFoundException {
		UserAndItems uItems = users.get(userId);
		if (uItems == null) {
			LOGGER.info("User with id '{}' not found!", userId);
			throw new UserNotFoundException(String.format("User with id '%s' not found.", userId));
		}
		return uItems;
	}

	private ItemAndBids findItemWithBids(String itemId) throws ItemNotFoundException {
		ItemAndBids iBids = items.get(itemId);
		if (iBids == null) {
			LOGGER.info("Item with id '{}' not found!", itemId);
			throw new ItemNotFoundException(String.format("Item with id '%s' not found.", itemId));
		}
		return iBids;
	}
}
