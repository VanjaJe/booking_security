import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportedUserCardsComponent } from './reported-user-cards/reported-user-cards.component';
import { ReportedUserCardComponent } from './reported-user-card/reported-user-card.component';
import { CommentsAndGradesCardsComponent } from './comments-and-grades-cards/comments-and-grades-cards.component';
import { CommentsAndGradesCardComponent } from './comments-and-grades-card/comments-and-grades-card.component';
import { MaterialModule } from '../infrastructure/material/material.module';
import { AccommodationApprovalCardsComponent } from './accommodation-approval-cards/accommodation-approval-cards.component';
import { AccommodationsModule } from "../accommodations/accommodations.module";



@NgModule({
    declarations: [
        ReportedUserCardComponent,
        ReportedUserCardsComponent,
        CommentsAndGradesCardComponent,
        CommentsAndGradesCardsComponent,
        AccommodationApprovalCardsComponent,
    ],
    exports: [
        CommentsAndGradesCardComponent,
        CommentsAndGradesCardsComponent,
        ReportedUserCardComponent,
        ReportedUserCardsComponent,
        AccommodationApprovalCardsComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        AccommodationsModule
    ]
})
export class AdministratorModule { }
