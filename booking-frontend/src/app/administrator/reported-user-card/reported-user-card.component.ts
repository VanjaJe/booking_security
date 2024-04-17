import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/account/model/model.module';
import { CommentAndGrade } from '../comments-and-grades/model/model.module';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { CommentAndGradeService } from '../administrator.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { UserService } from 'src/app/account/account.service';

@Component({
  selector: 'app-reported-user-card',
  templateUrl: './reported-user-card.component.html',
  styleUrls: ['./reported-user-card.component.css'],
  providers: [Location],
})
export class ReportedUserCardComponent implements OnInit {
  images: string[] =[];
  image:SafeUrl= '../../../assets/images/addpicture.png';
  protected readonly Array = Array;

  @Input()
  reportedUser: User |any;

  @Output()
  clicked: EventEmitter<User> = new EventEmitter<User>();

  onReportedUserClick(): void {
    this.clicked.emit(this.reportedUser);
    
  }

  constructor(private service: CommentAndGradeService,  private router:Router, private location: Location, private userService: UserService, 
    private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    console.log(this.reportedUser.id);
    this.userService.getImages(this.reportedUser.id).subscribe(
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
   
  
  decodeBase64AndSanitize(image: string): SafeUrl {
    const decodedImage = atob(image);
    const blob = new Blob([new Uint8Array([...decodedImage].map(char => char.charCodeAt(0)))], { type: 'image/png' });
    const imageUrl = URL.createObjectURL(blob);
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
  }
  

  // rating = this.commentAndGrade?.rating; // Replace with your actual rating
  // rating : number = 4.2
  
  getColorForStar(index: number, rating: number): string {
    const roundedRating = Math.round(rating);
    
    if (index < roundedRating) {
      return '#FFB703'; // Fully filled star
    } else if (index === roundedRating) {
      const decimalPart = rating - roundedRating;
      return `linear-gradient(90deg, #FFB703 ${decimalPart * 100}%, grey ${decimalPart * 100}%)`; // Partially filled star
    } else {
      return 'grey'; // Unfilled star
    }
  }
  
  blockUser(userId: number) {
    this.userService.block(this.reportedUser).subscribe(
      () => {
        console.log('User blocked successfully.');
        localStorage.removeItem('user');
        this.router.navigate(['logIn']);
        this.userService.setUser();
      },
      (error: Error) => {
        console.error('Error deleting user:', error);
      }
    );
  }
  }
