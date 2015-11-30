package org.flight.service;

import java.io.IOException;
import java.util.List;

import org.flight.dao.FlightDao;
import org.flight.model.Flight;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:META-INF/structure-context.xml" })
public class FlightServiceTest {

	@Autowired
	private FlightService flightService;
	@Value("${csv.all.flights}")
	private String csvAllFlights;

	private Flight flight;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		flightService.remove("{}", new Object[] {});

		flight = new Flight();
		flight.setAirline("IB2818");
		flight.setOrigin("CPH");
		flight.setDestination("FRA");
		flight.setBasePrice(186D);

		flightService.save(flight, flight.getAirline());

	}

	@After
	public void tearDown() throws Exception {
		flightService.remove("{}", new Object[] {});
	}

	@Test
	public void testFindById() {
		Flight flight = flightService.findById("IB2818");
		Assert.assertNotNull(flight);
	}
	@Test
	public void testFindById_NotExists() {
		Flight flight = flightService.findById("XXXX");
		Assert.assertNull(flight);
	}

	@Test
	public void testUpdate() {
		flight.setDestination("LHR");
		Integer updates = flightService.update(flight, true, "{"
				+ FlightDao.ID_NAME_FIELD + ":#}", new Object[] { "IB2818" });
		Assert.assertNotNull(updates);
		Assert.assertEquals(1, updates.intValue());

		Flight flight = flightService.findById("IB2818");
		Assert.assertNotNull(flight);
		Assert.assertEquals("LHR", flight.getDestination());
	}

	@Test
	public void testFind() {
		List<Flight> flights = flightService.find("{" + FlightDao.ORIGIN_NAME_FIELD
				+ ":#}", new Object[] { "CPH" });
		Assert.assertNotNull(flights);
		Assert.assertEquals(1, flights.size());
	}
	@Test
	public void testFind_NotExists() {
		List<Flight> flights = flightService.find("{" + FlightDao.ORIGIN_NAME_FIELD
				+ ":#}", new Object[] { "XXX" });
		Assert.assertNotNull(flights);
		Assert.assertEquals(0, flights.size());
	}

	@Test
	public void testSave() {
		flightService.remove("{}", new Object[] {});

		Flight flight = new Flight();
		flight.setAirline("IB2818");
		flight.setOrigin("CPH");
		flight.setDestination("FRA");
		flight.setBasePrice(186D);

		flightService.save(flight, flight.getAirline());

		flight = flightService.findById("IB2818");
		Assert.assertNotNull(flight);
		Assert.assertEquals("CPH", flight.getOrigin());
	}

	@Test
	public void testSaveFromCsv() {

		flightService.remove("{}", new Object[] {});

		List<Flight> flights = null;
		try {
			flights = flightService.saveFromCsv(this.csvAllFlights);
		} catch (IOException e) {
			Assert.fail();
		}
		Assert.assertNotNull(flights);
		Assert.assertFalse(flights.isEmpty());
	}

	@Test
	public void testSaveFromCsv_NotExists() {

		try {
			flightService.saveFromCsv("/wrong/path");
		} catch (IOException e) {
			Assert.assertTrue(true);
		}
	}




	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}

	public void setCsvAllFlights(String csvAllFlights) {
		this.csvAllFlights = csvAllFlights;
	}



}
