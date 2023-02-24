import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { CuisineListComponent } from './components/cuisine-list.component';
import { RestaurantCuisineComponent } from './components/restaurant-cuisine.component';
import { RestaurantDetailsComponent } from './components/restaurant-details.component';
import { MaterialModule } from './material.module';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  { path: '', component: CuisineListComponent },
  { path: 'api/:cuisine/restaurants', component: RestaurantCuisineComponent },
  { path: 'api/comments', component: RestaurantDetailsComponent },
  { path: 'api/restaurant/:name', component: RestaurantDetailsComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    CuisineListComponent,
    RestaurantCuisineComponent,
    RestaurantDetailsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
    RouterModule.forRoot(appRoutes, {onSameUrlNavigation: 'reload'}),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
