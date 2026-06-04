import { DestroyRef, inject, Injectable } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Login } from './login';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private readonly dialogService = inject(DialogService);
  private readonly destroyRef = inject(DestroyRef);
  private loginDialogRef: DynamicDialogRef<Login> | null = null;

  open() {
    if (this.loginDialogRef) {
      return;
    }

    this.loginDialogRef = this.dialogService.open(Login, {
      modal: true,
      width: '25rem',
      showHeader: false,
      styleClass: 'overflow-hidden rounded-xl bg-zircon [&_.p-dialog-content]:bg-zircon',
    });

    this.loginDialogRef?.onClose.pipe(takeUntilDestroyed(this.destroyRef)).subscribe(() => {
      this.loginDialogRef = null;
    });
  }
}
