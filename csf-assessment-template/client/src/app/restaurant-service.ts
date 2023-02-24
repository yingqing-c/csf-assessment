import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { lastValueFrom } from 'rxjs'
import { Restaurant, Comment } from './models'

@Injectable()
export class RestaurantService {

  constructor(private httpClient: HttpClient) {}


	// TODO Task 2
	// Use the following method to get a list of cuisines
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	public getCuisineList(cusisine: string): Promise<any>{
		// Implememntation in here
    const headers = new HttpHeaders().set("content-type", "application/json")

    return lastValueFrom(this.httpClient.get<any>(`api/cuisines`, {headers}))
  }

	// TODO Task 3
	// Use the following method to get a list of restaurants by cuisine
	// You can add any parameters (if any) and the return type
	// DO NOT CHNAGE THE METHOD'S NAME
	public getRestaurantsByCuisine(cuisine: string){
		// Implememntation in here
    const headers = new HttpHeaders()
    .set('content-type', 'application/json')
    .set('Access-Control-Allow-Origin', '*')

    return lastValueFrom(
    this.httpClient.get<Restaurant>(`/api/${cuisine}/restaurants}`,
    {headers})
)
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any)
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// public getRestaurant(???): Promise<Restaurant> {
		// Implememntation in here

	// }

	// TODO Task 5
	// Use this method to submit a comment
	// DO NOT CHANGE THE METHOD'S NAME OR SIGNATURE
	public postComment(comment: Comment): Promise<any> {
		// Implememntation in here
    const body = {
      comment: comment,
    };

    return lastValueFrom(
      this.httpClient.post(
        `https://ripe-cup-production.up.railway.app/api/comments}`,
        body
      )
    );
  }
}
