import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map, tap } from "rxjs/operators";

@Injectable ({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'api/auth';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<void> {
    return this.http
      .post<{ token: string }>(`${this.baseUrl}/login`, { email, password })
      .pipe(tap(res => localStorage.setItem('token', res.token)),
      map(() => void 0)
    );
  }

  register(username: string, email: string, password: string): Observable<void> {
    return this.http
      .post<{ token: string }>(`${this.baseUrl}/register`, { username, email, password })
      .pipe(tap(res => localStorage.setItem('token', res.token)),
      map(() => void 0)
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}