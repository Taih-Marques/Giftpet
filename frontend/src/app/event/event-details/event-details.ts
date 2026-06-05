import { CommonModule } from '@angular/common';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { InputTextModule } from 'primeng/inputtext';
import { EventService } from '../event.service';
import { Event } from '../event';

@Component({
  selector: 'app-event-details',
  standalone: true,
  imports: [CommonModule, CarouselModule, InputTextModule, ButtonModule],
  templateUrl: './event-details.html',
  styleUrl: './event-details.scss',
})
export class EventDetailsComponent implements OnInit {
  private readonly eventService = inject(EventService);
  private readonly route = inject(ActivatedRoute);

  protected readonly event = signal<Event | null>(null);
  protected readonly placeholderImage = 'https://via.placeholder.com/960x540?text=Evento+GiftPet';
  protected readonly selectedImageIndex = signal(0);

  protected readonly imageUrls = computed(() => {
    const eventData = this.event();
    return eventData?.images?.length
    ? eventData.images.map((image: { id: string }) => this.resolveImageUrl(image))
    : [this.placeholderImage];
  });
  protected readonly selectedImageUrl = computed(() => {
    const urls = this.imageUrls();
    const index = this.selectedImageIndex();
    return urls[index] ?? this.placeholderImage;
  });

  ngOnInit(): void {
    this.route.paramMap.subscribe((paramMap: ParamMap) => {
      const eventId = Number(paramMap.get('id'));
      if (!eventId || Number.isNaN(eventId)) {
        return;
      }

      this.eventService.findById(eventId).subscribe((eventData: Event) => {
        this.event.set(eventData);
        this.selectedImageIndex.set(0);
      });
    });
  }

  protected selectImage(index: number): void {
    this.selectedImageIndex.set(index);
  }

  private resolveImageUrl(image: { id: string }): string {
    return `/api/image/content/${encodeURIComponent(image.id)}`;
  }
}
