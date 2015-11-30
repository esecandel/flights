package org.flight.model;

public class Passenger {


	private int numberOfPassengers;
	private PassengerTypeEnum type;

	public Passenger() {
		super();
	}

	public Passenger(int numberOfPassengers, PassengerTypeEnum type) {
		super();
		this.numberOfPassengers = numberOfPassengers;
		this.type = type;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public PassengerTypeEnum getType() {
		return type;
	}

	public void setType(PassengerTypeEnum type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Passenger [numberOfPassengers=" + numberOfPassengers + ", type="
				+ type + "]";
	}

}
