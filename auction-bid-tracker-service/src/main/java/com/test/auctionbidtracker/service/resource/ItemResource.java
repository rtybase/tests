package com.test.auctionbidtracker.service.resource;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.test.auctionbidtracker.service.api.AuctionBidTracker;
import com.test.auctionbidtracker.service.api.BidNotFoundException;
import com.test.auctionbidtracker.service.api.ItemNotFoundException;
import com.test.auctionbidtracker.service.api.UserNotFoundException;
import com.test.auctionbidtracker.service.core.Bid;
import com.test.auctionbidtracker.service.core.BidOrderDto;
import com.test.auctionbidtracker.service.core.Item;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(ItemResource.PATH)
public class ItemResource {
	static final String PATH = "/v1/items/";
	static final String ITEM_BIDS_PATH = "/{itemId}/bids";
	static final String ITEM_BEST_BID_PATH = "/{itemId}/bestbid";

	private final AuctionBidTracker auctionBidTracker;

	public ItemResource(AuctionBidTracker auctionBidTracker) {
		this.auctionBidTracker = Objects.requireNonNull(auctionBidTracker, "auctionBidTracker must not be null!");
	}

	@POST
	@Timed
	public Response addItem(@Valid Item item) {
		Item createdItem = auctionBidTracker.addItem(item);
		return Response.created(URI.create(createdItem.getItemId())).entity(createdItem).build();
	}

	@GET
	@Timed
	public Collection<Item> getAllItems() {
		return auctionBidTracker.allItems();
	}

	@GET
	@Path(ITEM_BIDS_PATH)
	@Timed
	public Collection<Bid> getItemBids(@PathParam("itemId") @NotNull String itemId) throws ItemNotFoundException {
		return auctionBidTracker.getItemBids(auctionBidTracker.getItemById(itemId));
	}

	@POST
	@Path(ITEM_BIDS_PATH)
	@Timed
	public Response addBid(@PathParam("itemId") @NotNull String itemId, @Valid BidOrderDto bidOrder)
			throws ItemNotFoundException, UserNotFoundException {
		auctionBidTracker.addBid(new Bid(auctionBidTracker.getItemById(itemId),
				auctionBidTracker.getUserById(bidOrder.getUserId()), bidOrder.getBidValue()));
		return Response.noContent().build();
	}

	@GET
	@Path(ITEM_BEST_BID_PATH)
	@Timed
	public Bid getItemWinninBid(@PathParam("itemId") @NotNull String itemId)
			throws ItemNotFoundException, BidNotFoundException {
		return auctionBidTracker.getItemWinningBid(auctionBidTracker.getItemById(itemId));
	}
}
