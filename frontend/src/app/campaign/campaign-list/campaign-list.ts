import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { Campaign } from '../campaign';
import { CampaignService } from '../campaign.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-campaign-list',
  imports: [ButtonModule, CommonModule, InputTextModule, ReactiveFormsModule, NgOptimizedImage],
  templateUrl: './campaign-list.html',
  styleUrl: './campaign-list.scss',
})
export class CampaignList {
  private readonly campaignService = inject(CampaignService);

  protected campaigns$: Observable<Campaign[]>;
  protected searchText = new FormControl('');

  constructor() {
    this.campaigns$ = this.campaignService.getCampaigns();
  }

  findCampaigns() {
    this.campaigns$ = this.campaignService.getCampaigns(this.searchText.value?.trim() || undefined);
  }

}
