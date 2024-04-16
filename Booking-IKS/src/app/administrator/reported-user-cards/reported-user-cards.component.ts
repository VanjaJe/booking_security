import { Component, OnInit } from '@angular/core';
import { Status, User } from 'src/app/account/model/model.module';
import { ReportedUserService } from './reported-user-cards.service';

@Component({
  selector: 'app-reported-user-cards',
  templateUrl: './reported-user-cards.component.html',
  styleUrls: ['./reported-user-cards.component.css']
})

export class ReportedUserCardsComponent implements OnInit {

  reportedUsers: User[] =[];
  currentPage: number = 1;
  itemsPerPage: number = 5;
  clickedReportedUser: string='';
  
  constructor(private service: ReportedUserService) {
  }

  ngOnInit(): void {
    this.service.getUsersByStatus('REPORTED').subscribe({
      next:(data: User[]) => {
        this.reportedUsers = data
      },
      error: (_) => {console.log("Greska!")}
    })
  }

  onReportedUserClick(reportedUser: User): void {
    this.clickedReportedUser = reportedUser.firstName + " " + reportedUser.lastName;
  }


  get paginatedReportedUser(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.reportedUsers.slice(startIndex, endIndex);
  }

  onPageChange(pageNumber: number): void {
    this.currentPage = pageNumber;
    window.scrollTo({
      top: 0,
      behavior: 'auto' 
  });
  }

  getPages(): number[] {
    const totalItems = this.reportedUsers.length;
    const totalPages = Math.ceil(totalItems / this.itemsPerPage);
    return Array.from({ length: totalPages }, (_, index) => index + 1);
  }
}

