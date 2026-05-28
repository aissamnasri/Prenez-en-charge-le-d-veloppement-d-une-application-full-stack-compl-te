import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { UserService } from './user';
import { environment } from '../../../environments/environment';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch current user', () => {
    const user = { id: 1, username: 'john' };

    service.getCurrentUser().subscribe(response => {
      expect(response).toEqual(user as any);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/users/me`);
    expect(req.request.method).toBe('GET');
    req.flush(user);
  });

  it('should update current user', () => {
    const request = { username: 'john2' };
    const updatedUser = { id: 1, username: 'john2' };

    service.updateUser(request as any).subscribe(response => {
      expect(response).toEqual(updatedUser as any);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/users/me`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(request);
    req.flush(updatedUser);
  });
});
