import { Component, OnInit, ViewChild } from '@angular/core';
import {CommentAndGradeService } from '../administrator.service';
import { CommentAndGrade } from '../comments-and-grades/model/model.module';
import { AccommodationsService } from 'src/app/accommodations/accommodations.service';
import { Accommodation } from 'src/app/accommodations/accommodation/model/model.module';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/account/account.service';
import {Host, User} from "src/app/account/model/model.module";
import {CommentsService} from "../../comments/comments.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-comments-and-grades-cards',
  templateUrl: './comments-and-grades-cards.component.html',
  styleUrls: ['./comments-and-grades-cards.component.css']
})
export class CommentsAndGradesCardsComponent implements OnInit {
  images: string[] =[];
  image:string;
  commentsAndGrades: CommentAndGrade[] =[];
  accommodation:Accommodation;
  currentPage: number = 1;
  itemsPerPage: number = 5;
  clickedCommentAndGrade: string='';
  sanitizer: any;

  constructor(private service: CommentAndGradeService, private userService:UserService,
              private commentService: CommentsService, private root:ActivatedRoute,
              private accommodationService:AccommodationsService) {
  }



  ngOnInit(): void {
    if (!(this.userService.getRole()==="ROLE_ADMIN")) {

      this.root.params.subscribe((params) =>{
        const id=+params['id']
        this.accommodationService.getAccommodation(id).subscribe({
          next:(data:Accommodation)=>{
            this.accommodation=data;

            this.commentService.getHostComments(this.accommodation.host.id).subscribe({
              next:(data: CommentAndGrade[]) => {
                console.log("VANJAANAJAAJAA")
                console.log(data);
                this.commentsAndGrades = data
              },
              error: (_) => {console.log("Greska!")}
            })
          }
          })
        })
    }

    else {
      this.service.getAllReportedAndPanding().subscribe({
      next: (data: CommentAndGrade[]) => {
        this.commentsAndGrades = data;

        // If accommodationId is defined, find the corresponding CommentAndGrade
        for (const commentAndGrade of this.commentsAndGrades) {
            // Assuming there's a property 'accommodationId' in CommentAndGrade
            if (commentAndGrade.accommodation?.id) {
              this.accommodationService.getAccommodation(commentAndGrade.accommodation.id).subscribe({
                next: (data: Accommodation) => {
                  commentAndGrade.accommodation = data;
                  // console.log(this.accommodation);
                }})
            }
          }
      },
      error: (_) => {
        console.log("Error fetching data!");
      }
    });
    }
  }


  onCommentAndGradeClicked(commentAndGrade: CommentAndGrade): void {
    this.ngOnInit();
    this.clickedCommentAndGrade = commentAndGrade.text + " " + commentAndGrade.id;
  }

  get paginatedComments(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.commentsAndGrades.slice(startIndex, endIndex);
  }

  onPageChange(pageNumber: number): void {
    this.currentPage = pageNumber;
    window.scrollTo({
      top: 0,
      behavior: 'auto'
    });
  }

  getPages(): number[] {
    const totalItems = this.commentsAndGrades.length;
    const totalPages = Math.ceil(totalItems / this.itemsPerPage);
    return Array.from({ length: totalPages }, (_, index) => index + 1);
  }

  decodeBase64AndSanitize(image: string): string {
    console.log(image);
    const decodedImage = atob(image);
    const blob = new Blob([new Uint8Array([...decodedImage].map(char => char.charCodeAt(0)))], { type: 'image/png' });
    const imageUrl = URL.createObjectURL(blob);
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl) as string;
  }

}

