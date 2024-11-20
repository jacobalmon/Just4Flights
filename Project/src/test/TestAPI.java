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
		String expected = "Flight Number: DL 2572\n" +
			    "Airline(s): Delta\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T11:39:00\n" +
			    "Arrival: 2024-12-25T14:37:00\n" +
			    "Duration: 178 minutes\n" +
			    "Stops: 0\n" +
			    "Price: $416\n\n" +
			    
			    "Flight Number: KE 2572\n" +
			    "Airline(s): Korean Air\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T11:39:00\n" +
			    "Arrival: 2024-12-25T14:37:00\n" +
			    "Duration: 178 minutes\n" +
			    "Stops: 0\n" +
			    "Price: $644\n\n" +
			    
			    "Flight Number: KE 2672\n" +
			    "Airline(s): Korean Air\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T17:30:00\n" +
			    "Arrival: 2024-12-25T20:24:00\n" +
			    "Duration: 174 minutes\n" +
			    "Stops: 0\n" +
			    "Price: $845\n\n" +
			    
			    "Flight Number: AS 2250\n" +
			    "Airline(s): Alaska Airlines\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T08:02:00\n" +
			    "Arrival: 2024-12-25T16:24:00\n" +
			    "Duration: 502 minutes\n" +
			    "Stops: 1\n" +
			    "Price: $414\n\n" +
			    
			    "Flight Number: DL 2508\n" +
			    "Airline(s): Delta\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T07:30:00\n" +
			    "Arrival: 2024-12-25T10:31:00\n" +
			    "Duration: 181 minutes\n" +
			    "Stops: 0\n" +
			    "Price: $701\n\n" +
			    
			    "Flight Number: AS 1318\n" +
			    "Airline(s): Alaska Airlines\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T17:54:00\n" +
			    "Arrival: 2024-12-25T23:29:00\n" +
			    "Duration: 335 minutes\n" +
			    "Stops: 1\n" +
			    "Price: $653\n\n" +
			    
			    "Flight Number: AS 1156\n" +
			    "Airline(s): Alaska Airlines\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T21:10:00\n" +
			    "Arrival: 2024-12-25T23:59:00\n" +
			    "Duration: 169 minutes\n" +
			    "Stops: 0\n" +
			    "Price: $895\n\n" +
			    
			    "Flight Number: AS 1140\n" +
			    "Airline(s): Alaska Airlines\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T15:44:00\n" +
			    "Arrival: 2024-12-25T20:16:00\n" +
			    "Duration: 272 minutes\n" +
			    "Stops: 1\n" +
			    "Price: $659\n\n" +
			    
			    "Flight Number: KE 2508\n" +
			    "Airline(s): Korean Air\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T07:30:00\n" +
			    "Arrival: 2024-12-25T10:31:00\n" +
			    "Duration: 181 minutes\n" +
			    "Stops: 0\n" +
			    "Price: $808\n\n" +
			    
			    "Flight Number: NK 414\n" +
			    "Airline(s): Spirit Airlines\n" +
			    "Origin: Seattle / Tacoma International\n" +
			    "Destination: San Diego International\n" +
			    "Departure: 2024-12-25T14:16:00\n" +
			    "Arrival: 2024-12-25T22:12:00\n" +
			    "Duration: 476 minutes\n" +
			    "Stops: 1\n" +
			    "Price: $518\n\n";
		assertEquals(response, expected, "Response should Match");
	}
} 