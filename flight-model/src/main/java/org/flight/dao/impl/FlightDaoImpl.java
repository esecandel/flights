package org.flight.dao.impl;

import java.util.List;

import org.flight.dao.FlightDao;
import org.flight.dao.GenericMongoDao;
import org.flight.model.Flight;

import com.mongodb.DB;

public class FlightDaoImpl extends GenericMongoDao<Flight, String>
		implements FlightDao {

	protected FlightDaoImpl(DB db, String collectionName) {
		super(db, collectionName);
	}

	@Override
	protected String getKeyFieldName() {
		return FlightDao.ID_NAME_FIELD;
	}

	@Override
	protected Class<Flight> getClassType() {
		return Flight.class;
	}

	@Override
	protected Flight addMongoId(Flight object, String mongoId) {
		return object;
	}

	public void createIndex() {
	}

	@Override
	public List<Flight> findByOriginAndDestination(String origin, String destination){
		return this.find("{"+ORIGIN_NAME_FIELD+":#,"+DESTINATION_NAME_FIELD+":#}", new Object[]{origin,destination});
	}

}
