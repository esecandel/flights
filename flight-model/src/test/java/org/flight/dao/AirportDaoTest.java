package org.flight.dao;

import java.util.List;

import org.flight.model.Airport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:META-INF/structure-context.xml" })
public class AirportDaoTest {

	@Autowired
	private AirportDao airportDao;

	private Airport airport;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		airportDao.remove("{}", new Object[] {});

		airport = new Airport();
		airport.setIataCode("MAD");
		airport.setCity("Madrid");

		airportDao.save(airport, airport.getIataCode());

	}

	@After
	public void tearDown() throws Exception {
		airportDao.remove("{}", new Object[] {});
	}

	@Test
	public void testFindById() {
		Airport airport = airportDao.findById("MAD");
		Assert.assertNotNull(airport);
	}

	@Test
	public void testUpdate() {
		airport.setCity("Nueva Madrid");
		Integer updates = airportDao.update(airport, true, "{"
				+ AirportDao.ID_NAME_FIELD + ":#}", new Object[] { "MAD" });
		Assert.assertNotNull(updates);
		Assert.assertEquals(1, updates.intValue());

		Airport airport = airportDao.findById("MAD");
		Assert.assertNotNull(airport);
		Assert.assertEquals("Nueva Madrid", airport.getCity());
	}

	@Test
	public void testFind() {
		List<Airport> airports = airportDao.find("{" + AirportDao.CITY_FIELD
				+ ":#}", new Object[] { "Madrid" });
		Assert.assertNotNull(airports);
		Assert.assertEquals(1, airports.size());

	}

	@Test
	public void testSave() {
		airportDao.remove("{}", new Object[] {});

		Airport airport = new Airport();
		airport.setIataCode("MAD");
		airport.setCity("Madrid");

		airportDao.save(airport, airport.getIataCode());

		airport = airportDao.findById("MAD");
		Assert.assertNotNull(airport);
		Assert.assertEquals("Madrid", airport.getCity());
	}

	public void setAirportDao(AirportDao airportDao) {
		this.airportDao = airportDao;
	}

}
