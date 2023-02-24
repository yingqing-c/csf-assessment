package vttp2022.csf.assessment.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;
import vttp2022.csf.assessment.server.services.S3Service;

@Controller
@RequestMapping(path="/api")
public class RestaurantController {
    
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private S3Service s3Service;
    /*  
    * Task 2
    * GET/api/cuisines
    */

    @GetMapping(path="/cuisines", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCuisines() {
            String cuisinesJson = restaurantService.getCuisines();
            System.out.println("CusinesJson: " + cuisinesJson);
            return ResponseEntity.ok(cuisinesJson);
    }

    /*  
    * Task 3
    * GET/api/<cuisines>/restaurants
    * games in ascending ranking order
    */

    @GetMapping(path="/{cuisine}/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRestaurantsByCuisine(@PathVariable String cuisine) {
            String restaurants = restaurantService.getRestaurantsByCuisine(cuisine);
            System.out.println(restaurants);
            return ResponseEntity.ok(restaurants);
    }

    /*  
    * Task 4
    * GET/map?lat=<latitude>&lng=<longitude>
    * Accept: image/png
    */
    @GetMapping(path="/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRestaurantById(@PathVariable String restaurantId) {
            Optional<Restaurant> restaurant = restaurantService.getRestaurant(restaurantId);

            if (restaurant.isEmpty())
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            Json.createObjectBuilder().add("message", "Cannot find restaurant with restaurantId "
                                    + restaurantId).build().toString());

            return ResponseEntity.ok(restaurant.get().toJSON().toString());
    }
}


}
