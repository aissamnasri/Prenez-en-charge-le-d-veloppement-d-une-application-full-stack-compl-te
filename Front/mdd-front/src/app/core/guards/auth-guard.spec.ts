import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { authGuard } from './auth-guard';
import { AuthService } from '../services/auth.service';

describe('authGuard', () => {
  let router: Router;
  let authServiceSpy: any;

  beforeEach(() => {
    authServiceSpy = { isLoggedIn: vi.fn() };

    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([])],
      providers: [{ provide: AuthService, useValue: authServiceSpy }]
    });

    router = TestBed.inject(Router);
  });

  it('should allow activation when user is logged in', () => {
    authServiceSpy.isLoggedIn.mockReturnValue(true);

    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    expect(result).toBe(true);
  });

  it('should redirect to login when user is not logged in', () => {
    authServiceSpy.isLoggedIn.mockReturnValue(false);
    vi.spyOn(router, 'navigate').mockResolvedValue(true as any);

    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    expect(result).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
