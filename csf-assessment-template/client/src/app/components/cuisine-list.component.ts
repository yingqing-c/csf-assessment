import { RestaurantService } from './../restaurant-service';
import { Restaurant } from './../models';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent implements OnInit{

  // TODO Task 2
	// For View 1
  // List of Cuisines

  restaurants!: Restaurant[]
  cuisine!: string
  offset: number = 0
  param$!: Subscription

  constructor(private ar: ActivatedRoute, private router: Router, private restaurantSvc: RestaurantService) {}

  ngOnInit(): void {
    // subscribe to changes in current route
    this.param$ = this.ar.queryParams.subscribe((params) => {
      this.getCuisineList();
    });
  }

  getCuisineList() {
    this.restaurantSvc
    .getCuisineList(this.cuisine)
    .then((res) => {
      this.restaurants = res;
    })
    .catch((err) => {
      console.log(err);
    });
}
}
