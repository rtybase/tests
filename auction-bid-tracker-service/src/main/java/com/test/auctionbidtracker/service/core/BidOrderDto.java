package com.test.auctionbidtracker.service.core;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.validation.ValidationMethod;

public class BidOrderDto {
	@Valid
	@NotEmpty
	@JsonProperty
	private String userId;

	@JsonProperty
	private double bidValue;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getBidValue() {
		return bidValue;
	}

	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}

	@JsonIgnore
	@ValidationMethod(message = "bidValue must be positive.")
	public boolean isValidBidValue() {
		return Double.compare(bidValue, 0.0D) > 0;
	}
}
