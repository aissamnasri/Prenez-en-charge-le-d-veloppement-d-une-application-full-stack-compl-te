import {
  ComponentFixture,
  TestBed
} from '@angular/core/testing';

import { LoginComponent }
from './login';

import { AuthService }
from '../../../../core/services/auth.service';

import { Router }
from '@angular/router';

import { of } from 'rxjs';

describe('LoginComponent', () => {

  let component: LoginComponent;

  let fixture: ComponentFixture<LoginComponent>;

  let authServiceSpy: any;

  let routerSpy: any;

  beforeEach(async () => {

    authServiceSpy = { login: vi.fn() } as any;

    routerSpy = { navigate: vi.fn() } as any;

    await TestBed.configureTestingModule({

      imports: [LoginComponent],

      providers: [

        {
          provide: AuthService,
          useValue: authServiceSpy
        },

        {
          provide: Router,
          useValue: routerSpy
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(
      LoginComponent
    );

    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {

    expect(component).toBeTruthy();
  });

  it('should call login', () => {

    authServiceSpy.login.mockReturnValue(

      of({
        token: 'fake-jwt'
      })
    );

    component.loginForm.setValue({

      emailOrUsername: 'john@test.com',
      password: 'Password123!'
    });

    component.onSubmit();

    expect(authServiceSpy.login)
      .toHaveBeenCalled();
  });
});