import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommentCardComponent } from './comment-card/comment-card.component';
import {MaterialModule} from "../infrastructure/material/material.module";



@NgModule({
  declarations: [
    CommentCardComponent
  ],
  exports: [
    CommentCardComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
  ]
})
export class CommentsModule { }
