import { Routes } from '@angular/router';
import { ChatComponent } from './chat/chat.component'; // keep this import

export const routes: Routes = [
  { path: 'chat', component: ChatComponent }, // <-- use ChatComponent here
  { path: '', redirectTo: 'chat', pathMatch: 'full' },
];
