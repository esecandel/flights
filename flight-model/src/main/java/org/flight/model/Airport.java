package org.flight.model;

import org.jongo.marshall.jackson.oid.Id;

/**
 * Airport entity
 * @author scandel
 *
 */
public class Airport{

	@Id
	private String iataCode;
	private String city;

	public Airport(String iataCode, String city) {
		super();
		this.iataCode = iataCode;
		this.city = city;
	}

	public Airport() {
		super();
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Airport [iataCode=" + iataCode + ", city=" + city + "]";
	}

}
