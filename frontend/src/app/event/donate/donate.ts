import { Component, inject } from '@angular/core';
import { cpfValidator } from 'cpf-cnpj-validator/angular';
import { InputMaskModule } from 'primeng/inputmask';

import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-donate',
  imports: [ButtonModule, IftaLabelModule, InputMaskModule, InputTextModule, InputNumberModule, ReactiveFormsModule],
  templateUrl: './donate.html',
  styleUrl: './donate.scss',
})
export class Donate {
  private readonly dialogRef = inject(DynamicDialogRef);
  private readonly dialogConfig = inject(DynamicDialogConfig);

  protected readonly eventName = this.dialogConfig.data?.eventName ?? '';

  protected readonly donationForm = new FormGroup({
    fullName: new FormControl('', { nonNullable: true }),
    email: new FormControl('', { nonNullable: true, validators: Validators.email }),
    cpf: new FormControl('', { nonNullable: true, validators: cpfValidator()}),
    amount: new FormControl<number | null>(null),
  });

  protected close(): void {
    this.dialogRef.close();
  }

  protected nextStep(): void {}
}
