import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {KeycloakService} from "../certificates/keycloak.service";
import {UserService} from "../account/account.service";

@Injectable()
export class InterceptService implements HttpInterceptor{

  constructor(private keycloakService:KeycloakService, private userService:UserService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token=this.keycloakService.keycloak?.token;
    if(token){
      const authReq=req.clone({
        headers:new HttpHeaders({
          Authorization: `Bearer ${token}`
        })
      });

      localStorage.setItem('user', JSON.stringify(token));
      console.log("Tokenciccc",JSON.stringify(token));
      this.userService.setUser();

      return  next.handle(authReq);
    }
    return  next.handle(req);

    // const item = localStorage.getItem('user');
    // // @ts-ignore
    // const decodedItem = JSON.parse(item);
    // console.log("IOVDEEEEEEEEEEEE")
    // console.log(decodedItem);
    //
    // if (item) {
    //   console.log("USAO I OVDE")
    //   const cloned = req.clone({
    //     //headers: req.headers.set('X-Auth-Token', decodedItem.token)
    //     headers: req.headers.set('Authorization', "Bearer " +  decodedItem.accessToken)
    //   });
    //
    //   return next.handle(cloned);
    // } else {
    //   console.log("ELSEEEEEEEEEEEE")
    //   return next.handle(req);
    // }
  }
}

