import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AccommodationsService} from "../../accommodations/accommodations.service";
import {Router} from "@angular/router";
import {AmenityService} from "../../amenity/amenity.service";
import {UserService} from "../../account/account.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Accommodation} from "../../accommodations/accommodation/model/model.module";
import {ReportService} from "../report.service";
import {AccommodationReport} from "../model/model";
import {formatDate} from "@angular/common";
import html2canvas from 'html2canvas';
import { jsPDF } from 'jspdf';



@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent {
  accommodations:Accommodation[];
  report:AccommodationReport;
  reports:AccommodationReport[];
  showReservationsChart: boolean = false;
  form = new FormGroup({
    accommodationName: new FormControl(),
    year:new FormControl()
  });
  profitChart: {
    data: any[],
    labels: string[],
    options: any
  } = { data: [], labels: [], options: {} };
  reservationsChart: {
    data: any[],
    labels: string[],
    options: any
  } = { data: [], labels: [], options: {} };
  constructor(private accommodationService: AccommodationsService,
              private userService: UserService,
              private reportService:ReportService) {
  }

  ngOnInit(){
    this.loadAccommodations(this.userService.getUserId());

  }

  generateRangeReport(dateRangeStart: HTMLInputElement, dateRangeEnd: HTMLInputElement) {

    if(dateRangeEnd.value!="" && dateRangeStart.value!="") {
      const startDate = this.getFormattedDate(new Date(dateRangeStart.value));
      const endDate = this.getFormattedDate(new Date(dateRangeEnd.value));
      this.showReservationsChart = true;

      this.reportService.getTimeSlotRepost(this.userService.getUserId(), startDate, endDate)
        .subscribe({
          next: (data: AccommodationReport[]) => {
            this.reports = data;
            const totalProfits = this.reports.map(report => report.totalProfit);
            const numberOfReservations = this.reports.map(report => report.numberOfReservations);
            const accommodationNames = this.reports.map(report => report.accommodationName);

            this.profitChart.data = [
              {data: totalProfits, label: 'Profit', type: 'line'},
            ];
            this.profitChart.labels = accommodationNames;
            this.profitChart.options = {
              responsive: true,
              scales: {
                xAxes: [{ticks: {beginAtZero: true}, barPercentage: 0.5}],
                yAxes: [{ticks: {beginAtZero: true}}],
              },
            };

            this.reservationsChart.data = [
              {data: numberOfReservations, label: 'Number of Reservations', type: 'line'}
            ];
            this.reservationsChart.labels = accommodationNames;
            this.profitChart.options = {
              responsive: true,
              scales: {
                x: {
                  ticks: {beginAtZero: true},
                },
                y: {
                  ticks: {beginAtZero: true},
                },
              },
            };
            // @ts-ignore
            document.getElementById('monthInfo').innerText = "";
            // @ts-ignore
            document.getElementById('accommodationInfo').innerText = "";
            // @ts-ignore
            document.getElementById('yearInfo').innerText = "Start date: " + startDate.toLocaleDateString() + "\nEnd date: " + endDate.toLocaleDateString();
            for (const rep of this.reports) {
              // @ts-ignore
              document.getElementById('accommodationInfo').innerText +=
                "\n" + rep.accommodationName + "\nTotal profit: " + rep.totalProfit + "\nNumber of reservations:" + rep.numberOfReservations + "\n";
            }
          },
          error: (_) => {
            console.log("Error fetching time slot reports!");
          }
        });
    }

  }
  getFormattedDate(date: Date): Date {
    const formattedDate = formatDate(date, 'yyyy-MM-dd', 'en-US');
    return new Date(formattedDate);
  }
  generateAccommodationReport() {
    const selectedAcc=this.form.value.accommodationName;
    const selectedYear=this.form.value.year;
    this.showReservationsChart=false;
    if(selectedYear!=null && selectedAcc!=null) {
      this.reportService.getAnnualReport(selectedAcc,selectedYear).subscribe({
        next: (data: AccommodationReport) => {
          this.report = data
          console.log(this.report);
          this.profitChart.data = [{ data: this.report.profitByMonth, label: 'Monthly Profit' }];
          this.profitChart.labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July','August','September','October','November','December'];
          this.profitChart.options = {
            responsive: true,
            scales: {
              x: {
                ticks: { beginAtZero: true },
              },
              y: {
                ticks: { beginAtZero: true },
              },
            },
          };
          // @ts-ignore
          document.getElementById('yearInfo').innerText ="Year: "+selectedYear;
          // @ts-ignore
          document.getElementById('accommodationInfo').innerText ="Accommodation: "+this.report.accommodationName;

          const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
          let monthNum=0;
          // @ts-ignore
          document.getElementById('monthInfo').innerText="Monthly profits:\n";
          for(const profit of this.report.profitByMonth){
            const monthName = monthNames[monthNum];
            monthNum+=1;
            // @ts-ignore
            document.getElementById('monthInfo').innerText += `${monthName}: ${parseInt(profit)}\n`;
          }
        },
        error: (_) => {console.log("Greska!")}
      });
    }
  }

  private loadAccommodations(hostId: number) {
    this.accommodationService.getAll(hostId).subscribe({
      next: (data: Accommodation[]) => {
        this.accommodations = data
      },
      error: (_) => {console.log("Greska!")}
    });

  }

  downloadReport() {
    const data = document.getElementById('downloadDiv');
    // @ts-ignore
    html2canvas(data).then(canvas => {
      const imgWidth = 208;
      const imgHeight = canvas.height * imgWidth / canvas.width;

      const pdf = new jsPDF({
        orientation: 'p',
        unit: 'mm',
        format: 'a4',
      });
      let position = 0;
      pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, position, imgWidth, imgHeight);
      pdf.save('report.pdf');
    });


  }
}
