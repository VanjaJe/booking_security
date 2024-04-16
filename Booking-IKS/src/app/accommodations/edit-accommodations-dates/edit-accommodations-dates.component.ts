import {AfterViewInit, Component, Host, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccommodationsService} from "../accommodations.service";
import {ActivatedRoute, Router} from "@angular/router";
import {
  Accommodation,
  EditAccommodation,
  PriceListItem,
  TimeSlot
} from "../accommodation/model/model.module";
import {DatePipe} from "@angular/common";
import {MatTableDataSource} from "@angular/material/table";
import {SharedService} from "../../shared/shared.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-edit-accommodations-dates',
  templateUrl: './edit-accommodations-dates.component.html',
  styleUrls: ['./edit-accommodations-dates.component.css']
})

export class EditAccommodationsDatesComponent implements  OnInit, AfterViewInit{
  // @ts-ignore
  pricelists : PriceListItem[] = []
  // @ts-ignore
  dataSource: MatTableDataSource<PriceListItem>;
  displayedColumns: string[] = ['price', 'startDate', 'endDate'];
  isEdit: boolean | undefined;
  // @ts-ignore
  formatedStartDate:Date;
  // @ts-ignore
  formatedEndDate:Date;
  // @ts-ignore
  id:number;
  submitted:boolean = false;

  todayDate:Date = new Date();

  createAccommodationForm = new FormGroup({
    price: new FormControl(),
    startDate: new FormControl(),
    endDate: new FormControl()
  });


  editTimeSlotsAccommodationForm = new FormGroup({
    startFreeDate: new FormControl(),
    endFreeDate: new FormControl()
  });

  constructor(private accommodationService: AccommodationsService, private router: Router,
              private route:ActivatedRoute, private snackBar:MatSnackBar) {}

  ngAfterViewInit(): void {
    //ne razumemm
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params['id'];
    });

    this.accommodationService.getAccommodation(this.id).subscribe({
      next: (data: Accommodation) => {
        // @ts-ignore
        this.pricelists = data.priceList;
        this.dataSource = new MatTableDataSource<PriceListItem>(this.pricelists);
      },
      error: (_) => {console.log("Greska!")}
    })
  }


  getAcc() {
    this.accommodationService.getAccommodation(this.id).subscribe({
      next: (data: Accommodation) => {
        // @ts-ignore
        this.pricelists = data.priceList;
        this.dataSource = new MatTableDataSource<PriceListItem>(this.pricelists);
      },
      error: (_) => {console.log("Greska!")}
    })
  }


  edit() {
    this.submitted=true;

    if (this.createAccommodationForm.valid) {
      console.log(this.createAccommodationForm.value.price);

      const timeSlot: TimeSlot = {
        startDate: this.formatedStartDate,
        endDate: this.formatedEndDate
        };
      const pricelistItem: PriceListItem = {
        price: this.createAccommodationForm.value.price,
        timeSlot: timeSlot
      }

      console.log("Vanja")
      this.accommodationService.editPricelistItem(pricelistItem, this.id).subscribe(
        {
          next: (data: Accommodation) => {
            this.isEdit = false;
            this.createAccommodationForm.reset();
            // this.dataSource = new MatTableDataSource<PriceListItem>(this.pricelists);
            this.getAcc();

            // this.snackBar.open("Updated", 'Close', {
            //   duration: 3000,
            // });
          },
          error: (_) => {
          }
        });
      }
    }

  editTimeSlots() {
    if (this.editTimeSlotsAccommodationForm.valid) {

      const timeSlot: TimeSlot = {
        startDate: this.formatedStartDate,
        endDate: this.formatedEndDate
      };

      console.log("Vanja")
      console.log(this.formatedStartDate);
      console.log(this.formatedEndDate);
      this.accommodationService.editFreeTimeSlotsItem(timeSlot, this.id).subscribe(
        {
          next: (data: Accommodation) => {
            this.editTimeSlotsAccommodationForm.reset();  // proveriti
            this.snackBar.open("Updated", 'Close', {
              duration: 3000,
            });
          },
          error: (_) => {
            this.snackBar.open("Already has reservations in that period!", 'Close', {
              duration: 3000,
            });
              // this.sharedService.openSnack("Already has reservations in that period.")
              // console.log("Already has reservations");
          }
        });
    }
  }

  getFormatedDate(date: Date, format: string):string|null {
    const datePipe : DatePipe = new DatePipe('en-US');
    return datePipe.transform(date, format);
  }

  dateRangeChanged(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {
    // @ts-ignore
    this.formatedStartDate = this.getFormatedDate(new Date(dateRangeStart.value), "yyyy-MM-dd")
    // @ts-ignore
    this.formatedEndDate = this.getFormatedDate(new Date(dateRangeEnd.value), "yyyy-MM-dd")
  }

  freeDatesRangeChanged(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {
    // @ts-ignore
    this.formatedStartDate = this.getFormatedDate(new Date(dateRangeStart.value), "yyyy-MM-dd")
    // @ts-ignore
    this.formatedEndDate = this.getFormatedDate(new Date(dateRangeEnd.value), "yyyy-MM-dd")
  }

  // onEditClicked(priceListItem: PriceListItem) {
  //   this.selectedPriceItem = priceListItem;
  //   console.log("vanja")
  //   this.isEdit=true;
  //   this.createAccommodationForm.setValue({
  //     price: priceListItem.price,
  //     startDate: priceListItem.timeSlot.startDate,
  //     endDate: priceListItem.timeSlot.endDate
  //   });
  // }
}
