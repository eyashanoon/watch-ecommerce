import { Injectable } from '@angular/core';
import { Client, IMessage, StompSubscription } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private stompClient!: Client;
  private messageSubject = new Subject<string>();
  private subscription?: StompSubscription;

  connect(senderId: number, onConnected?: () => void): void {
    const socket = new SockJS(`http://10.10.33.90:8080/ws?userId=${senderId}`);

    this.stompClient = new Client({
      webSocketFactory: () => socket as WebSocket,
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
    });

    this.stompClient.onConnect = () => {
      this.subscription = this.stompClient.subscribe('/user/queue/messages', (message: IMessage) => {
        if (message.body) {
          console.log(message.body);
          this.messageSubject.next(message.body);
        }
      });

      if (onConnected) onConnected();
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    this.stompClient.activate();
  }

  sendMessage(content: string, senderId: number, recipientId: number): void {
    if (!this.stompClient.connected) {
      console.error('STOMP client not connected yet!');
      return;
    }

    const message = {
      senderId: senderId,
      recipientId: recipientId,
      content: content,
    };

    this.stompClient.publish({
      destination: '/app/chat',
      body: JSON.stringify(message),
    });
  }

  getMessages(): Observable<string> {
    return this.messageSubject.asObservable();
  }

  disconnect(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    if (this.stompClient) {
      this.stompClient.deactivate();
    }
  }
}
