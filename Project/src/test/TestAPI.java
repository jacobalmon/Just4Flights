package test;

import org.junit.jupiter.api.Test;
import com.mashape.unirest.http.exceptions.UnirestException;
import myapp.SearchFlights;
import static org.junit.jupiter.api.Assertions.*;

class TestAPI {
	@Test
	public void Airport1() throws UnirestException {
		String loc = "Seattle";
		String response = SearchFlights.getAirport(loc);
		String expected = "SkyId: SEAA, EntityId: 27538444";
		assertEquals(response, expected, "Id's should match.");
	}
	
	@Test
	public void Airport2() throws UnirestException {
		String loc = "San Diego";
		String response = SearchFlights.getAirport(loc);
		String expected = "SkyId: SANA, EntityId: 27545066";
		assertEquals(response, expected, "Id's should match.");
	}
	
	@Test
	public void Airport3() throws UnirestException {
		String loc = "New York City";
		String response = SearchFlights.getAirport(loc);
		String expected = "SkyId: NYCA, EntityId: 27537542";
		assertEquals(response, expected, "Id's should match.");
	}
	
	@Test
	public void Flight1() throws UnirestException {
		String originSkyId = "SEAA";
		String originEntityId = "27538444";
		String destinationSkyId = "SANA";
		String destinationEntityId = "27545066";
		String date = "2024-12-25";
		int adults = 1;
		int children = 2;
		int infants = 0;
		String type = "economy";
		
		String response = SearchFlights.getFlight(originSkyId, destinationSkyId, originEntityId, destinationEntityId, date, adults, children, infants, type);
		String expected = "Flight Number: ";
		int index = response.indexOf("Flight Number: ");
		response = response.substring(index, index + expected.length());
		assertEquals(response, expected, "Response should Match");
	}
} 