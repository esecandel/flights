package org.flight.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.flight.dao.AirlineDao;
import org.flight.dao.FlightDao;
import org.flight.model.Flight;
import org.flight.model.Passenger;
import org.flight.service.FlightSearch;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightSearchImpl implements FlightSearch {

	// 80% of the base price
	private static final Double MORETHAN30DAYS = 0.8D;
	// 100% of the base price
	private static final Double BETWEEN30AND16DAYS = 1D;
	// 120% of the base price
	private static final Double BETWEEN15AND3DAYS = 1.2D;
	// 150% of the base price
	private static final Double LESSTHAN3DAYS = 1.5D;

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private AirlineDao airlineDao;

	/**
	 * Method that calculate all possible flights and its prices according date,
	 * passengers and airlines, and sort them is ascending way.
	 *
	 * @param origin
	 *            airport
	 * @param destination
	 *            airport
	 * @param dateOfFlight
	 *            date of flight
	 * @param passengers
	 *            list with type and number of passengers
	 * @return all flights with final calculated prices
	 */
	@Override
	public List<Flight> search(String origin, String destination,
			Date dateOfFlight, List<Passenger> passengers) {
		List<Flight> flights = flightDao.findByOriginAndDestination(origin,
				destination);
		List<Flight> flightsCalculated = new ArrayList<>();
		if (flights != null && !flights.isEmpty()) {
			for (Flight flight : flights) {
				Flight flightCalculated = new Flight();
				BeanUtils.copyProperties(flight, flightCalculated);
				Double finalPrice = this.calculateTotalPrice(
						flight.getBasePrice(), dateOfFlight, passengers,
						airlineDao.getInfantPrice(flight.getAirline()));
				flightCalculated.setBasePrice(finalPrice);
				flightsCalculated.add(flightCalculated);
			}
			//Sort ascending price of flights
			this.sortFlights(flightsCalculated);
			return flightsCalculated;
		}else{
			return null;
		}

	}

	/**
	 * Method that calculate total price of a flight according all passengers
	 * and to the days to departure date rule
	 *
	 * @param passengers
	 *            list with all passengers
	 * @param basePrice
	 * @param dateOfFlight
	 * @return total new price according the age of passenger and date of departure
	 */
	@Override
	public Double calculateTotalPrice(Double basePrice, Date dateOfFlight,
			List<Passenger> passengers, Double infantPrice) {
		Double total = 0D;
		for (Passenger passenger : passengers) {
			total += this.calculateTypePassengerPrice(passenger, basePrice,
					dateOfFlight, infantPrice);
		}
		return total;
	}

	/**
	 * Method that calculate the price calculated according the age of passenger
	 * and to the days to departure date rule
	 *
	 * @param passenger
	 *            number of passgener
	 * @param basePrice
	 * @param dateOfFlight
	 * @return new price according the age of passenger and date of departure
	 */
	@Override
	public Double calculateTypePassengerPrice(Passenger passenger,
			Double basePrice, Date dateOfFlight, Double infantPrice) {
		switch (passenger.getType()) {
		case INFANT:
			return infantPrice * passenger.getNumberOfPassengers();
		case CHILD:
			return (0.8D * (Double) this.calculateDatePrice(basePrice,
					dateOfFlight)) * passenger.getNumberOfPassengers();
		default:
			return ((Double) this.calculateDatePrice(basePrice, dateOfFlight))
					* passenger.getNumberOfPassengers();
		}
	}

	/**
	 * Method that calculate the price calculated according to the days to
	 * departure date rule
	 *
	 * @param price
	 *            initial price
	 * @param dateOfFlight
	 *            date of flight
	 * @return new price according date of departure
	 */
	@Override
	public Double calculateDatePrice(Double price, Date dateOfFlight) {
		long diff = new Date().getTime() - dateOfFlight.getTime();
		long numberOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if (numberOfDays > 30L) {
			return price * MORETHAN30DAYS;
		} else if (numberOfDays <= 30L && numberOfDays >= 16L) {
			return price * BETWEEN30AND16DAYS;
		} else if (numberOfDays <= 15L && numberOfDays >= 3L) {
			return price * BETWEEN15AND3DAYS;
		} else {
			return price * LESSTHAN3DAYS;
		}
	}

	/**
	 * Sort a list of flights based on base price attribute
	 *
	 * @param flights
	 *            to sort
	 * @return sorted flights
	 */
	@Override
	public void sortFlights(List<Flight> flights) {
		Comparator<Flight> comparator = new Comparator<Flight>() {
			@Override
			public int compare(final Flight f1, final Flight f2) {
				return f1.getBasePrice().compareTo(f2.getBasePrice());
			}
		};
		Collections.sort(flights, comparator);
	}

	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setAirlineDao(AirlineDao airlineDao) {
		this.airlineDao = airlineDao;
	}

}
