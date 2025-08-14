import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router'; // ✅ ADD THIS

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, RouterOutlet], // ✅ RouterModule needed
  templateUrl: './app.html',
  styleUrls: ['./app.scss'],
})
export class App {
  protected readonly title = signal('chat-client');
}
