/**
 *
 */
package org.flight.dao;

import java.util.List;

import org.flight.model.Flight;
import org.springframework.stereotype.Repository;

/**
 * @author scandel
 *
 */
@Repository
public interface FlightDao extends GenericDao<Flight, String>{

	//Atributes field names
	String ID_NAME_FIELD = "_id";
	String ORIGIN_NAME_FIELD = "origin";
	String DESTINATION_NAME_FIELD = "destination";
	String BASE_PRICE_NAME_FIELD = "base_price";

	List<Flight> findByOriginAndDestination(String origin, String destination);
}
