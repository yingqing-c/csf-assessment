import { RestaurantService } from './../restaurant-service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant } from '../models';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css'],
})
export class RestaurantDetailsComponent implements OnInit {

  // TODO Task 4 and Task 5
  // For View 3

  constructor(private fb: FormBuilder, private ar: ActivatedRoute, private restaurantSvc: RestaurantService, private router: Router) {}

  commentForm!: FormGroup;
  name!: string;
  restaurant!: Restaurant;

  restaurants: string[] = [];

  ngOnInit(): void {
    this.commentForm = this.createForm();
  }

  createForm(): FormGroup<any> {
    return this.fb.group({
      name: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      rating: this.fb.control<number>(1, [
        Validators.required,
        Validators.min(1),
        Validators.max(5),
      ]),
      text: this.fb.control('', [Validators.required]),
    });
  }

  submitForm() {
    const comment = this.commentForm.get('comment')?.value;
    // post comment to server
    this.restaurants.push(comment);
  }


  postComment() {
    const comment = this.commentForm.value['comment']
    console.log(comment)

    // post comment
    this.restaurantSvc.postComment(this.name, comment)

    this.router.navigate([`/api/comments`])
  }
}
