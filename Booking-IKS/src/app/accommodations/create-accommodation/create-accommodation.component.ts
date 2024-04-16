import {Component, Host} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AccommodationsService} from "../accommodations.service";
import {
  Amenity,
  Address,
  CreateAccommodation,
  PriceListItem,
  TimeSlot, AccommodationType, Accommodation, Image
} from "../accommodation/model/model.module";
import {DatePipe} from "@angular/common";
import {AmenityService} from "../../amenity/amenity.service";
import {UserService} from "../../account/account.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import { User } from 'src/app/account/model/model.module';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-accommodation',
  templateUrl: './create-accommodation.component.html',
  styleUrls: ['./create-accommodation.component.css']
})
export class CreateAccommodationComponent {
  events: string[] = [];
  selectedAmenities: Amenity[] = [];
  startDate: string;
  endDate: string = "";
  allAmenities2: Amenity[] = []
  // createAccommodationForm:FormGroup;
  submitted: boolean =false;
  todayDate:Date = new Date();
  // @ts-ignore
  newAccommodation: Accommodation;
  public role: string| undefined;

  host1: User | undefined;
  constructor(private accommodationService: AccommodationsService, private router: Router,
              private amenityService: AmenityService,
              private fb: FormBuilder,
              private userService: UserService, private snackBar:MatSnackBar) {
  }


  createAccommodationForm = this.fb.group({
    name: ['',[Validators.required]],
    city: ['',[Validators.required]],
    country: ['',[Validators.required]],
    address: ['',[Validators.required]],
    description: ['',[Validators.required]],
    minGuests: ['',[Validators.required]],
    maxGuests: ['',[Validators.required]],
    price: ['',[Validators.required]],
    deadline: ['',[Validators.required]],
    checkPrice: new FormControl(),
    checkReservation: new FormControl(),
    selectType: ['',[Validators.required]]
  });

  ngOnInit(): void {
    const item = localStorage.getItem('user');
    const jwt: JwtHelperService = new JwtHelperService();
    console.log("JWTTTTTTTTTTTTTTTTTTTTTTTTT")
    // @ts-ignore
    console.log(jwt.decodeToken(item));
    // @ts-ignore
    console.log(jwt.decodeToken(item).sub)

    this.amenityService.getAll().subscribe({
      next: (data: Amenity[]) => {
        this.allAmenities2 = data
      },
      error: (_) => {console.log("Greska!")}
    });

    // @ts-ignore
    this.userService.getUserByUsername(jwt.decodeToken(item).sub).subscribe({
      next: (data: User) => {
        this.host1 = data;
        console.log(this.host1)
      },
      error: (_) => {console.log("Greska!")}
    })
  }

  create() {

    this.submitted=true;

    console.log("vanja ", this.createAccommodationForm.value.name);

    if (this.createAccommodationForm.valid) {
      // console.log("vakiiiii")
      const address: Address = {
        // @ts-ignore
        city: this.createAccommodationForm.value.city,
        // @ts-ignore
        country: this.createAccommodationForm.value.country,
        // @ts-ignore
        address: this.createAccommodationForm.value.address,
      };

      const timeslots: TimeSlot[] = []
      const timeSlot: TimeSlot = {
        startDate: new Date(this.startDate),
        endDate: new Date(this.endDate)
      };
      timeslots.push(timeSlot);

      const pricelist: PriceListItem[] = [];
      const pricelistItem: PriceListItem = {
        // @ts-ignore
        price: this.createAccommodationForm.value.price,
        timeSlot: timeSlot
      }
      pricelist.push(pricelistItem);


      const accommodation: CreateAccommodation = {
        // @ts-ignore
        name: this.createAccommodationForm.value.name,
        // @ts-ignore
        description: this.createAccommodationForm.value.description,
        address: address,
        // @ts-ignore
        minGuests: this.createAccommodationForm.value.minGuests,
        // @ts-ignore
        maxGuests: this.createAccommodationForm.value.maxGuests,
        // @ts-ignore
        type: <AccommodationType> this.createAccommodationForm.value.selectType,
        pricePerGuest: this.createAccommodationForm.value.checkPrice,
        automaticConfirmation: this.createAccommodationForm.value.checkReservation,
        // host: Host(),
        // @ts-ignore
        host:this.host1,
        // @ts-ignore
        reservationDeadline: this.createAccommodationForm.value.deadline,
        amenities: this.selectedAmenities,
        freeTimeSlots: timeslots,
        priceList: pricelist
      };
      console.log("checkeeed ", this.createAccommodationForm.value.name);


      this.accommodationService.add(accommodation).subscribe(
        {
          next: (data: CreateAccommodation) => {
            this.newAccommodation = data;
            this.snackBar.open("Accommodation created!", 'Close', {
              duration: 3000,
            });
            // @ts-ignore
            this.uploadPicture(this.newAccommodation.id);

          },
          error: (_) => {
          }
        });
    }
    else {}
  }
  url: string|null|ArrayBuffer = '../../../assets/images/addpicture.png'

  selectedImages: Image[] = [];

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
            console.log(files[i]);
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }
  }
  //
  // var reader = new FileReader()
  // reader.readAsDataURL(files[0])
  // reader.onload = (event:Event) => {
  //   let fileReader = event.target as FileReader
  //   this.url = fileReader.result;
  // }


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

  onChange(amenity: Amenity) {
    this.selectedAmenities.push(amenity)
  }

  getFormatedDate(date: Date, format: string):string|null {
    const datePipe : DatePipe = new DatePipe('en-US');
    return datePipe.transform(date, format);
  }

  dateRangeChanged(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {
    // @ts-ignore
    this.startDate = this.getFormatedDate(new Date(dateRangeStart.value), "yyyy-MM-dd")
    // @ts-ignore
    this.endDate = this.getFormatedDate(new Date(dateRangeEnd.value), "yyyy-MM-dd")
  }
}
