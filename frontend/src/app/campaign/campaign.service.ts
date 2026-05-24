import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Campaign } from './campaign';

@Injectable({
  providedIn: 'root',
})
export class CampaignService {
  private readonly baseUrl = `${environment.apiHost}/campaign`;
  private readonly http = inject(HttpClient);

  getCampaigns(searchText?: string) {
    let params = new HttpParams();
    if (searchText?.trim().length) {
      params = params.append('search', searchText);
    }
    return this.http.get<Campaign[]>(this.baseUrl, { params });
  }
}
