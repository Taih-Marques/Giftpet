import { Component } from '@angular/core';
import { Router } from '@angular/router';

type Action = 'home' | 'about' | 'campaigns' | 'gift' | 'claim' | 'account' | 'donate';
type ActionColor = 'aquamarine' | 'peachy-maroney';

interface NavbarAction {
  type: Action;
  label: string;
  color?: ActionColor;
}

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  constructor(private readonly router: Router) {}

  protected readonly actions: NavbarAction[] = [
    { type: 'home', label: 'Início' },
    { type: 'about', label: 'Sobre nós' },
    { type: 'campaigns', label: 'Campanhas ativas' },
    { type: 'gift', label: 'Presentear' },
    { type: 'claim', label: 'Resgatar gift card' },
    { type: 'account', label: 'Minha conta', color: 'aquamarine' },
    { type: 'donate', label: 'Doe agora', color: 'peachy-maroney' },
  ];

  protected onClick(action: Action): void {
    this.router.navigate([`/${action}`]);
  }
}
