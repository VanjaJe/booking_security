import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CommentAndGrade} from "../../administrator/comments-and-grades/model/model.module";
import {AccommodationsService} from "../../accommodations/accommodations.service";
import {CommentsService} from "../comments.service";
import {Status, User} from "../../account/model/model.module";
import {UserService} from "../../account/account.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-comment-card',
  templateUrl: './comment-card.component.html',
  styleUrls: ['./comment-card.component.css']
})
export class CommentCardComponent {
  role:string='';
  userId:number;

  @Output()
  delComm = new EventEmitter<CommentAndGrade>()


  constructor( private commentService: CommentsService , private accountService: UserService, private snackBar: MatSnackBar) {
    this.role=accountService.getRole();
    this.userId=this.accountService.getUserId();

    console.log("USER ROLE " + this.role)
  }

  protected readonly Array = Array;
  @Input()
  comment: CommentAndGrade | undefined;
  getStarColor(starIndex: number): string {
    // @ts-ignore
    return starIndex <= this.comment?.rating ? 'filled-star' : 'empty-star';
  }


  public reportAccommodationComment(id: number|undefined) {

    this.commentService.reportComment(id, Status.REPORTED).subscribe({
      next: (data: CommentAndGrade) => {
        // this.comments = data
      },
      error: (err) => {
        if (err.status===400) {
          console.log(err);
          this.snackBar.open("Comment is reported", 'Close', {
            duration: 3000,
          });
        }
        console.log("Greska");
      }
    })
  }


  public deleteAccommodationComment(id: number|undefined) {
    this.commentService.deleteComment(id).subscribe({
      next: data => {
        this.delComm.emit();
        this.snackBar.open("Delete successful", 'Close', {
          duration: 3000,
        });
      },
      error: (_) => {
        console.log("greeskaa")
      }
    });
  }
}
