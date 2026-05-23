import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Campaign } from './campaign';

@Injectable({
  providedIn: 'root',
})
export class CampaignService {
  private readonly baseUrl = `${environment.apiHost}/campaign`;
  private readonly http = inject(HttpClient);

  getCampaigns() {
    return this.http.get<Campaign[]>(this.baseUrl);
  }
}
