import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class DonationService {
  private readonly baseUrl = `${environment.apiHost}/donation`;
  private readonly http = inject(HttpClient);

  simulateDonation(donation: SimulatedDonation): Observable<void> {
    return this.http.post<void>(this.baseUrl, donation);
  }
}

interface SimulatedDonation {
  fullName: string;
  email: string;
  cpf: string;
  amount: number;
  eventId: number;
  giftCardCode: string;
}
