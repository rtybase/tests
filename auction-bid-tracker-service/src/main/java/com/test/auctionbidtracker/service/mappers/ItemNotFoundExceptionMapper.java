package com.test.auctionbidtracker.service.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.test.auctionbidtracker.service.api.ItemNotFoundException;

import io.dropwizard.jersey.errors.ErrorMessage;

public class ItemNotFoundExceptionMapper implements ExceptionMapper<ItemNotFoundException> {
	@Override
	public Response toResponse(ItemNotFoundException exception) {
		return Response.status(Status.NOT_FOUND)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(new ErrorMessage(Status.NOT_FOUND.getStatusCode(), exception.getMessage()))
				.build();
	}
}
