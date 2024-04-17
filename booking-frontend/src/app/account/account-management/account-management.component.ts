import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { UserService } from "../account.service";
import { Account, Address, Role, Status, User, Image} from "../model/model.module";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JWT_OPTIONS, JwtInterceptor } from '@auth0/angular-jwt';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-account-management',
  templateUrl: './account-management.component.html',
  styleUrls: ['./account-management.component.css']
})
export class AccountManagementComponent implements OnInit {
  user: User | undefined;
  role: string ;
  images: string[] =[];
  image:string = '../../../assets/images/addpicture.png';

  passwordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    return password === confirmPassword ? null : { 'passwordMismatch': true };
  };

  updateUserForm = new FormGroup({
    firstName: new FormControl('', [Validators.required, Validators.minLength(3)]),
    lastName: new FormControl('', [Validators.required, Validators.minLength(3)]),
    country: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    address: new FormControl('', Validators.required),
    phoneNumber: new FormControl('', [Validators.required, Validators.pattern(/^\d+$/)]),
    username: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    confirmPassword: new FormControl('', [Validators.required, Validators.minLength(6), this.matchValues('password')]),
    picturePath: new FormControl('')
  });

  matchValues(field: string) {
    return (control: AbstractControl) => {
      const fieldValue = control.value;
      const matchingControl = control.root.get(field);

      if (matchingControl && fieldValue !== matchingControl.value) {
        return { mismatchedValues: true };
      }
      return null;
    };
  }

  constructor(private service: UserService,private router: Router,private cdr: ChangeDetectorRef, private sanitizer:DomSanitizer) {}

  ngOnInit(): void {
    const deleteButton = document.getElementById('deleteButton') as HTMLButtonElement;
    if (this.service.getRole() == 'ROLE_ADMIN') {
        deleteButton.style.visibility = 'hidden';
    }
    this.service.getUser(this.service.getUserId()).subscribe({
      next: (data: User) => {
        this.user = data;
        this.service.getImages(this.user?.id).subscribe(
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
        this.updateUserForm.patchValue({
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          country: this.user.address?.country,
          city: this.user.address?.city,
          address: this.user.address?.address,
          phoneNumber: this.user.phoneNumber,
          username: this.user.account?.username,
          password: this.user.account?.password,
          confirmPassword: this.user.account?.password,
          picturePath: this.user.picturePath
        });
      },
      error: (_) => {
        console.log("Error fetching user data!");
      },
    });
  }

  decodeBase64AndSanitize(image: string): string {
    const decodedImage = atob(image);
    const blob = new Blob([new Uint8Array([...decodedImage].map(char => char.charCodeAt(0)))], { type: 'image/png' });
    const imageUrl = URL.createObjectURL(blob);
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl) as string;
  }


  selectedImages: Image[] = [];

  onFileSelected(event: any):void {
    const files: FileList | null = event.target.files;
    if (files) {
      for (let i=0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          if (e.target) {
            const imageURL = e.target.result as string;
            const image : Image = {
              url: imageURL,
              file: files[i]
            }
            this.selectedImages.push(image);
            console.log(files[i]);
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }
  }

  updateUser() {
    if (this.updateUserForm.valid) {
      const address: Address = {
        city: this.updateUserForm.value.city as string || '',
        country: this.updateUserForm.value.country as string || '',
        address: this.updateUserForm.value.address as string || '',
      };

      console.log(this.user)

      const account: Account = {
        id: this.user?.account?.id,
        username: this.updateUserForm.value.username as string || '',
        password: this.updateUserForm.value.password as string || '',
        status: this.user?.account?.status as Status,
        roles: this.user?.account?.roles as Role[],
      };

      const updatedUser: User = {
        firstName: this.updateUserForm.value.firstName as string || '',
        lastName: this.updateUserForm.value.lastName as string || '',
        address: address,
        phoneNumber: this.updateUserForm.value.phoneNumber as string || '',
        account: account,
        id: this.user?.id as number,
        picturePath: this.updateUserForm.value.picturePath as string || '',
      };

      this.service.update(updatedUser).subscribe(
        (updatedUser) => {
          localStorage.removeItem('user');
          this.router.navigate(['logIn']);
          this.service.setUser();
          this.uploadPicture(this.user?.id as number);
        },
        (error) => {
          console.error('Error updating user', error);
        }
      );
    }
  }

  uploadPicture(userId : number) {
    const images : File[] = [];
    for (let image of this.selectedImages) {
      images.push(image.file);
    }

    // const idAccommodation = this.newAccommodation.id;
    // @ts-ignore
    this.service.uploadImage(images, userId).subscribe(
      {
        next: (data: User) => {
          // alert(data);
        },
        error: (_) => {
        }
      }
    );
  }

  deleteUser() {
    this.service.delete(this.user?.id as number).subscribe(
      () => {
        console.log('User deleted successfully.');
        localStorage.removeItem('user');
        this.router.navigate(['logIn']);
        this.service.setUser();
      },
      (error) => {
        console.error('Error deleting user:', error);
      }
    );
  }
}
