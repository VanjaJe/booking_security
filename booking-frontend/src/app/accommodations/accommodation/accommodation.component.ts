import {Component, OnInit} from '@angular/core';
import {AccommodationsService} from "../accommodations.service";
import {Accommodation} from "./model/model.module";

@Component({
  selector: 'app-accommodation',
  templateUrl: './accommodation.component.html',
  styleUrls: ['./accommodation.component.css']
})

export class AccommodationComponent implements OnInit{

  accommodations: Accommodation[] = []
  constructor(private service: AccommodationsService) {
  }

  ngOnInit(): void {
    this.service.getAll().subscribe({
      next: (data: Accommodation[]) => {
        this.accommodations = data
      },
      error: (_) => {console.log("Greska!")}
    })
  }
}
