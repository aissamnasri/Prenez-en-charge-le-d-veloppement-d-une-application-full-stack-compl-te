import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';

import { MatButtonModule } from '@angular/material/button';

import { RouterModule } from '@angular/router';

import { PostService } from '../../../../core/services/post.service';

import { Post } from '../../../../core/models/post.model';
import { NavbarComponent } from '../../../../layout/navbar/navbar';

@Component({
  selector: 'app-feed',

  standalone: true,

  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    RouterModule,
    NavbarComponent
  ],

  templateUrl: './feed.html',

  styleUrl: './feed.scss'
})
export class FeedComponent implements OnInit {

  posts: Post[] = [];

  loading = false;

  constructor(
    private postService: PostService
  ) {}

  ngOnInit(): void {

    this.loadFeed();
  }

  loadFeed(): void {

    this.loading = true;

    this.postService.getFeed()
      .subscribe({

        next: posts => {

          this.posts = posts;

          this.loading = false;
        },

        error: error => {

          console.error(error);

          this.loading = false;
        }
      });
  }
}