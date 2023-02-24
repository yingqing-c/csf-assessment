package vttp2022.csf.assessment.server.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import vttp2022.csf.assessment.server.models.LatLng;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	// db.restaurants.distinct('cuisine')
	public List<String> getCuisines() {
		List<String> cuisines = mongoTemplate.findDistinct(
				new Query(), "cuisine", "restaurants", String.class);

		System.out.println(cuisines.size());
		System.out.println(cuisines.get(0));
		return cuisines;
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	// db.restaurants.find({ cuisine: { $regex: cuisine } })
	// db.restaurants.find({ cuisine: cuisine }) // for exact match
	public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		Criteria criteria = Criteria.where("cuisine").regex(cuisine);
		Query query = Query.query(criteria);

		List<Document> result = mongoTemplate.find(
				query, Document.class, "restaurants"
		);

		List<Restaurant> restaurants = new ArrayList<>();
		if (result.size() > 0) {
			for (Document d: result) {;
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(d.getString("restaurant_id"));
				restaurant.setName(d.getString("name"));
				restaurant.setCuisine(d.getString("cuisine"));
				// address
				Document addressDoc = d.get("address", Document.class);
				String address = addressDoc.getString("building") + ", " +
							addressDoc.getString("street") + ", " +
							addressDoc.getString("zipcode") + ", " + d.getString("borough");
				restaurant.setAddress(address);
				// Coordinates
				LatLng coordinates = new LatLng();
				List<Double> coord = addressDoc.getList("coord", Double.class);
				coordinates.setLatitude(coord.get(0).floatValue());
				coordinates.setLongitude(coord.get(1).floatValue());
				restaurant.setCoordinates(coordinates);
				restaurants.add(restaurant);
			}
		}
		return restaurants;
	}

//	// TODO Task 4
//	// Use this method to find a specific restaurant
//	// You can add any parameters (if any)
//	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
//	// Write the Mongo native query above for this method
//	//
//	public Optional<Restaurant> getRestaurant(???) {
//		// Implmementation in here
//
//	}
///	// TODO Task 4
//	// Use this method to find a specific restaurant
//	// You can add any parameters (if any)
//	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
//	// Write the Mongo native query above for this method
//	// db.restaurants.find({ restaurant_id: restaurant_id })
	public Optional<Restaurant> getRestaurant(String restaurantId) {
		Criteria criteria = Criteria.where("restaurant_id").is(restaurantId);
		Query query = Query.query(criteria);
		List<Document> result = mongoTemplate.find(query, Document.class, "restaurants");
		if (result.size() == 0) {
			return null;
		}
		Restaurant restaurant = this.restaurantFromDocument(result.get(0));
//		try {
//			String mapUrl = this.mapCache.getMap(restaurantId, restaurant.getCoordinates());
//			restaurant.setMapURL(mapUrl);
//		} catch (IOException e) {
//			System.out.println("Unable to get map");
//			System.out.println(e);
//		}

		return Optional.ofNullable(restaurant);
	}

//
//	// TODO Task 5
//	// Use this method to insert a comment into the restaurant database
//	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
//	// Write the Mongo native query above for this method
//	//
//	public void addComment(Comment comment) {
//		// Implmementation in here
//
//	}
	
	// You may add other methods to this class

}
