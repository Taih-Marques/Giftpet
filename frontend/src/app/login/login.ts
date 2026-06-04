import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InputTextModule } from 'primeng/inputtext';
import { finalize } from 'rxjs';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-login',
  imports: [ButtonModule, IftaLabelModule, InputTextModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private readonly formBuilder = inject(FormBuilder);
  private readonly userService = inject(UserService);
  private readonly cd = inject(ChangeDetectorRef);
  private readonly dialogRef = inject(DynamicDialogRef);

  protected isSubmitting = false;

  protected readonly loginForm = this.formBuilder.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  protected submit(): void {
    if (this.loginForm.invalid || this.isSubmitting) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { email, password } = this.loginForm.getRawValue();

    this.isSubmitting = true;
    this.userService
      .login(email, password)
      .pipe(
        finalize(() => {
          this.isSubmitting = false;
          this.cd.detectChanges();
        }),
      )
      .subscribe({
        next: () => this.close(),
        error: () => {},
      });
  }

  close() {
    this.loginForm.reset();
    this.dialogRef.close();
  }
}
