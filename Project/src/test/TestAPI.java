package test;

import org.junit.jupiter.api.Test;
import com.mashape.unirest.http.exceptions.UnirestException;
import myapp.SearchFlights;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for testing the SearchFlights API methods.
 * 
 * This class contains tests to ensure the correctness of airport data retrieval and flight search
 * functionality in the SearchFlights class.
 */
class TestAPI {

    /**
     * Test case to verify the airport details for Seattle.
     * 
     * This test sends a request to the SearchFlights API to retrieve airport information for Seattle,
     * then asserts that the response matches the expected data for the airport.
     * 
     * @throws UnirestException if an error occurs while making the HTTP request.
     */
    @Test
    public void Airport1() throws UnirestException {
        String loc = "Seattle";
        String response = SearchFlights.getAirport(loc);
        String expected = "SkyId: SEAA, EntityId: 27538444";
        assertEquals(response, expected, "Id's should match.");
    }

    /**
     * Test case to verify the airport details for San Diego.
     * 
     * This test sends a request to the SearchFlights API to retrieve airport information for San Diego,
     * then asserts that the response matches the expected data for the airport.
     * 
     * @throws UnirestException if an error occurs while making the HTTP request.
     */
    @Test
    public void Airport2() throws UnirestException {
        String loc = "San Diego";
        String response = SearchFlights.getAirport(loc);
        String expected = "SkyId: SANA, EntityId: 27545066";
        assertEquals(response, expected, "Id's should match.");
    }

    /**
     * Test case to verify the airport details for New York City.
     * 
     * This test sends a request to the SearchFlights API to retrieve airport information for New York City,
     * then asserts that the response matches the expected data for the airport.
     * 
     * @throws UnirestException if an error occurs while making the HTTP request.
     */
    @Test
    public void Airport3() throws UnirestException {
        String loc = "New York City";
        String response = SearchFlights.getAirport(loc);
        String expected = "SkyId: NYCA, EntityId: 27537542";
        assertEquals(response, expected, "Id's should match.");
    }

    /**
     * Test case to verify the flight search functionality.
     * 
     * This test sends a request to the SearchFlights API to search for a flight between Seattle and San Diego
     * for the given date and passenger count. It then checks if the response contains a flight number.
     * 
     * @throws UnirestException if an error occurs while making the HTTP request.
     */
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