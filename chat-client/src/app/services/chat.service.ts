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

  connect(onConnected?: () => void): void {
    const socket = new SockJS('http://localhost:8080/ws'); // Your backend WebSocket endpoint

    this.stompClient = new Client({
      webSocketFactory: () => socket as WebSocket,
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
    });

    this.stompClient.onConnect = () => {
      // Subscribe to topic after connected
      this.subscription = this.stompClient.subscribe('/topic/messages', (message: IMessage) => {
        if (message.body) {
          this.messageSubject.next(message.body);
        }
      });

      // Notify the component that the connection is ready
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
      destination: '/app/chat.sendMessage',
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
