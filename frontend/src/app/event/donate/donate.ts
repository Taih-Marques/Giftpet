import { Component, inject, signal } from '@angular/core';
import { cpfValidator } from 'cpf-cnpj-validator/angular';
import { InputMaskModule } from 'primeng/inputmask';
import { MessageModule } from 'primeng/message';

import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { finalize } from 'rxjs';
import { DonationService } from '../../donation/donation.service';

@Component({
  selector: 'app-donate',
  imports: [
    ButtonModule,
    IftaLabelModule,
    InputMaskModule,
    MessageModule,
    InputTextModule,
    InputNumberModule,
    ReactiveFormsModule,
  ],
  templateUrl: './donate.html',
  styleUrl: './donate.scss',
  providers: [MessageService],
})
export class Donate {
  private readonly dialogRef = inject(DynamicDialogRef);
  private readonly dialogConfig = inject(DynamicDialogConfig);
  private readonly donationService = inject(DonationService);
  private readonly messageService = inject(MessageService);

  protected readonly eventName = this.dialogConfig.data?.eventName ?? '';

  protected readonly testMode = true;
  protected isSubmitting = signal(false);

  protected readonly donationForm = new FormGroup({
    fullName: new FormControl('', { nonNullable: true, validators: Validators.required }),
    email: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.email],
    }),
    cpf: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, cpfValidator()],
    }),
    amount: new FormControl<number | null>(null, [Validators.required, Validators.min(1)]),
    eventId: new FormControl<number | undefined>(this.dialogConfig.data?.eventId, [
      Validators.required,
    ]),
    giftCardCode: new FormControl(this.dialogConfig.data?.giftCardCode, {
      nonNullable: true,
      validators: Validators.required,
    }),
  });

  protected close(success?: boolean): void {
    this.dialogRef.close(success);
  }

  protected donate(): void {
    if (this.donationForm.invalid) {
      this.donationForm.markAllAsTouched();
      return;
    }

    if (this.testMode) {
      this.simulateDonation();
      return;
    }
  }

  protected simulateDonation(): void {
    this.isSubmitting.set(true);

    const formValue = this.donationForm.value;

    this.donationService
      .simulateDonation({
        fullName: formValue.fullName || '',
        cpf: formValue.cpf || '',
        email: formValue.email || '',
        amount: formValue.amount || 0,
        eventId: formValue.eventId || 0,
        giftCardCode: formValue.giftCardCode || ''
      })
      .pipe(
        finalize(() => {
          this.isSubmitting.set(false);
        }),
      )
      .subscribe({
        next: () => {
          this.donationForm.reset();
          this.close(true);
        },
        error: () => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Ocorreu um erro ao realizar a doação. Por favor, tente novamente.',
          });
        },
      });
  }
}
