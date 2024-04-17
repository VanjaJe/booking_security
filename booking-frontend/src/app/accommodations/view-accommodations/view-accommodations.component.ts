import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Accommodation, AccommodationType, Amenity, TimeSlot} from "../accommodation/model/model.module";
import {AccommodationsService} from "../accommodations.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {DatePipe, formatDate} from "@angular/common";
import {AmenitiesService} from "../../amenities/amenities.service";
import {MatOption} from "@angular/material/core";
import {MatCheckbox} from "@angular/material/checkbox";
import {UserService} from "../../account/account.service";
import {MatDateRangePicker} from "@angular/material/datepicker";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-view-accommodations',
  templateUrl: './view-accommodations.component.html',
  styleUrls: ['./view-accommodations.component.css']
})
export class ViewAccommodationsComponent implements  OnInit{

  accommodations: Accommodation[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 2;
  startDate: Date |undefined;
  endDate: Date |undefined;
  guestNum: number;
  timeSlot: TimeSlot;
  finalPrice: number;
  country:string=""
  city:string=""
  hostId: number;
  minValueView=5000;
  maxValueView=20000;
  minValue=5000;
  maxValue=20000;
  role: string = '';
  accommodationPricesMap: Map<number, number> = new Map<number, number>();
  @ViewChildren(MatOption) options: QueryList<MatOption>;
  @ViewChildren(MatCheckbox) checkboxes: QueryList<MatCheckbox>;
  filterFrom = new FormGroup({
    destination: new FormControl(),
    accommodationType: new FormControl(),
    guestNum:new FormControl(),
    minValue:new FormControl(),
    maxValue:new FormControl()
  });
  priceForm:FormGroup;
  amenities: Amenity[]=[];
  selectedAmenities:string[]=[];
  typeOptions: string[]=[];
  constructor(private service: AccommodationsService,
              private amenityService:AmenitiesService,private fb: FormBuilder,
              private userService:UserService) {
  }

  ngOnInit(): void {
    this.userService.userState.subscribe((result) => {
      this.role = result;
    });
    this.typeOptions=Object.values(AccommodationType).map(item => String(item));
    this.priceForm = this.fb.group({
      minValue: 5000,
      maxValue: 20000
    });
    if(this.role=="ROLE_HOST"){
      this.hostId=this.userService.getUserId();
    }
    this.service.getAll(this.hostId).subscribe({
      next: (data: Accommodation[]) => {
        this.accommodations = data
      },
      error: (_) => {console.log("Greska!")}
    })
    this.amenityService.getAll().subscribe({
      next:(data:Amenity[]) => {
       this.amenities=data;
    },
      error:(_)=>{console.log("Greska!")}
    })

  }
  search(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement){
    const destination = this.filterFrom.value.destination;
    if(destination){
      const destArray=destination.split(",");
      if(destArray.length>1){
        this.country=destArray[0];
        this.city=destArray[1];
      }else{
        this.country=destination;
      }
    }else{
      this.country=destination;
    }
    if(this.role=="ROLE_HOST"){
      this.hostId=this.userService.getUserId();
    }
    const accommodationType=<AccommodationType>this.filterFrom.value.accommodationType;
    const guestNumber=this.filterFrom.value.guestNum;
    if(!this.priceForm.enabled){
      this.minValue=0;
      this.maxValue=0;
    }else{
      this.minValue=this.minValueView;
      this.maxValue=this.maxValueView;
    }
    this.service.getAll(this.hostId,this.country,this.city,accommodationType,guestNumber,
      this.startDate,this.endDate,this.selectedAmenities,this.minValue,this.maxValue).subscribe({
      next: (data: Accommodation[]) => {
        this.accommodations = data
        this.calculateTotalPrice(dateRangeStart,dateRangeEnd);
      },
      error: (_) => {console.log("Greska!")}
    })

  }

    getFormattedDate(date: Date): Date {
        const formattedDate = formatDate(date, 'yyyy-MM-dd', 'en-US');
        return new Date(formattedDate);
    }

  dateRangeChanged(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {
    try{
      this.startDate = this.getFormattedDate(new Date(dateRangeStart.value))
      this.endDate = this.getFormattedDate(new Date(dateRangeEnd.value))
    }catch (Exception){
      this.startDate = undefined;
      this.endDate = undefined;
    }
  }

  calculateTotalPrice(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {
    const selectedValue = this.filterFrom.value.guestNum;

    if(selectedValue && dateRangeEnd.value!="" && dateRangeStart.value!="") {
      this.setValues(dateRangeStart, dateRangeEnd);
      const timeDifference = this.getFormattedDate(new Date(dateRangeEnd.value)).getTime() - this.getFormattedDate(new Date(dateRangeStart.value)).getTime();
      const nights = Math.floor(timeDifference / (1000 * 60 * 60 * 24));

      for (let accommodation of this.accommodations) {
        this.service.getAccommodationPrice(accommodation.id, this.guestNum, this.startDate, this.endDate)
          .subscribe((price: number) => {
            accommodation.price=price;
            if(accommodation.pricePerGuest){
              accommodation.unitPrice=price/this.guestNum/nights;
            }else{
              accommodation.unitPrice=price/nights;
            }

            },
            (error) => {
              console.error('Error fetching average rating:', error);
            });
      }
    }


  }
  private setValues(dateRangeStart: HTMLInputElement,dateRangeEnd:HTMLInputElement) {
    this.guestNum  = this.filterFrom.value.guestNum;

    this.timeSlot= {
      startDate:this.getFormattedDate(new Date(dateRangeStart.value)),
      endDate: this.getFormattedDate(new Date(dateRangeEnd.value)),
    };
  }

  onChange(amenity: string) {
    if (this.selectedAmenities.includes(amenity)) {
      this.selectedAmenities = this.
        selectedAmenities.filter((item) => item !== amenity);
    } else {
      this.selectedAmenities.push(amenity);
    }

  }

  sliderChanges() {
    this.minValueView = this.priceForm.value.minValue;
    this.maxValueView =this.priceForm.value.maxValue;

  }

  clearFilters(rangePicker: MatDateRangePicker<any>) {
    this.filterFrom.reset();
    this.checkboxes.forEach((checkbox) => {
      checkbox.checked = false;
    });
    this.selectedAmenities=[]
    this.country="";
    this.city="";
    rangePicker.select(undefined);
    this.startDate = undefined;
    this.endDate = undefined;

  }

  disablePrice() {
    if (this.priceForm.enabled) {
      this.priceForm.disable();
    } else {
      this.priceForm.enable();
    }
  }
  get paginatedAccommodations(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.accommodations.slice(startIndex, endIndex);
  }

  onPageChange(pageNumber: number): void {
    this.currentPage = pageNumber;
    window.scrollTo({
      top: 0,
      behavior: 'auto'
    });
  }

  getPages(): number[] {
    const totalItems = this.accommodations.length;
    const totalPages = Math.ceil(totalItems / this.itemsPerPage);
    return Array.from({ length: totalPages }, (_, index) => index + 1);
  }

}
