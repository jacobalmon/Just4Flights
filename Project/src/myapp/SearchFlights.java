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
	    HttpResponse<String> response = Unirest.get("https://sky-scrapper.p.rapidapi.com/api/v2/flights/searchFlights?originSkyId=" + originSkyId 
	            + "&destinationSkyId=" + destinationSkyId 
	            + "&originEntityId=" + originEntityId 
	            + "&destinationEntityId=" + destinationEntityId 
	            + "&date=" + date 
	            + "&cabinClass=" + flightType 
	            + "&adults=" + numAdults 
	            + "&childrens=" + numChildren 
	            + "&infants=" + numInfants 
	            + "&sortBy=best&currency=USD&market=en-US&countryCode=US")
	        .header("x-rapidapi-key", "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949")
	        .header("x-rapidapi-host", "sky-scrapper.p.rapidapi.com")
	        .asString();

	    JSONObject jsonResponse = new JSONObject(response.getBody());

	    // Check if "data" and "itineraries" are present
	    if (!jsonResponse.has("data") || !jsonResponse.getJSONObject("data").has("itineraries")) {
	        return "No flights found for the specified criteria.";
	    }

	    JSONArray itineraries = jsonResponse.getJSONObject("data").getJSONArray("itineraries");
	    StringBuilder flightDetails = new StringBuilder();

	    // Iterate through each itinerary
	    for (int i = 0; i < itineraries.length(); i++) {
	        JSONObject itinerary = itineraries.getJSONObject(i);

	        // Extract price
	        String price = itinerary.getJSONObject("price").optString("formatted", "N/A");

	        // Extract leg details (assuming one leg per itinerary for simplicity)
	        JSONArray legs = itinerary.getJSONArray("legs");
	        if (legs.length() > 0) {
	            JSONObject leg = legs.getJSONObject(0);

	            // Extract basic details
	            String origin = leg.getJSONObject("origin").optString("name", "N/A");
	            String destination = leg.getJSONObject("destination").optString("name", "N/A");
	            String departureTime = leg.optString("departure", "N/A");
	            String arrivalTime = leg.optString("arrival", "N/A");
	            int duration = leg.optInt("durationInMinutes", 0);
	            int stopCount = leg.optInt("stopCount", 0);

	            // Extract carrier details
	            JSONArray marketingCarriers = leg.getJSONObject("carriers").getJSONArray("marketing");
	            StringBuilder carriers = new StringBuilder();
	            for (int j = 0; j < marketingCarriers.length(); j++) {
	                if (j > 0) carriers.append(", ");
	                carriers.append(marketingCarriers.getJSONObject(j).optString("name", "N/A"));
	            }

	            // Extract flight segments
	            JSONArray segments = leg.getJSONArray("segments");
	            String flightNumber = segments.getJSONObject(0).getJSONObject("marketingCarrier").optString("alternateId", "N/A")
	                    + " " + segments.getJSONObject(0).optString("flightNumber", "N/A");

	            // Append details
	            flightDetails.append("Flight Number: ").append(flightNumber).append("\n")
	                    .append("Airline(s): ").append(carriers).append("\n")
	                    .append("Origin: ").append(origin).append("\n")
	                    .append("Destination: ").append(destination).append("\n")
	                    .append("Departure: ").append(departureTime).append("\n")
	                    .append("Arrival: ").append(arrivalTime).append("\n")
	                    .append("Duration: ").append(duration).append(" minutes\n")
	                    .append("Stops: ").append(stopCount).append("\n")
	                    .append("Price: ").append(price).append("\n\n");
	        }
	    }

	    return flightDetails.length() > 0 ? flightDetails.toString() : "No flights found for the specified criteria.";
	}

}
