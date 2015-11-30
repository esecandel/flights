package org.flight.csvreader;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.flight.model.Flight;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:META-INF/structure-context.xml" })
public class SuperCsvReaderTest extends TestCase {

	@Autowired
	private SuperCSVReader superCSVReader;


	@Test
	public void testRead() throws IOException {
		List<Flight> flights = superCSVReader.read("src/main/resources/flights.csv");

		Assert.assertNotNull(flights);
		Assert.assertFalse(flights.isEmpty());

	}

}
