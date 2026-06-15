import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Event } from './event';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private readonly baseUrl = `${environment.apiHost}/event`;
  private readonly http = inject(HttpClient);

  createEvent(eventData: NewEventRequest): Observable<NewEventResponse> {
    const formData = new FormData();

    for (const file of eventData.images) {
      formData.append('images', file);
    }
    formData.append(
      'event',
      new Blob([JSON.stringify({ ...eventData, images: undefined })], { type: 'application/json' }),
    );
    return this.http.post<NewEventResponse>(this.baseUrl, formData);
  }

  findById(id: number): Observable<Event> {
    return this.http.get<Event>(`${this.baseUrl}/${id}`);
  }

  validateGiftCard(giftCardCode: string, eventId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/validate-giftcard`, { code:giftCardCode, eventId });
  }
}

interface NewEventRequest {
  name: string;
  description: string;
  campaignId: number;
  goal: number;
  date: Date;
  giftCardQuantity: number;
  suggestedGiftCardValue: number;
  images: File[];
}

interface NewEventResponse {
  id: number;
}
