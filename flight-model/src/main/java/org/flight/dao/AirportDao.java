/**
 *
 */
package org.flight.dao;

import org.flight.model.Airport;
import org.springframework.stereotype.Repository;

/**
 * @author scandel
 *
 */
@Repository
public interface AirportDao extends GenericDao<Airport, String>{

	//Atributes field names
	String ID_NAME_FIELD = "_id";
	String CITY_FIELD = "city";
}
