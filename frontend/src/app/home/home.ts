import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ButtonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  constructor() {
  }
}
