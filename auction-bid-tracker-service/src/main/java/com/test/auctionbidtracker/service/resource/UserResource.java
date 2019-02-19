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
import com.test.auctionbidtracker.service.api.UserNotFoundException;
import com.test.auctionbidtracker.service.core.Item;
import com.test.auctionbidtracker.service.core.User;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path(UserResource.PATH)
public class UserResource {
	static final String PATH = "/v1/users/";
	static final String USER_ITEMS_PATH = "/{userId}/items";

	private final AuctionBidTracker auctionBidTracker;

	public UserResource(AuctionBidTracker auctionBidTracker) {
		this.auctionBidTracker = Objects.requireNonNull(auctionBidTracker, "auctionBidTracker must not be null!");
	}

	@POST
	@Timed
	public Response addUser(@Valid User user) {
		User createdUser = auctionBidTracker.addUser(user);
		return Response.created(URI.create(createdUser.getUserId())).entity(createdUser).build();
	}

	@GET
	@Timed
	public Collection<User> getAllUsers() {
		return auctionBidTracker.allUsers();
	}

	@GET
	@Path(USER_ITEMS_PATH)
	@Timed
	public Collection<Item> getAllUserItems(@PathParam("userId") @NotNull String userId) throws UserNotFoundException {
		return auctionBidTracker.getUserBidItems(auctionBidTracker.getUserById(userId));
	}
}
