package org.flight.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.flight.dao.AirlineDao;
import org.flight.dao.GenericMongoDao;
import org.flight.model.Airline;

import com.mongodb.DB;

public class AirlineDaoImpl extends GenericMongoDao<Airline, String> implements
		AirlineDao {

	protected AirlineDaoImpl(DB db, String collectionName) {
		super(db, collectionName);
	}

	@Override
	protected String getKeyFieldName() {
		return AirlineDao.ID_NAME_FIELD;
	}

	@Override
	protected Class<Airline> getClassType() {
		return Airline.class;
	}

	@Override
	protected Airline addMongoId(Airline object, String mongoId) {
		return object;
	}

	public void createIndex() {
	}

	@PostConstruct
	public void create() {
		List<Airline> airlines = this.find("{}", new Object[] {});
		if (airlines == null || (airlines != null && airlines.isEmpty())) {
			airlines = new ArrayList<Airline>();
			Airline airline = new Airline("IB", "Iberia", 10D);
			airlines.add(airline);
			airline = new Airline("BA", "British Airways", 15D);
			airlines.add(airline);
			airline = new Airline("LH", "Lufthansa", 7D);
			airlines.add(airline);
			airline = new Airline("RY", "Ryanair", 20D);
			airlines.add(airline);
			airline = new Airline("VU", "Vueling", 10D);
			airlines.add(airline);
			airline = new Airline("TK", "Turkish Airlines", 5D);
			airlines.add(airline);
			airline = new Airline("U2", "Easyjet", 19.90D);
			airlines.add(airline);
			this.insert(airlines);
		}
	}

	/**
	 * Return infant price for iataCode of airline. IataCode can be flight code,
	 * ex: IB2818, or airline code, ex: IB. If airline is not found, return
	 * null.
	 *
	 * @param iataCode
	 *            flight code or airline code
	 * @return price of infants seat
	 */
	@Override
	public Double getInfantPrice(String iataCode) {
		String id = iataCode.length() > 2 ? iataCode.substring(0, 2) : iataCode;

		Airline airline = this.findById(id);
		if (airline != null) {
			return airline.getInfantPrice();
		} else {
			return null;
		}
	}
}
