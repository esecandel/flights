package org.flight.service;

import java.util.Date;
import java.util.List;

import org.flight.model.Flight;
import org.flight.model.Passenger;

public interface FlightSearch {

	void sortFlights(List<Flight> flights);

	List<Flight> search(String origin, String destination,
			Date dateOfFlight, List<Passenger> passengers);

	Double calculateDatePrice(Double basePrice, Date dateOfFlight);

	Double calculateTotalPrice(Double basePrice, Date dateOfFlight,
			List<Passenger> passengers, Double infantPrice);


	Double calculateTypePassengerPrice(Passenger passenger, Double basePrice,
			Date dateOfFlight, Double infantPrice);
}
