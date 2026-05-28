import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';

import { NavbarComponent } from './navbar';
import { AuthService } from '../../core/services/auth.service';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let authServiceSpy: any;
  let router: Router;

  beforeEach(async () => {
    authServiceSpy = { logout: vi.fn() } as any;

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([]), NavbarComponent],
      providers: [{ provide: AuthService, useValue: authServiceSpy }]
    }).compileComponents();

    router = TestBed.inject(Router);

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should logout and navigate to login', () => {
    vi.spyOn(router, 'navigate').mockResolvedValue(true as any);

    component.logout();

    expect(authServiceSpy.logout).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
