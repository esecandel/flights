/**
 *
 */
package org.flight.dao;

import org.flight.model.Airline;
import org.springframework.stereotype.Repository;

/**
 * @author scandel
 *
 */
@Repository
public interface AirlineDao extends GenericDao<Airline, String>{

	//Atributes field names
	String ID_NAME_FIELD = "_id";
	String NAME_FIELD = "name";
	Double getInfantPrice(String iataCode);
}
