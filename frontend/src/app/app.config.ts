import { provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { JwtModule } from '@auth0/angular-jwt';
import { routes } from './app.routes';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import { definePreset } from '@primeuix/themes';
import { JWT_STORAGE_KEY } from './user/user.service';
import localePt from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';
import { tokenExpiredInterceptor } from './core/token-expired.interceptor';
import { DialogService } from 'primeng/dynamicdialog';

export function tokenGetter() {
  return localStorage.getItem(JWT_STORAGE_KEY);
}

registerLocaleData(localePt, 'pt');

const GiftpetPreset = definePreset(Aura, {
  semantic: {
    primary: {
      50: '#f8f5fa',
      100: '#eee7f2',
      200: '#ded1e6',
      300: '#c7afd3',
      400: '#a985ba',
      500: '#664973', // primary color
      600: '#5b4167',
      700: '#4d3757',
      800: '#412f49',
      900: '#392a40',
      950: '#241729',
    },
  },
});

export const appConfig: ApplicationConfig = {
  providers: [
    importProvidersFrom(
      JwtModule.forRoot({
        config: {
          tokenGetter: tokenGetter,
          allowedDomains: ['localhost:4200'],
          skipWhenExpired: true,
        },
      }),
    ),
    provideHttpClient(withInterceptors([tokenExpiredInterceptor]), withInterceptorsFromDi()),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    DialogService,
    providePrimeNG({
      theme: {
        preset: GiftpetPreset,
      },
    }),
  ],
};
