import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommentAndGrade } from '../comments-and-grades/model/model.module';
import { CommentAndGradeService } from '../administrator.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from 'src/app/account/account.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import {Status} from "../../account/model/model.module";
import {CommentsService} from "../../comments/comments.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-comments-and-grades-card',
  templateUrl: './comments-and-grades-card.component.html',
  styleUrls: ['./comments-and-grades-card.component.css']
})

 
export class CommentsAndGradesCardComponent implements OnInit{
  images: string[] =[];
  role:string;
  image:SafeUrl= '../../../assets/images/addpicture.png';
  protected readonly Array = Array;

  @Input()
  commentAndGrade: CommentAndGrade |any;

  @Output()
  clicked: EventEmitter<CommentAndGrade> = new EventEmitter<CommentAndGrade>();


  onCommentAndGradeClicked(): void {
    this.commentAndGrade = this.commentAndGrade;
    this.clicked.emit(this.commentAndGrade);
  }

  constructor(private service: CommentAndGradeService,  private commentService: CommentsService, private router:Router, private location: Location, private userService: UserService, 
    private sanitizer: DomSanitizer,  private snackBar: MatSnackBar) {
      this.role=userService.getRole();
  }

  ngOnInit(): void {
    console.log(this.commentAndGrade.guest.id);
    this.userService.getImages(this.commentAndGrade.guest.id).subscribe(
      (images) => { 
        this.images = images;
        if(this.images.length>0){
          this.image=this.decodeBase64AndSanitize(this.images[0])
        }  
      },
      (error) => {
        console.error('Error fetching images:', error);
      }
    );
  }
   


  // rating = this.commentAndGrade?.rating; // Replace with your actual rating
  // rating : number = 4.2

  getColorForStar(index: number, rating: number): string {
    const roundedRating = Math.round(rating);
    const filledStarColor = '#FFB703';
    const unfilledStarColor = '#555555';

  if (index < roundedRating) {
    return filledStarColor; // Fully filled star
  } else {
    return unfilledStarColor; // Same color for all unfilled stars
  }
    
    
  }
  
  declineComment() {
    console.log(this.commentAndGrade);
    this.service.decline(this.commentAndGrade).subscribe(
      {
        next: (data: CommentAndGrade) => {
          this.commentAndGrade = data;
          this.router.navigate(['home']);
        },
        error: (_) => {
        }
      }
    );
  }
  approveComment() {
    console.log(this.commentAndGrade);
    this.service.approve(this.commentAndGrade).subscribe(
      {
        next: (data: CommentAndGrade) => {
          this.commentAndGrade = data;
          this.router.navigate(['home']);
        },
        error: (_) => {
        }
      }
    );
  }

  decodeBase64AndSanitize(image: string): SafeUrl {
    const decodedImage = atob(image);
    const blob = new Blob([new Uint8Array([...decodedImage].map(char => char.charCodeAt(0)))], { type: 'image/png' });
    const imageUrl = URL.createObjectURL(blob);
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }
  
  


  public reportHostComment(id: number|undefined) {
    this.commentService.reportComment(id, Status.REPORTED).subscribe({
      next: (data: CommentAndGrade) => {
        this.snackBar.open("Comment is reported!", 'Close', {
          duration: 3000,
        });
      },
      error: (err) => {
        this.snackBar.open("You cannot report a comment!", 'Close', {
          duration: 3000,
        });
        console.log("Greska");
      }
    })
  }

  public deleteHostComment(id: number|undefined) {
    this.commentService.deleteComment(id).subscribe({
      next: data => {
        this.onCommentAndGradeClicked();
        // this.status = 'Delete successful';
      },
      error: (_) => {
        console.log("greeskaa")
      }
    });
  }
  
}
