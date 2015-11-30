package org.flight.service.impl;

import java.io.IOException;
import java.util.List;

import org.flight.csvreader.SuperCSVReader;
import org.flight.dao.FlightDao;
import org.flight.model.Flight;
import org.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private SuperCSVReader superCSVReader;

	@Override
	public List<Flight> findByElementIs(String nameElment, Object value) {
		return flightDao.findByElementIs(nameElment, value);
	}

	/**
	 * Find a flight using its unique id
	 *
	 * @param key
	 *            unique identifier
	 * @return found flight
	 */
	@Override
	public Flight findById(String key) {
		return flightDao.findById(key);
	}

	/**
	 * Save a flight object using a unique key
	 *
	 * @param object
	 *            flight to save
	 * @param key
	 *            unique identifier
	 */
	@Override
	@Transactional
	public void save(Flight object, String defaultKey) {
		flightDao.save(object, defaultKey);
	}

	/**
	 * Update a flight object.
	 *
	 * @param object
	 *            flight
	 * @param upsert
	 *            true to create if doesn't exist
	 * @param query
	 *            to search entity to update
	 * @param queryParams
	 *            params for query
	 * @return number of updated documents
	 */
	@Override
	@Transactional
	public Integer update(Flight object, boolean upsert, String query,
			Object[] queryParams) {
		return flightDao.update(object, upsert, query, queryParams);
	}

	/**
	 * Find flight using query
	 *
	 * @param query
	 *            to search entity to update
	 * @param queryParams
	 *            params for query
	 * @return list of found flights
	 */
	@Override
	public List<Flight> find(String query, Object[] queryParams) {
		return flightDao.find(query, queryParams);
	}

	/**
	 * Delete flight using query
	 *
	 * @param query
	 *            to delete entities
	 * @param queryParams
	 *            params for query
	 */
	@Override
	@Transactional
	public void remove(String query, Object[] params) {
		flightDao.remove(query, params);

	}

	/**
	 * Read all flights from csv and save them into DB
	 *
	 * @param filename
	 *            csv
	 * @return list of stored flights
	 */
	@Override
	public List<Flight> saveFromCsv(String filename) throws IOException {
		List<Flight> flights = superCSVReader.read(filename);
		flightDao.insert(flights);
		return flights;
	}


	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}
}
