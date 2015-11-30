package org.flight.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.flight.dao.AirportDao;
import org.flight.dao.GenericMongoDao;
import org.flight.model.Airport;

import com.mongodb.DB;

public class AirportDaoImpl extends GenericMongoDao<Airport, String>
		implements AirportDao {

	protected AirportDaoImpl(DB db, String collectionName) {
		super(db, collectionName);
	}

	@Override
	protected String getKeyFieldName() {
		return AirportDao.ID_NAME_FIELD;
	}

	@Override
	protected Class<Airport> getClassType() {
		return Airport.class;
	}

	@Override
	protected Airport addMongoId(Airport object, String mongoId) {
		return object;
	}

	public void createIndex() {
	}

	@PostConstruct
	public void create() {
		List<Airport> airports = this.find("{}", new Object[] {});
		if (airports == null || (airports != null && airports.isEmpty())) {
			airports = new ArrayList<Airport>();
			Airport airport = new Airport("MAD", "Madrid");
			airports.add(airport);
			airport = new Airport("BCN", "Barcelona");
			airports.add(airport);
			airport = new Airport("LHR", "London");
			airports.add(airport);
			airport = new Airport("CDG", "Paris");
			airports.add(airport);
			airport = new Airport("FRA", "Frankfurt");
			airports.add(airport);
			airport = new Airport("IST", "Istambul");
			airports.add(airport);
			airport = new Airport("AMS", "Amsterdam");
			airports.add(airport);
			airport = new Airport("FCO", "Rome");
			airports.add(airport);
			airport = new Airport("CPH", "Copenhagen");
			airports.add(airport);
			this.insert(airports);
		}
	}
}
