import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface User {
  name: string;
  sub: string;
  iat: number;
  exp: number;
}

export const JWT_STORAGE_KEY = 'giftpet-jwt'

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly baseUrl = `${environment.apiHost}/user`;
  private readonly authenticatedUser = new BehaviorSubject<User | null>(null);
  private readonly jwtHelper = inject(JwtHelperService);
  private readonly http = inject(HttpClient);

  constructor() {
    console.log('initialize user service');
    const jwt = localStorage.getItem(JWT_STORAGE_KEY);
    if (!jwt) {
      return;
    }
    console.log('jwt found at startup');
    if (this.jwtHelper.isTokenExpired(jwt)) {
      console.log('token is expired. removing')
      localStorage.removeItem(JWT_STORAGE_KEY);
      return;
    }

    this.processJWT(jwt);
  }

  get user(): Observable<User | null> {
    return this.authenticatedUser.asObservable();
  }

  login(email: string, password: string) {
    return this.http
      .post<{ access_token: string }>(`${this.baseUrl}/login`, {
        email,
        password,
      })
      .pipe(
        tap({
          next: (response) => this.processJWT(response.access_token),
          error: (err) => console.log(err),
        }),
      );
  }

  private processJWT(jwt: string) {
    console.log('processing jwt');
    const user = this.jwtHelper.decodeToken(jwt);
    localStorage.setItem(JWT_STORAGE_KEY, jwt);
    this.authenticatedUser.next(user);
  }

  logout() {
    this.authenticatedUser.next(null);
    localStorage.removeItem(JWT_STORAGE_KEY);
  }
}
