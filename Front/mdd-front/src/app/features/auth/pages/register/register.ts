import { Component } from '@angular/core';
import { Router } from '@angular/router';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../../../core/services/auth.service';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {

    this.registerForm = this.fb.group({

      username: [
        '',
        [Validators.required]
      ],

      email: [
        '',
        [Validators.required, Validators.email]
      ],

      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8)
        ]
      ]
    });
  }

  goBack(): void {
    this.router.navigate(['/']);
  }

 onSubmit(): void {

  if (this.registerForm.invalid) {
    return;
  }

  this.authService
    .register(this.registerForm.value)
    .subscribe({

      next: () => {

        this.router.navigate(['/login']);
      },

     error: error => {

  console.error(error.error);
}
    });
}
}