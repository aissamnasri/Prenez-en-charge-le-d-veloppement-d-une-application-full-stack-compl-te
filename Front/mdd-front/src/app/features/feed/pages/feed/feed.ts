import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';
import {
  Router,
  RouterModule
} from '@angular/router';
import { NavbarComponent } from '../../../../layout/navbar/navbar';

import { MatButtonModule } from '@angular/material/button';

import { MatCardModule } from '@angular/material/card';

import { PostService } from '../../../../core/services/post.service';

import { Post } from '../../../../core/models/post.model';

@Component({
  selector: 'app-feed',

  standalone: true,

  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule,
    MatCardModule,
    NavbarComponent

  ],

  templateUrl: './feed.html',

  styleUrl: './feed.scss'
})
export class FeedComponent implements OnInit {

  posts: Post[] = [];

  constructor(
    private postService: PostService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.loadPosts();
  }

  loadPosts(): void {

    this.postService
      .getFeed()
      .subscribe({

        next: (posts) => {

          console.log(posts);

          this.posts = posts;
        },

        error: (error) => {

          console.error(error);
        }
      });
  }

  openPost(postId: number): void {

    this.router.navigate([
      '/posts',
      postId
    ]);
  }
}