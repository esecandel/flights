package org.flight.dao;

import java.util.List;

import org.flight.model.Airline;
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
public class AirlineDaoTest {

	@Autowired
	private AirlineDao airlineDao;

	private Airline airline;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		airlineDao.remove("{}", new Object[] {});

		airline = new Airline();
		airline.setIataCode("IB");
		airline.setInfantPrice(10D);
		airline.setName("Iberia");

		airlineDao.save(airline, airline.getIataCode());

	}

	@After
	public void tearDown() throws Exception {
		airlineDao.remove("{}", new Object[] {});
	}

	@Test
	public void testFindById() {
		Airline airline = airlineDao.findById("IB");
		Assert.assertNotNull(airline);
	}

	@Test
	public void testUpdate() {
		airline.setName("Nueva Iberia");
		Integer updates = airlineDao.update(airline, true, "{"
				+ AirlineDao.ID_NAME_FIELD + ":#}", new Object[] { "IB" });
		Assert.assertNotNull(updates);
		Assert.assertEquals(1, updates.intValue());

		Airline airline = airlineDao.findById("IB");
		Assert.assertNotNull(airline);
		Assert.assertEquals("Nueva Iberia", airline.getName());
	}

	@Test
	public void testFind() {
		List<Airline> airlines = airlineDao.find("{" + AirlineDao.NAME_FIELD
				+ ":#}", new Object[] { "Iberia" });
		Assert.assertNotNull(airlines);
		Assert.assertEquals(1, airlines.size());

	}

	@Test
	public void testSave() {
		airlineDao.remove("{}", new Object[] {});

		Airline airline = new Airline();
		airline.setIataCode("IB");
		airline.setInfantPrice(10D);
		airline.setName("Iberia");

		airlineDao.save(airline, airline.getIataCode());

		airline = airlineDao.findById("IB");
		Assert.assertNotNull(airline);
		Assert.assertEquals("Iberia", airline.getName());
	}

	@Test
	public void testGetInfantPrice() {
		Double priceInfant= airlineDao.getInfantPrice(airline.getIataCode());

		Assert.assertNotNull(priceInfant);
		Assert.assertEquals((Double)10D, priceInfant);

		priceInfant= airlineDao.getInfantPrice("IB2818");

		Assert.assertNotNull(priceInfant);
		Assert.assertEquals((Double)10D, priceInfant);

		priceInfant= airlineDao.getInfantPrice("XXX");

		Assert.assertNull(priceInfant);
	}



	public void setAirlineDao(AirlineDao airlineDao) {
		this.airlineDao = airlineDao;
	}

}
