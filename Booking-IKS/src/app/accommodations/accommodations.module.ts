import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {ViewAccommodationsComponent} from "./view-accommodations/view-accommodations.component";
import {MaterialModule} from "../infrastructure/material/material.module";
import { AccommodationDetailsComponent } from './accommodation-details/accommodation-details.component';
import { AccommodationComponent } from './accommodation/accommodation.component';
import { AccommodationCardComponent } from './accommodation-card/accommodation-card.component';
import {RouterLink} from "@angular/router";
import {LayoutModule} from "../layout/layout.module";
import { CreateAccommodationComponent } from './create-accommodation/create-accommodation.component';
import { EditAccommodationsDatesComponent } from './edit-accommodations-dates/edit-accommodations-dates.component';
import {CommentCardComponent} from "../comments/comment-card/comment-card.component";
import {CommentsModule} from "../comments/comments.module";
import {MapModule} from "../map/map.module";
import {SharedModule} from "../shared/shared.module";
import { AccommodationUpdateComponent } from './accommodation-update/accommodation-update.component';
import { AccountManagementComponent } from '../account/account-management/account-management.component';

@NgModule({
  declarations: [
    ViewAccommodationsComponent,
    AccommodationDetailsComponent,
    AccommodationComponent,
    AccommodationCardComponent,
    CreateAccommodationComponent,
    EditAccommodationsDatesComponent,
    AccommodationUpdateComponent
  ],
    imports: [
        CommonModule,
        CommentsModule,
        MapModule,
        MaterialModule,
        RouterLink,
        LayoutModule,
        SharedModule,
        NgOptimizedImage
    ],
  exports: [
    ViewAccommodationsComponent,
    AccommodationDetailsComponent,
    AccommodationCardComponent,
    AccommodationUpdateComponent
  ]
})
export class AccommodationsModule { }
