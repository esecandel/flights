package org.flight.model;

import org.jongo.marshall.jackson.oid.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Flight entity
 * @author scandel
 *
 */
public class Flight {

	@Id
	private String airline;
	private String origin;
	private String destination;
	@JsonProperty("base_price")
	private Double basePrice;

	public Flight() {
		super();
	}
	public Flight(String airline, String origin, String destination,
			Double basePrice) {
		super();
		this.airline = airline;
		this.origin = origin;
		this.destination = destination;
		this.basePrice = basePrice;
	}

	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	@Override
	public String toString() {
		return "Flight [airline=" + airline + ", origin=" + origin
				+ ", destination=" + destination + ", basePrice=" + basePrice
				+ "]";
	}


}
