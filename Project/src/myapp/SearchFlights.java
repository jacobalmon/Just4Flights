package myapp;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.json.JSONArray;

public class SearchFlights {
	// API Data Members.
	private static final String host = "https://rapidapi.com/apiheya/api/sky-scrapper";
	private static final String charSet = "UTF-8";
	private static final String x_rapidapi_host = "sky-scrapper.p.rapidapi.com";
	private static final String x_rapidapi_key = "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949";
		
	static String getAirportSrc(String src) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://sky-scrapper.p.rapidapi.com/api/v1/flights/searchAirport?query=" + src +"new&locale=en-US")
				.header("x-rapidapi-key", "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949")
				.header("x-rapidapi-host", "sky-scrapper.p.rapidapi.com")
				.asString();
		
		JSONObject jsonResponse = new JSONObject(response.getBody());
		JSONArray airports = jsonResponse.getJSONArray("data");
		
		if (airports.length() > 0) {
			JSONObject firstAirport = airports.getJSONObject(0);
			String originSkyId = firstAirport.getString("originSkyId");
			String originEntityId = firstAirport.getString("originEntityId");
			
			return "OriginSkyId: " + originSkyId + ", OriginEntityId: " + originEntityId;
		} else {
			return "No airport found for the location.";
		}
	}
	
	static String getAirportDst(String dst) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://sky-scrapper.p.rapidapi.com/api/v1/flights/searchAirport?query=" + dst +"new&locale=en-US")
				.header("x-rapidapi-key", "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949")
				.header("x-rapidapi-host", "sky-scrapper.p.rapidapi.com")
				.asString();
		
		JSONObject jsonResponse = new JSONObject(response.getBody());
		JSONArray airports = jsonResponse.getJSONArray("data");
		
		if (airports.length() > 0) {
			JSONObject firstAirport = airports.getJSONObject(0);
			String destinationSkyId = firstAirport.getString("destinationSkyId");
			String destinationEntityId = firstAirport.getString("destinationEntityId");
			
			return "OriginSkyId: " + destinationSkyId + ", OriginEntityId: " + destinationEntityId;
		} else {
			return "No airport found for the location.";
		}
	}
	
	static String getFlight(String originSkyId, String destinationSkyId, String originEntityId, String destinationEntityId, String date, int numAdults, int numChildren, int numInfants, String flightType) throws UnirestException {
		HttpResponse<String> response = Unirest.get("https://sky-scrapper.p.rapidapi.com/api/v2/flights/searchFlights?originSkyId=" + originSkyId + "&destinationSkyId=" + destinationSkyId + "&originEntityId=" + originEntityId + "&destinationEntityId=" + destinationEntityId + "&date=" + date + "&cabinClass=" + flightType + "&adults=" + numAdults + "&childrens=" + numChildren + "&infants=" + numInfants + "&sortBy=best&currency=USD&market=en-US&countryCode=US")
				.header("x-rapidapi-key", "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949")
				.header("x-rapidapi-host", "sky-scrapper.p.rapidapi.com")
				.asString();
		
		JSONObject jsonResponse = new JSONObject(response.getBody());
		JSONArray flights = jsonResponse.getJSONArray("data");
		
		return "";
	}
}
