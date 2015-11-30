package org.flight.csvreader;

import java.io.IOException;
import java.util.List;

import org.flight.model.Flight;

public interface SuperCSVReader {

	List<Flight> read(String filename)throws IOException;

}
