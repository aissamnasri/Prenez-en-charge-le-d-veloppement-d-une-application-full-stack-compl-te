import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable, tap } from 'rxjs';

import {
    AuthResponse,
    LoginRequest,
    RegisterRequest
} from '../models/auth.models';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.apiUrl;

  private readonly sessionTimeoutMs = 30 * 60 * 1000; // 30 minutes
  private inactivityTimer?: number;

  constructor(private http: HttpClient) {
    this.initializeSession();
  }

  register(
    request: RegisterRequest
  ): Observable<AuthResponse> {

    return this.http.post<AuthResponse>(
      `${this.apiUrl}/auth/register`,
      request
    );
  }

  login(
    request: LoginRequest
  ): Observable<AuthResponse> {

    return this.http.post<AuthResponse>(
      `${this.apiUrl}/auth/login`,
      request
    ).pipe(

      tap(response => {
        localStorage.setItem('token', response.token);
        this.updateLastActivity();
        this.startInactivityTimer();
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('lastActivity');
    this.clearInactivityTimer();
  }

  isLoggedIn(): boolean {
    if (typeof window === 'undefined') {
      return false;
    }

    const token = localStorage.getItem('token');

    if (!token) {
      return false;
    }

    if (this.isSessionExpired()) {
      this.logout();
      return false;
    }

    return true;
  }

  getToken(): string | null {
    if (typeof window === 'undefined') {
      return null;
    }

    if (this.isSessionExpired()) {
      this.logout();
      return null;
    }

    return localStorage.getItem('token');
  }

  updateLastActivity(): void {
    if (typeof window === 'undefined') {
      return;
    }

    if (!localStorage.getItem('token')) {
      return;
    }

    localStorage.setItem('lastActivity', Date.now().toString());
    this.startInactivityTimer();
  }

  private initializeSession(): void {
    if (typeof window === 'undefined') {
      return;
    }

    this.updateLastActivity();

    const events = [
      'mousemove',
      'mousedown',
      'keydown',
      'touchstart',
      'scroll'
    ];

    events.forEach(event => {
      window.addEventListener(event, () => this.updateLastActivity());
    });

    window.addEventListener('storage', event => {
      if (event.key === 'token' || event.key === 'lastActivity') {
        if (!this.isLoggedIn()) {
          this.clearInactivityTimer();
        } else {
          this.startInactivityTimer();
        }
      }
    });
  }

  private isSessionExpired(): boolean {
    if (typeof window === 'undefined') {
      return false;
    }

    const lastActivity = this.getLastActivity();

    if (!lastActivity) {
      return false;
    }

    return Date.now() - lastActivity > this.sessionTimeoutMs;
  }

  private getLastActivity(): number | null {
    const value = localStorage.getItem('lastActivity');
    return value ? Number(value) : null;
  }

  private startInactivityTimer(): void {
    this.clearInactivityTimer();

    const lastActivity = this.getLastActivity();
    if (!lastActivity) {
      return;
    }

    const remaining = this.sessionTimeoutMs - (Date.now() - lastActivity);
    if (remaining <= 0) {
      this.logout();
      return;
    }

    this.inactivityTimer = window.setTimeout(() => {
      this.logout();
      window.location.reload();
    }, remaining);
  }

  private clearInactivityTimer(): void {
    if (this.inactivityTimer) {
      clearTimeout(this.inactivityTimer);
      this.inactivityTimer = undefined;
    }
  }
}