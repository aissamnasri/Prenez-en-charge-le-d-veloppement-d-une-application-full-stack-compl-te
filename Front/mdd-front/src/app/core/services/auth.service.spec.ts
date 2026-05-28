import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { LoginRequest, RegisterRequest } from '../models/auth.models';
import { environment } from '../../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    localStorage.clear();

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should register user', () => {
    const request: RegisterRequest = {
      username: 'john',
      email: 'john@test.com',
      password: 'Password123!'
    };

    service.register(request).subscribe(response => {
      expect(response.token).toBe('fake-jwt');
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/auth/register`);
    expect(req.request.method).toBe('POST');
    req.flush({ token: 'fake-jwt' });
  });

  it('should login user and store token', () => {
    const request: LoginRequest = {
      emailOrUsername: 'john@test.com',
      password: 'Password123!'
    };

    service.login(request).subscribe(response => {
      expect(response.token).toBe('fake-jwt');
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush({ token: 'fake-jwt' });

    expect(localStorage.getItem('token')).toBe('fake-jwt');
    expect(service.isLoggedIn()).toBe(true);
  });

  it('should logout and clear local storage', () => {
    localStorage.setItem('token', 'fake-jwt');
    localStorage.setItem('lastActivity', Date.now().toString());

    service.logout();

    expect(localStorage.getItem('token')).toBeNull();
    expect(localStorage.getItem('lastActivity')).toBeNull();
    expect(service.isLoggedIn()).toBe(false);
  });

  it('should return token when the session is still valid', () => {
    localStorage.setItem('token', 'fake-jwt');
    localStorage.setItem('lastActivity', Date.now().toString());

    expect(service.getToken()).toBe('fake-jwt');
  });

  it('should clear expired session when getting token', () => {
    localStorage.setItem('token', 'fake-jwt');
    const expiredTime = Date.now() - 31 * 60 * 1000;
    localStorage.setItem('lastActivity', expiredTime.toString());

    expect(service.getToken()).toBeNull();
    expect(localStorage.getItem('token')).toBeNull();
  });

  it('should return null token when none stored', () => {
    expect(service.getToken()).toBeNull();
  });
});
