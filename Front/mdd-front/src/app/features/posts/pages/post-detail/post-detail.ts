import { ChangeDetectorRef, Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { ActivatedRoute } from '@angular/router';

import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';

import { MatInputModule } from '@angular/material/input';

import { MatButtonModule } from '@angular/material/button';

import { NavbarComponent } from '../../../../layout/navbar/navbar';

import { PostService } from '../../../../core/services/post.service';

import { PostDetail } from '../../../../core/models/post-detail.model';

@Component({
  selector: 'app-post-detail',

  standalone: true,

  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    NavbarComponent,
  ],

  templateUrl: './post-detail.html',

  styleUrl: './post-detail.scss',
})
export class PostDetailComponent implements OnInit {
  post!: PostDetail;

  form: FormGroup;

  loading = false;

  constructor(
    private route: ActivatedRoute,

    private fb: FormBuilder,

    private postService: PostService,

    private cdr: ChangeDetectorRef,
  ) {
    this.form = this.fb.group({
      content: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const postId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadPost(postId);
  }

  loadPost(postId: number): void {
    this.loading = true;

    this.postService.getPostById(postId).subscribe({
      next: (post) => {
        this.post = post;

        this.loading = false;

        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error(error);

        this.loading = false;

        this.cdr.detectChanges();
      },
    });
  }

  submitComment(): void {
    if (this.form.invalid) {
      return;
    }

    this.postService.addComment(this.post.id, this.form.value.content).subscribe({
      next: () => {
        this.form.reset();

        this.loadPost(this.post.id);
      },

      error: (error) => {
        console.error(error);
      },
    });
  }
}
