import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class InterceptService implements HttpInterceptor{

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const item = localStorage.getItem('user');
    // @ts-ignore
    const decodedItem = JSON.parse(item);
    console.log("IOVDEEEEEEEEEEEE")
    console.log(decodedItem);

    if (item) {
      console.log("USAO I OVDE")
      const cloned = req.clone({
        //headers: req.headers.set('X-Auth-Token', decodedItem.token)
        headers: req.headers.set('Authorization', "Bearer " +  decodedItem.accessToken)
      });

      return next.handle(cloned);
    } else {
      console.log("ELSEEEEEEEEEEEE")
      return next.handle(req);
    }
  }
}

