import { HttpHeaders } from '@angular/common/http';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { FileUploadModule } from 'primeng/fileupload';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TextareaModule } from 'primeng/textarea';
import { ToastModule } from 'primeng/toast';
import { Campaign } from '../../campaign/campaign';
import { CampaignService } from '../../campaign/campaign.service';
import { EventService } from '../event.service';
import { MessageService } from 'primeng/api';
import { finalize } from 'rxjs';

interface SelectedImage {
  file: File;
  url: string;
}

type EventFormGroup = FormGroup<{
  name: FormControl<string>;
  description: FormControl<string>;
  campaign: FormControl<number | null>;
  goal: FormControl<number | null>;
  date: FormControl<Date | null>;
  giftCardQuantity: FormControl<number | null>;
  suggestedGiftCardValue: FormControl<number | null>;
  images: FormControl<SelectedImage[] | null>;
}>;

@Component({
  selector: 'app-event-form',
  imports: [
    ButtonModule,
    DatePickerModule,
    IftaLabelModule,
    InputNumberModule,
    InputTextModule,
    ReactiveFormsModule,
    SelectModule,
    TextareaModule,
    FileUploadModule,
    ToastModule,
  ],
  providers: [MessageService],
  templateUrl: './event-form.html',
  styleUrl: './event-form.scss',
})
export class EventForm implements OnInit {
  private readonly campaignService = inject(CampaignService);
  private readonly eventService = inject(EventService);
  private readonly messageService = inject(MessageService);

  protected campaigns: Campaign[] = [];

  protected fileUploadHeaders = new HttpHeaders().append('Content-Type', `multipart/form-data`);
  protected maxFileSize = 10485760;

  protected isSubmitting = signal(false);

  protected readonly eventForm: EventFormGroup = new FormGroup({
    name: new FormControl('', { nonNullable: true, validators: Validators.required }),
    description: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(2000)],
    }),
    campaign: new FormControl<number | null>(null, Validators.required),
    goal: new FormControl<number | null>(null, Validators.required),
    date: new FormControl<Date | null>(null, Validators.required),
    giftCardQuantity: new FormControl<number | null>(null, Validators.required),
    suggestedGiftCardValue: new FormControl<number | null>(null, Validators.required),
    images: new FormControl<SelectedImage[] | null>(null, [Validators.required, Validators.minLength(1)]),
  });

  ngOnInit(): void {
    const campaignId = Number(history.state['campaignId']);

    this.campaignService.getCampaigns().subscribe((campaigns) => {
      this.campaigns = campaigns;

      if (campaigns.some((campaign) => campaign.id === campaignId)) {
        this.eventForm.patchValue({ campaign: campaignId });
      }
    });
  }

  protected createEvent(): void {
    if (this.eventForm.invalid) {
      this.eventForm.markAllAsTouched();
      return;
    }

    this.isSubmitting.set(true);

    const formValue = this.eventForm.getRawValue();

    this.eventService
      .createEvent({
        name: formValue.name || '',
        description: formValue.description || '',
        campaignId: formValue.campaign || 0,
        goal: formValue.goal || 0,
        date: formValue.date || new Date(),
        giftCardQuantity: formValue.giftCardQuantity || 0,
        suggestedGiftCardValue: formValue.suggestedGiftCardValue || 0,
        images: formValue.images?.map((f) => f.file) || [],
      })
      .pipe(
        finalize(() => {
          this.isSubmitting.set(false);
        })
      )
      .subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Evento criado com sucesso!' });
          this.eventForm.reset();
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Ocorreu um erro ao criar o evento. Por favor, tente novamente.' });
        },
      });
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const files = Array.from(input.files ?? []);

    if (!files.length) {
      return;
    }

    const currentFiles = this.eventForm.value.images || [];
    const isSameFile = (a: File, b: File): boolean =>
      a.name === b.name && a.size === b.size && a.lastModified === b.lastModified;

    const updatedFiles = currentFiles
      // keep existing files that are still selected
      .filter((currentFile) => files.some((file) => isSameFile(file, currentFile.file)))
      .concat(
        files
          // add new files that weren't previously selected
          .filter((file) => !currentFiles.some((currentFile) => isSameFile(file, currentFile.file)))
          .map((file) => ({
            file,
            url: URL.createObjectURL(file),
          })),
      )
      .slice(0, 4);

    this.eventForm.patchValue({ images: updatedFiles });
  }

  onImageRemoved(image: SelectedImage): void {
    const currentFiles = this.eventForm.value.images || [];
    const updatedFiles = currentFiles.filter((f) => f.file.name !== image.file.name);
    this.eventForm.patchValue({ images: updatedFiles });
  }
}
