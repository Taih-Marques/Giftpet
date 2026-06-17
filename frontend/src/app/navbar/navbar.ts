import { Component, HostListener, inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';

type Action = 'home' | 'about' | 'campaigns' | 'gift' | 'claim' | 'account' | 'donate';
type ActionColor = 'aquamarine' | 'peachy-maroney';

interface NavbarAction {
  type: Action;
  label: string;
  color?: ActionColor;
  bgColor?: ActionColor;
}

@Component({
  selector: 'app-navbar',
  host: { class: 'sticky top-0 z-50 block' },
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  private readonly router = inject(Router);
  private readonly loginService = inject(LoginService);

  protected isScrolled = false;

  protected readonly actions: NavbarAction[] = [
    { type: 'home', label: 'Início' },
    { type: 'about', label: 'Sobre nós' },
    { type: 'campaigns', label: 'Campanhas ativas' },
    { type: 'gift', label: 'Presentear' },
    { type: 'claim', label: 'Resgatar gift card' },
    { type: 'account', label: 'Minha conta', color: 'aquamarine' },
    { type: 'donate', label: 'Doe agora', bgColor: 'peachy-maroney' },
  ];

  protected onClick(action: Action): void {
    if (action === 'account') {
      this.loginService.open();
      return;
    }
    this.router.navigate([`/${action}`]);
  }

  @HostListener('window:scroll')
  protected onWindowScroll(): void {
    this.isScrolled = window.scrollY > 0;
  }
}
