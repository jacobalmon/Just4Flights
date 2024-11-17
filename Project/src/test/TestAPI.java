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
}