import { Component, inject } from '@angular/core';
import { CampaignService } from '../campaign.service';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Campaign } from '../campaign';
import { map, Observable, of } from 'rxjs';

@Component({
  selector: 'app-campaign-list',
  imports: [CommonModule, NgOptimizedImage],
  templateUrl: './campaign-list.html',
  styleUrl: './campaign-list.scss',
})
export class CampaignList {
  private readonly campaignService = inject(CampaignService);

  protected campaigns$: Observable<Campaign[]>;

  constructor() {
    this.campaigns$ =this.campaignService.getCampaigns();
  }
}
