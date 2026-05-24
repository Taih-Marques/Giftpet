import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { JwtModule } from '@auth0/angular-jwt';
import { routes } from './app.routes';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import { JWT_STORAGE_KEY } from './user/user.service';
import localePt from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';

export function tokenGetter() {
  return localStorage.getItem(JWT_STORAGE_KEY);
}

registerLocaleData(localePt, 'pt');

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
    provideHttpClient(withInterceptorsFromDi()),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    providePrimeNG({
      theme: {
        preset: Aura,
      },
    }),
  ],
};
