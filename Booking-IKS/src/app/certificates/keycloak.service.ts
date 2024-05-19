import { Injectable } from '@angular/core';
import Keycloak from "keycloak-js";
import {User, UserProfile} from "../account/model/model.module";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined
  public profile:UserProfile | undefined

  get keycloak(){
    if(!this._keycloak){
      this._keycloak=new Keycloak({
        url:"http://localhost:8080",
        realm:"BookingKeycloak",
        clientId:"login-app"
      },);
    }
    return this._keycloak
  }

  constructor() { }

  async init() {
    const authenticated=await this.keycloak?.init({
      onLoad:'check-sso',
    });

    if(authenticated){
        this.profile=(await this.keycloak?.loadUserProfile()) as UserProfile;
        this.profile.token=this.keycloak?.token;
    }

  }
  login(){
    return this.keycloak?.login({redirectUri:'https://localhost:4200/home'})
  }
  logout(){
    return this.keycloak?.logout({redirectUri:'https://localhost:4200'});
  }
}
