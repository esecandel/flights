package org.flight.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.flight.dao.AirlineDao;
import org.flight.dao.FlightDao;
import org.flight.model.Airline;
import org.flight.model.Flight;
import org.flight.model.Passenger;
import org.flight.model.PassengerTypeEnum;
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
public class FlightSearchTest {

	@Autowired
	private FlightSearch flightSearch;
	@Autowired
	private FlightService flightService;
	@Autowired
	private FlightDao flightDao;
	@Autowired
	private AirlineDao airlineDao;
	@Value("${csv.all.flights}")
	private String csvAllFlights;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		flightService.remove("{}", new Object[] {});
		flightService.saveFromCsv(csvAllFlights);

		airlineDao.remove("{}", new Object[] {});
		List<Airline> airlines = new ArrayList<Airline>();
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

		airlineDao.insert(airlines);
	}

	@After
	public void tearDown() throws Exception {
		// flightService.remove("{}", new Object[] {});
	}

	@Test
	public void testSortFlights() {
		List<Flight> flights = flightDao.find("{}", new Object[] {});
		Assert.assertNotNull(flights);
		flightSearch.sortFlights(flights);
		Assert.assertNotNull(flights);
		Assert.assertFalse(flights.isEmpty());
		Assert.assertEquals("U29718", flights.get(0).getAirline());
	}

	@Test
	public void testCalculateDatePrice_moreThan30Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -35);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 80D, finalPrice);
	}

	@Test
	public void testCalculateDatePrice_30Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 100D, finalPrice);
	}

	@Test
	public void testCalculateDatePrice_between30And16Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -29);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 100D, finalPrice);
	}

	@Test
	public void testCalculateDatePrice_15Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -15);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 120D, finalPrice);
	}

	@Test
	public void testCalculateDatePrice_between15And3Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 120D, finalPrice);
	}

	@Test
	public void testCalculateDatePrice_3Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 120D, finalPrice);
	}

	@Test
	public void testCalculateDatePrice_lessThan3Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Double finalPrice = flightSearch
				.calculateDatePrice(100D, cal.getTime());
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 150D, finalPrice);
	}

	@Test
	public void testSearch_1Adult_30days_AMS_FRA() {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Passenger passenger = new Passenger(1, PassengerTypeEnum.ADULT);
		passengers.add(passenger);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		List<Flight> flights = flightSearch.search("AMS", "FRA", cal.getTime(), passengers);
		Assert.assertNotNull(flights);
		Assert.assertEquals(3, flights.size());
		Assert.assertEquals("LH5909",flights.get(0).getAirline());
		Assert.assertEquals((Double)113D,flights.get(0).getBasePrice());
	}

	@Test
	public void testSearch_2Adult_1Child_1Infant_15days_AMS_FRA() {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Passenger passenger = new Passenger(2, PassengerTypeEnum.ADULT);
		passengers.add(passenger);
		passenger = new Passenger(1, PassengerTypeEnum.CHILD);
		passengers.add(passenger);
		passenger = new Passenger(1, PassengerTypeEnum.INFANT);
		passengers.add(passenger);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -15);
		List<Flight> flights = flightSearch.search("LHR", "IST", cal.getTime(), passengers);
		Assert.assertNotNull(flights);
		Assert.assertEquals(2, flights.size());
		Assert.assertEquals("LH1085",flights.get(0).getAirline());
		Assert.assertEquals((Double)504.28D,flights.get(0).getBasePrice());
	}

	@Test
	public void testSearch_1Adult_2Child_2days_MAD_BCN() {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Passenger passenger = new Passenger(1, PassengerTypeEnum.ADULT);
		passengers.add(passenger);
		passenger = new Passenger(2, PassengerTypeEnum.CHILD);
		passengers.add(passenger);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		List<Flight> flights = flightSearch.search("BCN", "MAD", cal.getTime(), passengers);
		Assert.assertNotNull(flights);
		Assert.assertEquals(2, flights.size());
		Assert.assertEquals("IB2171",flights.get(0).getAirline());
		Assert.assertEquals((Double)1010.1D,flights.get(0).getBasePrice());
	}
	@Test
	public void testSearch_CDG_FRA() {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Calendar cal = Calendar.getInstance();
		List<Flight> flights = flightSearch.search("CDG", "FRA", cal.getTime(), passengers);
		Assert.assertNull(flights);
	}

	@Test
	public void testCalculateTotalPrice_between30and16Day_2Adult_1Infant() {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Passenger passenger = new Passenger(2, PassengerTypeEnum.ADULT);
		passengers.add(passenger);
		passenger = new Passenger(1, PassengerTypeEnum.INFANT);
		passengers.add(passenger);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -29);
		Double finalPrice = flightSearch.calculateTotalPrice(100D, cal.getTime(), passengers, 10D);
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 210D, finalPrice);
	}

	@Test
	public void testCalculateTotalPrice_between30and16Day_1Adult_1Child() {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Passenger passenger = new Passenger(1, PassengerTypeEnum.ADULT);
		passengers.add(passenger);
		passenger = new Passenger(1, PassengerTypeEnum.CHILD);
		passengers.add(passenger);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -29);
		Double finalPrice = flightSearch.calculateTotalPrice(100D, cal.getTime(), passengers, 10D);
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 180D, finalPrice);
	}

	@Test
	public void testCalculateTypePassengerPrice_Adult() {
		Passenger passenger = new Passenger(1, PassengerTypeEnum.ADULT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -29);
		Double finalPrice = flightSearch.calculateTypePassengerPrice(passenger, 100D, cal.getTime(), 10D);
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 100D, finalPrice);
	}

	@Test
	public void testCalculateTypePassengerPrice_Child() {
		Passenger passenger = new Passenger(1, PassengerTypeEnum.CHILD);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -29);
		Double finalPrice = flightSearch.calculateTypePassengerPrice(passenger, 100D, cal.getTime(), 10D);
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 80D, finalPrice);
	}

	@Test
	public void testCalculateTypePassengerPrice_Infant() {
		Passenger passenger = new Passenger(1, PassengerTypeEnum.INFANT);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -29);
		Double finalPrice = flightSearch.calculateTypePassengerPrice(passenger, 100D, cal.getTime(), 10D);
		Assert.assertNotNull(finalPrice);
		Assert.assertEquals((Double) 10D, finalPrice);
	}

	public void setFlightSearch(FlightSearch flightSearch) {
		this.flightSearch = flightSearch;
	}

	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}

	public void setCsvAllFlights(String csvAllFlights) {
		this.csvAllFlights = csvAllFlights;
	}

	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setAirlineDao(AirlineDao airlineDao) {
		this.airlineDao = airlineDao;
	}

}
