import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css'],
})
export class RestaurantCuisineComponent implements OnInit {
  // TODO Task 3
  // For View 2
  restaurants: Restaurant[] = [];
  cuisine!: string;

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.restaurants = this.restaurantService.getRestaurantsByCuisine(this.cuisine);
  }
}
