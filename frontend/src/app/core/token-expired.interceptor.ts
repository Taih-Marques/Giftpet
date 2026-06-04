import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { LoginService } from '../login/login.service';

export const tokenExpiredInterceptor: HttpInterceptorFn = (request, next) => {
  const loginService = inject(LoginService);

  return next(request).pipe(
    catchError((error: unknown) => {
      if (error instanceof HttpErrorResponse && error.status === 403) {
        loginService.open();
      }

      return throwError(() => error);
    }),
  );
};
