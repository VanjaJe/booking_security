import {Component, OnInit} from '@angular/core';
import {ReservationRequest} from "../accommodations/accommodation/model/model.module";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AccountActivateService} from "./account-activate.service";

@Component({
  selector: 'app-account-activation',
  templateUrl: './account-activation.component.html',
  styleUrls: ['./account-activation.component.css']
})
export class AccountActivationComponent implements OnInit{
  // @ts-ignore
  username: string;
  // @ts-ignore
  accessToken: string;
  constructor(private service:AccountActivateService, private route:ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.username = params['username'];
      this.accessToken = params['accessToken'];
    });
  }

  activateAccount() {
    this.service.activateAccount(this.accessToken, this.username).subscribe(
      {
        next: (data: string) => {
          console.log(data);
        },
        error: (_) => {
        }
      }
    );
  }
}
