package org.flight.dao;

import java.util.ArrayList;
import java.util.List;

import org.flight.model.Flight;
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
public class FlightDaoTest {

	@Autowired
	private FlightDao flightDao;

	private Flight flight;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		flightDao.remove("{}", new Object[] {});

		flight = new Flight();
		flight.setAirline("IB2818");
		flight.setOrigin("CPH");
		flight.setDestination("FRA");
		flight.setBasePrice(186D);

		flightDao.save(flight, flight.getAirline());

	}

	@After
	public void tearDown() throws Exception {
		flightDao.remove("{}", new Object[] {});
	}

	@Test
	public void testFindById() {
		Flight flight = flightDao.findById("IB2818");
		Assert.assertNotNull(flight);

		flight = flightDao.findById("XXX");
		Assert.assertNull(flight);
	}

	@Test
	public void testUpdate() {
		flight.setDestination("LHR");
		Integer updates = flightDao.update(flight, true, "{"
				+ FlightDao.ID_NAME_FIELD + ":#}", new Object[] { "IB2818" });
		Assert.assertNotNull(updates);
		Assert.assertEquals(1, updates.intValue());

		Flight flight = flightDao.findById("IB2818");
		Assert.assertNotNull(flight);
		Assert.assertEquals("LHR", flight.getDestination());
	}

	@Test
	public void testFind() {
		List<Flight> flights = flightDao.find("{" + FlightDao.ORIGIN_NAME_FIELD
				+ ":#}", new Object[] { "CPH" });
		Assert.assertNotNull(flights);
		Assert.assertEquals(1, flights.size());

		flights = flightDao.find("{" + FlightDao.ORIGIN_NAME_FIELD
				+ ":#}", new Object[] { "XXX" });
		Assert.assertNotNull(flights);
		Assert.assertEquals(0, flights.size());

	}

	@Test
	public void testSave() {
		flightDao.remove("{}", new Object[] {});

		Flight flight = new Flight();
		flight.setAirline("IB2818");
		flight.setOrigin("CPH");
		flight.setDestination("FRA");
		flight.setBasePrice(186D);

		flightDao.save(flight, flight.getAirline());

		flight = flightDao.findById("IB2818");
		Assert.assertNotNull(flight);
		Assert.assertEquals("CPH", flight.getOrigin());
	}

	@Test
	public void testFindByOriginAndDestination() {
		List<Flight> flights = new ArrayList<Flight>();

		Flight flight = new Flight();
		flight.setAirline("LH1678");
		flight.setOrigin("CPH");
		flight.setDestination("FRA");
		flight.setBasePrice(298D);
		flights.add(flight);

		flight = new Flight();
		flight.setAirline("U23631");
		flight.setOrigin("CPH");
		flight.setDestination("LHR");
		flight.setBasePrice(186D);
		flights.add(flight);

		flightDao.insert(flights);

		flights = flightDao.findByOriginAndDestination("CPH", "FRA");
		Assert.assertNotNull(flight);
		Assert.assertEquals(2, flights.size());
	}

	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

}
