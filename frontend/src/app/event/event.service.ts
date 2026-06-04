import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private readonly baseUrl = `${environment.apiHost}/event`;
  private readonly http = inject(HttpClient);

  createEvent(eventData: NewEvent) {
    const formData = new FormData();

    for (const file of eventData.images) {
      formData.append('images', file);
    }
    formData.append(
      'event',
      new Blob([JSON.stringify({ ...eventData, images: undefined })], { type: 'application/json' }),
    );
    return this.http.post(this.baseUrl, formData);
  }
}

interface NewEvent {
  name: string;
  description: string;
  campaignId: number;
  goal: number;
  date: Date;
  giftCardQuantity: number;
  suggestedGiftCardValue: number;
  images: File[];
}
