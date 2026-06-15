import { CommonModule } from '@angular/common';
import {
  Component,
  DestroyRef,
  ElementRef,
  OnInit,
  computed,
  inject,
  signal,
  viewChild,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { InputTextModule } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { ToastModule } from 'primeng/toast';
import { EventService } from '../event.service';
import { Event } from '../event';
import { Donate } from '../donate/donate';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-event-details',
  standalone: true,
  imports: [CommonModule, CarouselModule, InputTextModule, ButtonModule, ToastModule],
  providers: [MessageService, DialogService],
  templateUrl: './event-details.html',
  styleUrl: './event-details.scss',
})
export class EventDetailsComponent implements OnInit {
  private static readonly GIFT_CARD_CODE_PATTERN = /^GP[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$/i;

  private readonly eventService = inject(EventService);
  private readonly dialogService = inject(DialogService);
  private readonly messageService = inject(MessageService);
  private readonly route = inject(ActivatedRoute);
  private readonly destroyRef = inject(DestroyRef);
  private eventId?: number;

  protected readonly giftCardCode =
    viewChild.required<ElementRef<HTMLInputElement>>('giftCardCode');
  protected readonly event = signal<Event | null>(null);
  protected readonly placeholderImage = 'https://via.placeholder.com/960x540?text=Evento+GiftPet';
  protected readonly selectedImageIndex = signal(0);
  protected isDonating = signal(false);

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
      this.eventId = Number(paramMap.get('id'));
      if (!this.eventId || Number.isNaN(this.eventId)) {
        return;
      }
      this.findEvent(true);
    });
  }

  private findEvent(resetSelectedImage = false): void {
    if (!this.eventId) {
      return;
    }
    this.eventService.findById(this.eventId).subscribe((eventData: Event) => {
      this.event.set(eventData);

      if (resetSelectedImage) {
        this.selectedImageIndex.set(0);
      }
    });
  }

  protected selectImage(imageUrl: string): void {
    const index = this.imageUrls().indexOf(imageUrl);

    if (index >= 0) {
      this.selectedImageIndex.set(index);
    }
  }

  protected donate(): void {
    const giftCardInput = this.giftCardCode().nativeElement;
    const trimmedCode = giftCardInput.value.trim();
    if(!this.eventId){
      return;
    }

    if (!trimmedCode) {
      this.showGiftCardError('Informe o código do Gift Card.');
      return;
    }

    if (!EventDetailsComponent.GIFT_CARD_CODE_PATTERN.test(trimmedCode)) {
      this.showGiftCardError('O código deve seguir o formato GPXXXX-XXXX-XXXX.');
      return;
    }
    this.isDonating.set(true);

    this.eventService.validateGiftCard(trimmedCode, this.eventId).subscribe({
      next: () => {
        this.dialogService
          .open(Donate, {
            modal: true,
            showHeader: false,
            data: {
              eventName: this.event()?.name ?? '',
              eventId: this.event()?.id ?? '',
              giftCardCode: trimmedCode,
            },
          })
          ?.onClose.pipe(
            takeUntilDestroyed(this.destroyRef),
            finalize(() => {
              this.isDonating.set(false);
            }),
          )
          .subscribe((success) => {
            if (success) {
              giftCardInput.value = '';
              this.showDonationSucess();
              this.findEvent();
            }
          });
      },
      error: () => {
        this.showGiftCardError();
        this.isDonating.set(false);
      },
    });
  }

  private showGiftCardError(detail?: string): void {
    this.messageService.add({
      severity: 'error',
      summary: 'Gift Card inválido',
      detail,
    });
  }
  private showDonationSucess(): void {
    this.messageService.add({
      severity: 'success',
      summary: 'Sucesso',
      detail: 'Doação realizada com sucesso!',
    });
  }

  private resolveImageUrl(image: { id: string }): string {
    return `/api/image/content/${encodeURIComponent(image.id)}`;
  }
}
