import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';

import { Router, RouterLink, RouterLinkActive }
from '@angular/router';

import { MatButtonModule }
from '@angular/material/button';

import { MatIconModule }
from '@angular/material/icon';

import { AuthService }
from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',

  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    MatButtonModule,
    MatIconModule
  ],

  templateUrl: './navbar.html',

  styleUrl: './navbar.scss'
})
export class NavbarComponent {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout(): void {

    this.authService.logout();

    this.router.navigate(['/login']);
  }
}