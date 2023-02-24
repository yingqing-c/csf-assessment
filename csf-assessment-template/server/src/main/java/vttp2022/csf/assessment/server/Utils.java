package vttp2022.csf.assessment.server;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.models.Restaurant;

public class Utils {
    
    public static JsonObject toJSON() {
        Restaurant r = new Restaurant();
		return Json.createObjectBuilder()
				.add("restaurantId", r.getRestaurantId())
				.add("name", r.getName())
				.add("cuisine", r.getCuisine())
				.add("address", r.getAddress())
				// .add("coordinates", r.getCoordinates().toJSON())
				.add("mapUrl", r.getMapURL())
				.build();
	}

    public static JsonObject toJSONCoordinates(){
        LatLng l = new LatLng();
		return Json.createObjectBuilder()
				.add("latitude", l.getLatitude())
				.add("longitude", l.getLongitude())
				.build();
	}

}