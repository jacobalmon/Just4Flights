package myapp;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;

public class SearchFlights {
	// API Data Members.
	private static final String host = "https://rapidapi.com/apiheya/api/sky-scrapper";
	private static final String charSet = "UTF-8";
	private static final String x_rapidapi_host = "sky-scrapper.p.rapidapi.com";
	private static final String x_rapidapi_key = "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949";
		
	public static String getAirport(String loc) throws UnirestException {
	    try {
	        // Encode the location string to handle spaces and special characters.
	        String encodedLoc = URLEncoder.encode(loc, StandardCharsets.UTF_8.toString());

	        // Call API for location-based Airport IDs.
	        HttpResponse<String> response = Unirest.get("https://sky-scrapper.p.rapidapi.com/api/v1/flights/searchAirport?query=" + encodedLoc + "&locale=en-US")
	                .header("x-rapidapi-key", "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949")
	                .header("x-rapidapi-host", "sky-scrapper.p.rapidapi.com")
	                .asString();

	        // Parse JSON response.
	        JSONObject jsonResponse = new JSONObject(response.getBody());
	        JSONArray airports = jsonResponse.optJSONArray("data");

	        System.out.println(jsonResponse);

	        // Check if the API returned any airports.
	        if (airports != null && airports.length() > 0) {
	            JSONObject firstAirport = airports.getJSONObject(0);
	            String skyId = firstAirport.optString("skyId", "N/A");
	            String entityId = firstAirport.optString("entityId", "N/A");

	            return "SkyId: " + skyId + ", EntityId: " + entityId;
	        } else {
	            return "No airport found for the location.";
	        }
	    } catch (Exception e) {
	        // Handle encoding or other unexpected errors.
	        return "Error: " + e.getMessage();
	    }
	}
	
	public static String getFlight(String originSkyId, String destinationSkyId, String originEntityId, String destinationEntityId, String date, int numAdults, int numChildren, int numInfants, String flightType) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://sky-scrapper.p.rapidapi.com/api/v2/flights/searchFlights?originSkyId=" + originSkyId + "&destinationSkyId=" + destinationSkyId + "&originEntityId=" + originEntityId + "&destinationEntityId=" + destinationEntityId + "&date=" + date + "&cabinClass=" + flightType + "&adults=" + numAdults + "&childrens=" + numChildren + "&infants=" + numInfants + "&sortBy=best&currency=USD&market=en-US&countryCode=US")
				.header("x-rapidapi-key", "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949")
				.header("x-rapidapi-host", "sky-scrapper.p.rapidapi.com")
				.asString();
		
		JSONObject jsonResponse = new JSONObject(response.getBody());
		JSONArray flights = jsonResponse.getJSONArray("data");
		
		// Check if API returned any flights.
		if (flights.length() > 0) {
			StringBuilder flightDetails = new StringBuilder();
			
			// Loop Through each flight and extract details.
			for (int i = 0; i < flights.length(); ++i) {
					JSONObject flight = flights.getJSONObject(i);
				
				    String flightNumber = flight.optString("flightNumber", "N/A");
		            String airline = flight.optString("airline", "N/A");
		            String departureTime = flight.optString("departureTime", "N/A");
		            String arrivalTime = flight.optString("arrivalTime", "N/A");
		            String price = flight.optString("price", "N/A");
		            String duration = flight.optString("duration", "N/A");
		            String status = flight.optString("status", "N/A");
		            
		            flightDetails.append("Flight Number: ").append(flightNumber).append("\n")
                    	.append("Airline: ").append(airline).append("\n")
                    	.append("Departure Time: ").append(departureTime).append("\n")
                    	.append("Arrival Time: ").append(arrivalTime).append("\n")
                    	.append("Price: ").append(price).append("\n")
                    	.append("Duration: ").append(duration).append("\n")
                    	.append("Status: ").append(status).append("\n\n");
			}
			return flightDetails.toString();
		} else {
			return "No flights found for the specified criteria.";
		}
	}
}
