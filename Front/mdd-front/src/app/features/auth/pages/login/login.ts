import { Component } from '@angular/core';

import { Router } from '@angular/router';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { CommonModule }
from '@angular/common';

import { MatButtonModule }
from '@angular/material/button';

import { MatFormFieldModule }
from '@angular/material/form-field';

import { MatIconModule }
from '@angular/material/icon';

import { MatInputModule }
from '@angular/material/input';

import { AuthService }
from '../../../../core/services/auth.service';

@Component({
  selector: 'app-login',

  standalone: true,

  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule
  ],

  templateUrl: './login.html',

  styleUrl: './login.scss'
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,

    private router: Router,

    private authService: AuthService
  ) {

    this.loginForm = this.fb.group({

      emailOrUsername: [
        '',
        [Validators.required]
      ],

      password: [
        '',
        [Validators.required]
      ]
    });
  }

  goBack(): void {

    this.router.navigate(['/']);
  }

  onSubmit(): void {

    if (this.loginForm.invalid) {

      return;
    }

    this.authService
      .login(this.loginForm.value)
      .subscribe({

        next: response => {

          localStorage.setItem(
            'token',
            response.token
          );

          this.router.navigate(['/feed']);
        },

        error: error => {

          console.error(error);
        }
      });
  }
}