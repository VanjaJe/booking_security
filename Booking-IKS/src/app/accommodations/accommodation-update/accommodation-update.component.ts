import { DatePipe } from '@angular/common';
import { Component, Host, QueryList, ViewChildren } from '@angular/core';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AmenityService } from 'src/app/amenity/amenity.service';
import { Amenity, Address, TimeSlot, PriceListItem, CreateAccommodation, Accommodation, Image, AccommodationType, RequestStatus, ReservationRequest } from '../accommodation/model/model.module';
import { AccommodationsService } from '../accommodations.service';
import { CommentAndGrade } from 'src/app/administrator/comments-and-grades/model/model.module';
import { MatCheckbox } from '@angular/material/checkbox';
import { ReservationsService } from 'src/app/reservations/reservations.service';

@Component({
  selector: 'app-accommodation-update',
  templateUrl: './accommodation-update.component.html',
  styleUrls: ['./accommodation-update.component.css']
})

export class AccommodationUpdateComponent {
  accommodation: Accommodation | undefined;
  activeReservations: ReservationRequest[];
  events: string[] = [];
  selectedAmenities: Amenity[]=[];
  allAmenities2: Amenity[] = []
  submitted: boolean =false;

  constructor(private root:ActivatedRoute,private accommodationService: AccommodationsService, private router: Router,
              private amenityService: AmenityService,
              private fb: FormBuilder,
              private reservationService: ReservationsService) {
  }


  updateAccommodationFormGroup = this.fb.group({
    name: ['',[Validators.required]],
    city: ['',[Validators.required]],
    country: ['',[Validators.required]],
    address: ['',[Validators.required]],
    description: ['',[Validators.required]],
    minGuests: [0,[Validators.required]],
    maxGuests: [0,[Validators.required]],
    deadline: [0,[Validators.required]],
    checkPrice: new FormControl(),
    checkReservation: new FormControl(),
    selectType: ['',[Validators.required]]
  });

  ngOnInit(): void {
    this.root.params.subscribe((params) =>{
      const id=+params['id']
      this.accommodationService.getAccommodation(id).subscribe({
        next:(data:Accommodation)=>{
          this.accommodation=data;
          this.updateAccommodationFormGroup.patchValue({
            name: this.accommodation.name,
            description: this.accommodation.description,
            city: this.accommodation.address?.city,
            country: this.accommodation.address?.country,
            address: this.accommodation.address?.address,
            minGuests: this.accommodation.maxGuests,
            maxGuests: this.accommodation.maxGuests,
            deadline: this.accommodation.reservationDeadline,
            checkPrice: this.accommodation.pricePerGuest,
            checkReservation: this.accommodation.automaticConfirmation,
          });
          if(this.accommodation.amenities){
          // this.selectedAmenities = this.accommodation?.amenities;
          }
          this.haveActiveReservation();
        }
      })
      this.amenityService.getAll().subscribe({
        next: (data: Amenity[]) => {
          this.allAmenities2 = data
        },
        error: (_) => {console.log("Greska!")}
      })
      }

    )

  }

  // onChange(amenity: Amenity) {
  //   if(this.selectedAmenities){
  //     if(!this.selectedAmenities.includes(amenity)){
  //       this.selectedAmenities.push(amenity)
  //     }
  //   }

  // }

  @ViewChildren(MatCheckbox) checkboxes: QueryList<MatCheckbox>;

  getCheckedAmenities(): string[] {
    const checkedAmenities: string[] = [];

    this.checkboxes.forEach(checkbox => {
      if (checkbox.checked) {
        if(checkbox.name){
          checkedAmenities.push(checkbox.name); // or use another property of your amenity object
      }}
    });
    for (let checkedAmenity of checkedAmenities) {
      for (let amenity of this.allAmenities2) {
        if (amenity.name === checkedAmenity) {
          this.selectedAmenities.push(amenity);
        }
      }
    }

    return checkedAmenities;
  }

  haveActiveReservation(){
    const today: Date = new Date();
    const targetDate: Date = new Date(2030, 11, 31); 

    const year: number = today.getFullYear();
    const month: number = today.getMonth() + 1; // Month is zero-based, so add 1
    const day: number = today.getDate();
    const dateString: string = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
    const dateString1: string = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`

    console.log(this.updateAccommodationFormGroup.value.name)
    this.reservationService.getAll(RequestStatus.ACCEPTED,this.updateAccommodationFormGroup.value.name as string,dateString,dateString1).subscribe({
      next: (data: ReservationRequest[]) => {
        this.activeReservations = data;
        console.log(this.activeReservations)
        if(this.activeReservations.length>0){
          const updateButton = document.getElementById('updateAccommodation') as HTMLButtonElement;
          updateButton.style.visibility = 'hidden';
        }
      },
      error: (_) => {
        console.log("Error fetching data from ReservationsService");
      }
    });
  }

  update() {
    console.log(this.getCheckedAmenities())
    this.submitted=true;

    if (this.updateAccommodationFormGroup.valid) {
      const address: Address = {
        city: this.updateAccommodationFormGroup.value.city as string,
        country: this.updateAccommodationFormGroup.value.country as string,
        address: this.updateAccommodationFormGroup.value.address as string,
      };

      const updatedAccommodation: Accommodation = {
        id: this.accommodation?.id,
        name: this.updateAccommodationFormGroup.value.name as string,
        description: this.updateAccommodationFormGroup.value.description as string,
        address: address,
        minGuests: this.updateAccommodationFormGroup.value.minGuests as number,
        maxGuests: this.updateAccommodationFormGroup.value.maxGuests as number,
        type: <AccommodationType> this.updateAccommodationFormGroup.value.selectType as AccommodationType,
        pricePerGuest: this.updateAccommodationFormGroup.value.checkPrice,
        automaticConfirmation: this.updateAccommodationFormGroup.value.checkReservation,
        host: Host(),
        reservationDeadline: this.updateAccommodationFormGroup.value.deadline as number,
        amenities: this.selectedAmenities
      };

      console.log(updatedAccommodation)

      this.accommodationService.update(updatedAccommodation).subscribe(
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
    else {

    }
  }
  url: string|null|ArrayBuffer = '../../../assets/images/addpicture.png'

  selectedImages: Image[] = [];

  isAmenitySelected(amenity: Amenity): boolean {
    const isSelected = this.accommodation?.amenities?.some(a => a.name === amenity.name);
    if(isSelected){
      return isSelected;
    }
    return false;
  }

  onFileSelected(event: any):void {
    const files: FileList | null = event.target.files;
    if (files) {
      for (let i=0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          if (e.target) {
            const imageURL = e.target.result as string;
            const image : Image = {
              url: imageURL,
              file: files[i]
            }
            this.selectedImages.push(image);
            console.log(files[i]); // ovde ispise undefined
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }
  }

  uploadPicture(idAccommodation: number) {
    const images : File[] = [];
    for (let image of this.selectedImages) {
      images.push(image.file);
    }

    // const idAccommodation = this.newAccommodation.id;
    // @ts-ignore
    this.accommodationService.uploadImage(images, idAccommodation).subscribe(
      {
        next: (data: Accommodation) => {
          // alert(data);
        },
        error: (_) => {
        }
      }
    );
  }
}
