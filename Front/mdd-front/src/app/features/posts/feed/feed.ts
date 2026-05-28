import { Component } from '@angular/core';

import { CommonModule }
from '@angular/common';

import { RouterModule }
from '@angular/router';

import { MatButtonModule }
from '@angular/material/button';

import { MatCardModule }
from '@angular/material/card';

@Component({
  selector: 'app-feed',

  standalone: true,

  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule,
    MatCardModule
  ],

  templateUrl: './feed.html',

  styleUrl: './feed.scss'
})
export class FeedComponent {

  posts: any[] = [];
}