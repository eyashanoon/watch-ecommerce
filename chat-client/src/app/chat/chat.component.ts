import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../services/chat.service';

interface ChatMessage {
  content: string;
  senderId: number;
  recipientId: number;
  timestamp?: string;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  newMessage: string = '';
  senderId?: number;
  recipientId?: number;
  isSenderSet: boolean = false;
  messageSubscription?: Subscription;

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    // Do not connect here anymore; wait for senderId
  }

  confirmSender(): void {
    if (this.senderId && this.senderId > 0) {
      this.isSenderSet = true;

      this.chatService.connect(this.senderId, () => {
        console.log('STOMP connected');
      });

      this.messageSubscription = this.chatService.getMessages().subscribe((msgJson) => {
        try {
          const msg: ChatMessage = JSON.parse(msgJson);
          this.messages.push(msg);
          setTimeout(() => {
            const container = document.querySelector('.messages');
            if (container) container.scrollTop = container.scrollHeight;
          }, 0);
        } catch (e) {
          console.error('Error parsing incoming message:', e);
        }
      });
    } else {
      alert('Please enter a valid sender ID');
    }
  }

  sendMessage(): void {
    if (!this.newMessage.trim() || !this.recipientId) return;
    this.chatService.sendMessage(this.newMessage, this.senderId!, this.recipientId);
    this.newMessage = '';
  }

  ngOnDestroy(): void {
    this.messageSubscription?.unsubscribe();
    this.chatService.disconnect();
  }
}
