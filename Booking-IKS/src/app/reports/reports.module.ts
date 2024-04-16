import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportComponent } from './report/report.component';
import {MatButtonModule} from "@angular/material/button";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import { AnnualChartComponent } from './annual-chart/annual-chart.component';
import {NgChartsModule} from "ng2-charts";
import { ReservationsChartComponent } from './reservations-chart/reservations-chart.component';



@NgModule({
  declarations: [
    ReportComponent,
    AnnualChartComponent,
    ReservationsChartComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatInputModule,
    NgChartsModule
  ]
})
export class ReportsModule { }
