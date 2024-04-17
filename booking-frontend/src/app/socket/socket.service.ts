import { Injectable } from '@angular/core';
import {Message} from "../account/login/model/model";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {environment} from "../../env/env";
import {HttpHeaders} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  private serverUrl = 'http://localhost:8080/socket'
  private stompClient: any;
  isLoaded: boolean = false;
  isCustomSocketOpened = false;
  messages: Message[] = [];
  // headers:HttpHeaders;

  constructor(private snackBar: MatSnackBar) { }


  initializeWebSocketConnection(userId:number) {
    // serverUrl je vrednost koju smo definisali u registerStompEndpoints() metodi na serveru
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;

    this.stompClient.connect({}, function () {
      that.isLoaded = true;
      that.openGlobalSocket()
      that.openSocket(userId)
    });
  }
  openGlobalSocket() {
    console.log("GLOBALNA")
    if (this.isLoaded) {
      this.stompClient.subscribe("/socket-publisher", (message: { body: string; }) => {
        this.handleResult(message);
      });
    }
  }
  handleResult(message: { body: string; }) {
    console.log("AAA")
    if (message.body) {
      let messageResult: Message = JSON.parse(message.body);
      this.messages.push(messageResult);

      this.snackBar.open(messageResult.message, "close",{
        horizontalPosition: 'start',
        verticalPosition: 'top',
        duration:10000
      });
    }
  }

  openSocket(userId:number) {
    console.log("POJEDINACNA")
    console.log(this.isLoaded)
    if (this.isLoaded) {
      this.isCustomSocketOpened = true;
      console.log("USEEERRRR" + userId)
      this.stompClient.subscribe("/socket-publisher/" + userId, (message: { body: string; }) => {
        this.handleResult(message);
      });
    }
  }


  sendMessageUsingSocket(messageText:string, from:number,to:number) {
    let message: Message = {
      message: messageText,
      fromId: (from).toString(),
      toId: (to).toString()
    };

    this.stompClient.send("/socket-subscriber/send/message", {}, JSON.stringify(message));
  }

}
