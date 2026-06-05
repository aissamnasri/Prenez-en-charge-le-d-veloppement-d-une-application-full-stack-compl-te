import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';

import { MatCardModule } from '@angular/material/card';

import { PostService } from '../../../core/services/post.service';

@Component({
  selector: 'app-feed',

  standalone: true,

  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule
  ],

  templateUrl: './feed.html',

  styleUrl: './feed.scss'
})
export class FeedComponent implements OnInit {

  posts: any[] = [];

  constructor(
    private postService: PostService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.loadPosts();
  }

  loadPosts(): void {

    this.postService.getFeed()
      .subscribe({

        next: posts => {

          console.log(posts);

          this.posts = posts;
        },

        error: error => {

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
testClick(post: any): void {

  console.log('CLICK DETECTE');

  console.log(post);
}
}