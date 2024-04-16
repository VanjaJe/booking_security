import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-annual-chart',
  templateUrl: './annual-chart.component.html',
  styleUrls: ['./annual-chart.component.css']
})
export class AnnualChartComponent {
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
