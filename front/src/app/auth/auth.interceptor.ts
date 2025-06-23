import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.auth.getToken();

    if(token) {
      const cloned = req.clone({ setHeaders: { Authorization: `Bearer ${token}` }});

      return next.handle(cloned);
    }
    return next.handle(req);
  }
};
