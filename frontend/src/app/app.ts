import { Component, DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute, RouterOutlet } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Login } from './login/login';
import { Navbar } from './navbar/navbar';
import { Footer } from "./footer/footer";

@Component({
  selector: 'app-root',
  host: { class: 'flex min-h-screen flex-col' },
  imports: [RouterOutlet, Navbar, Footer],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  providers: [DialogService],
})
export class App {
  private readonly route = inject(ActivatedRoute);
  private readonly dialogService = inject(DialogService);
  private readonly destroyRef = inject(DestroyRef);

  private loginDialogRef: DynamicDialogRef<Login> | null = null;

  constructor() {
    this.route.queryParamMap.pipe(takeUntilDestroyed(this.destroyRef)).subscribe((params) => {
      if (!params.has('login') || this.loginDialogRef) {
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
    });
  }
}
