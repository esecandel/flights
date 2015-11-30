package org.flight.service;

import java.io.IOException;
import java.util.List;

import org.flight.model.Flight;

public interface FlightService {


	List<Flight> findByElementIs(String nameElment, Object value);

	Flight findById(String key);

	void save(Flight object, String defaultKey);

	Integer update(Flight object, boolean upsert, String query, Object[] queryParams);

	List<Flight> find(String query, Object[] queryParams);

	void remove(String query, Object[] params);

	List<Flight> saveFromCsv(String filename) throws IOException;

}
