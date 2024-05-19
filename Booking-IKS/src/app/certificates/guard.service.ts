import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {UserService} from "../account/account.service";
import {KeycloakService} from "./keycloak.service";
import {waitForAsync} from "@angular/core/testing";
import {UserProfile} from "../account/model/model.module";


@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate{

  constructor(private keycloakService:KeycloakService, private router: Router
              ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (this.keycloakService.keycloak?.authenticated) {
      return true; // User is authenticated, allow access to the route
    } else {
      this.keycloakService.login();
      // @ts-ignore
      return false; // Navigation is blocked
    }
  }
}
