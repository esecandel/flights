package org.flight.csvreader.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.flight.csvreader.SuperCSVReader;
import org.flight.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.DMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

@Service
public class SuperCSVReaderImpl implements SuperCSVReader {
	final private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Read from csv file, all flights info
	 * @param filename csv entry
	 * @return list of flights
	 */
	@Override
	public List<Flight> read(String filename) throws IOException{

	        ICsvBeanReader beanReader = null;
	        List<Flight> flights = new ArrayList<Flight>();
	        try {
	                beanReader = new CsvBeanReader(new FileReader(filename), CsvPreference.STANDARD_PREFERENCE);

	                // the header elements are used to map the values to the bean (names must match)
	                final String[] header = beanReader.getHeader(true);
	                final CellProcessor[] processors = getProcessors();

	                Flight flight;
	                while( (flight = beanReader.read(Flight.class, header, processors)) != null ) {
	                        logger.debug(String.format("lineNo=%s, rowNo=%s, customer=%s", beanReader.getLineNumber(),
	                                beanReader.getRowNumber(), flight));
	                        flights.add(flight);
	                }

	        }finally {
	                if( beanReader != null ) {
	                        beanReader.close();
	                }
	        }
	        return flights;
	}

	/**
	 * Sets up the processors used for Flights.
	 *
	 * @return the cell processors
	 */
	private CellProcessor[] getProcessors() {

	        final CellProcessor[] processors = new CellProcessor[] {
	                new NotNull(), // origin
	                new NotNull(), // dest
	                new UniqueHashCode(), // airline
	                new DMinMax(0D, DMinMax.MAX_DOUBLE) // basePrice
	        };
	        return processors;
	}
}
