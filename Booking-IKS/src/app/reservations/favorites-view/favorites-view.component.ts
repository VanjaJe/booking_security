import {Component, OnInit} from '@angular/core';
import {Accommodation, FavoriteAccommodations} from "../../accommodations/accommodation/model/model.module";
import {AccommodationsService} from "../../accommodations/accommodations.service";
import {FavoritesService} from "./favorites.service";
import {forkJoin} from "rxjs";
import {UserService} from "../../account/account.service";

@Component({
  selector: 'app-favorites-view',
  templateUrl: './favorites-view.component.html',
  styleUrls: ['./favorites-view.component.css']
})
export class FavoritesViewComponent implements OnInit{

  // clickedAccommodation:number|undefined
  accommodations: Accommodation[] = []
  constructor(private service: AccommodationsService,private userService:UserService) {
  }

  ngOnInit(): void {
    this.service.getAllFavorites(this.userService.getUserId()).subscribe({
      next: (data: Accommodation[]) => {
        this.accommodations = data
      },
      error: (_) => {console.log("Greska!")}
    });
  }
  // onAccommodationClicked(accommodation:Accommodation){
  //   this.clickedAccommodation=accommodation.id;
  //
  // }

}
