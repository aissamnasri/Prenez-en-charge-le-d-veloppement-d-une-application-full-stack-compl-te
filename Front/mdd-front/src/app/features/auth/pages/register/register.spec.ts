import {
  ComponentFixture,
  TestBed
} from '@angular/core/testing';

import { RegisterComponent }
from './register';

import { AuthService }
from '../../../../core/services/auth.service';

import { Router }
from '@angular/router';

import { of } from 'rxjs';

describe('RegisterComponent', () => {

  let component: RegisterComponent;

  let fixture: ComponentFixture<RegisterComponent>;

  let authServiceSpy: any;

  let routerSpy: any;

  beforeEach(async () => {

    authServiceSpy = { register: vi.fn() } as any;

    routerSpy = { navigate: vi.fn() } as any;

    await TestBed.configureTestingModule({

      imports: [RegisterComponent],

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
      RegisterComponent
    );

    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {

    expect(component).toBeTruthy();
  });

  it('should call register', () => {

    authServiceSpy.register.mockReturnValue(

      of({
        token: 'fake-jwt'
      })
    );

    component.registerForm.setValue({

      username: 'john',
      email: 'john@test.com',
      password: 'Password123!'
    });

    component.onSubmit();

    expect(authServiceSpy.register)
      .toHaveBeenCalled();
  });

  it('should not submit invalid form', () => {

    component.registerForm.setValue({

      username: '',
      email: '',
      password: ''
    });

    component.onSubmit();

    expect(authServiceSpy.register)
      .not.toHaveBeenCalled();
  });
});