import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Accommodation, Address, Amenity, RequestStatus, ReservationRequest} from "../accommodation/model/model.module";
import {Subscription} from "rxjs";
import {CommentsService} from "../../comments/comments.service";
import {AccommodationsService} from "../accommodations.service";
import {DomSanitizer} from "@angular/platform-browser";
import { UserService } from 'src/app/account/account.service';
import { Router } from '@angular/router';
import { ReservationsService } from 'src/app/reservations/reservations.service';
import {Guest} from "../../administrator/comments-and-grades/model/model.module";

@Component({
  selector: 'app-accommodation-card',
  templateUrl: './accommodation-card.component.html',
  styleUrls: ['./accommodation-card.component.css']
})
export class AccommodationCardComponent {

  role: string;
  activeReservations:ReservationRequest[];
  rating: number = 0;
  images: string[] =[];
  favorites:Accommodation[]=[];
  image:string;
  guest:Guest;
  private subscription: Subscription;
  @Input()
  accommodation: Accommodation;
  @Output()
  clicked:EventEmitter<Accommodation>=new EventEmitter<Accommodation>();
  reservations: any;
  buttonColor: string = 'primary'

  constructor(private commentService:CommentsService,
              private accommodationService:AccommodationsService,private sanitizer:DomSanitizer,
              private userService: UserService, private router:Router, private reservationService:ReservationsService) {}


  ngOnInit() {
    this.role = this.userService.getRole();
    if(this.role=="ROLE_GUEST"){
      const guestId=this.userService.getUserId();
      this.userService.getUser(guestId).subscribe(
        (data) => {
          this.guest=data;
        },
        (error) => {
          console.error('Error fetching guest:', error);
        });
      this.colorFavorites();
    }
    this.accommodation.unitPrice=0;
    this.accommodation.price=0;
    const updateButtons = document.getElementsByClassName('updateAccommodation') as HTMLCollectionOf<HTMLButtonElement>;
    const acceptButtons = document.getElementsByClassName('acceptButton') as HTMLCollectionOf<HTMLButtonElement>;
    const declineButtons = document.getElementsByClassName('declineButton') as HTMLCollectionOf<HTMLButtonElement>;
    for (let i = 0; i < updateButtons.length; i++) {
        const updateButton = updateButtons[i];
        if (this.userService.getRole() != 'ROLE_HOST') {
            updateButton.style.visibility = 'hidden';
        }
    }
    for (let i = 0; i < acceptButtons.length; i++) {
      const acceptButton = acceptButtons[i];
      if (this.userService.getRole() != 'ROLE_ADMIN') {
          acceptButton.style.visibility = 'hidden';
      }
    }
    for (let i = 0; i < declineButtons.length; i++) {
      const declineButton = declineButtons[i];
      if (this.userService.getRole() != 'ROLE_ADMIN') {
          declineButton.style.visibility = 'hidden';
      }
    }

    // @ts-ignore
    this.commentService.getAverageAccommodationRating(this.accommodation.id)
      .subscribe(
        (averageRating: number) => {
            this.rating = averageRating;
        },
        (error) => {
          console.error('Error fetching average rating:', error);
        }
      );
    this.accommodationService.getImages(this.accommodation?.id).subscribe(
      (images) => {
        this.images = images;
        if(this.images.length>0){
          this.image=this.decodeBase64AndSanitize(this.images[0])
        }
      },
      (error) => {
        console.error('Error fetching images:', error);
      }
    );
  }
  decodeBase64AndSanitize(image: string): string {
    const decodedImage = atob(image);
    const blob = new Blob([new Uint8Array([...decodedImage].map(char => char.charCodeAt(0)))], { type: 'image/png' });
    const imageUrl = URL.createObjectURL(blob);
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl) as string;
  }
  onAccommodationClicked():void{
    this.clicked.emit(this.accommodation);
  }

    protected readonly Array = Array;


  getStarColor(starIndex: number): string {
    return starIndex <= this.rating ? 'filled-star' : 'empty-star';
  }

  acceptAccommodation() {
    this.accommodationService.accept(this.accommodation).subscribe(
      {
        next: (data: Accommodation) => {
          this.accommodation = data;
          this.router.navigate(['home']);
        },
        error: (_) => {
        }
      }
    );
  }
  declineAccommodation() {
    this.accommodationService.decline(this.accommodation).subscribe(
      {
        next: (data: Accommodation) => {
          this.accommodation = data;
          this.router.navigate(['home']);
        },
        error: (_) => {
        }
      }
    );
  }


  addFavorite(accommodation: Accommodation) {
    this.buttonColor = this.buttonColor === 'warn' ? 'primary' : 'warn';
    this.accommodationService.updateFavoriteAccommodation(this.guest.id, accommodation.id).subscribe(
      (response) => {
        console.log('Favorite added successfully:', response);
      },
      (error) => {
        console.log('Error adding favorite:', error)
      }
    );
  }
  private colorFavorites() {
    this.accommodationService.getAllFavorites(this.userService.getUserId()).subscribe({
      next: (data: Accommodation[]) => {
        this.favorites = data
        if (this.favorites.some(favorite => favorite.id === this.accommodation.id)) {
          this.buttonColor = 'warn';
        }
      },
      error: (_) => {console.log("Greska!")}
    });
  }
}


