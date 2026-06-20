import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Event } from '../event';
import { EventService } from '../event.service';

@Component({
  selector: 'app-event-list',
  imports: [CommonModule, NgOptimizedImage],
  templateUrl: './event-list.html',
  styleUrl: './event-list.scss',
})
export class EventList {
  private readonly eventService = inject(EventService);
  private readonly router = inject(Router);

  protected events$: Observable<Event[]>;

  constructor() {
    this.events$ = this.eventService.getEvents();
  }

  openEventDetails(eventId: number): void {
    this.router.navigate(['/event', eventId]);
  }
}
