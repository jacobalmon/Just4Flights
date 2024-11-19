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
} 