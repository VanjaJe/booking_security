import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-reservations-chart',
  templateUrl: './reservations-chart.component.html',
  styleUrls: ['./reservations-chart.component.css']
})
export class ReservationsChartComponent {
  @Input() lineChartData: any[];
  @Input() lineChartLabels: string[];
  @Input() lineChartOptions: any = {
    responsive: true,
  };
  @Input() lineChartLegend = true;
  @Input() lineChartType = 'line';
  @Input() lineChartColor: any = {
    backgroundColor: '#D3CBE0',
  };
  ngOnChanges() {
    this.lineChartData.forEach(dataset => {
      dataset.backgroundColor = this.lineChartColor.backgroundColor;
    });
  }

}
