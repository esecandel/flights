package org.flight.model;

import org.jongo.marshall.jackson.oid.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Airline entity
 * @author scandel
 *
 */
public class Airline {

	@Id
	private String iataCode;
	private String name;
	@JsonProperty("infant_price")
	private Double infantPrice;



	public Airline(String iataCode, String name, Double infantPrice) {
		super();
		this.iataCode = iataCode;
		this.name = name;
		this.infantPrice = infantPrice;
	}

	public Airline() {
		super();
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getInfantPrice() {
		return infantPrice;
	}

	public void setInfantPrice(Double infantPrice) {
		this.infantPrice = infantPrice;
	}

	@Override
	public String toString() {
		return "Airline [iataCode=" + iataCode + ", name=" + name
				+ ", infantPrice=" + infantPrice + "]";
	}

}
