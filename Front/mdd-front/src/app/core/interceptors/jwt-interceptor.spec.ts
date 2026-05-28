import { HttpRequest, HttpResponse } from '@angular/common/http';
import { lastValueFrom, of } from 'rxjs';

import { jwtInterceptor } from './jwt-interceptor';

describe('jwtInterceptor', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  it('should add Authorization header when token exists', async () => {
    localStorage.setItem('token', 'fake-jwt');

    const req = new HttpRequest('GET', '/test');
    const next = vi.fn().mockImplementation((request: HttpRequest<any>) => {
      expect(request.headers.get('Authorization')).toBe('Bearer fake-jwt');
      return of(new HttpResponse({ status: 200 }));
    });

    const response = await lastValueFrom(jwtInterceptor(req, next as any));
    expect(response instanceof HttpResponse).toBe(true);
  });

  it('should leave Authorization header undefined when no token', async () => {
    const req = new HttpRequest('GET', '/test');
    const next = vi.fn().mockImplementation((request: HttpRequest<any>) => {
      expect(request.headers.get('Authorization')).toBeNull();
      return of(new HttpResponse({ status: 200 }));
    });

    const response = await lastValueFrom(jwtInterceptor(req, next as any));
    expect(response instanceof HttpResponse).toBe(true);
  });
});
