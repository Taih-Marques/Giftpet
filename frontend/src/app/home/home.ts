import { Component, inject, OnInit } from '@angular/core';
import { Login } from '../login/login';
import { User, UserService } from '../user/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [Login, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  private readonly userService = inject(UserService);

  protected user$: Observable<User | null>;

  constructor() {
    this.user$ = this.userService.user.pipe(takeUntilDestroyed());
  }
}
