import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';

import { MatInputModule } from '@angular/material/input';

import { MatFormFieldModule } from '@angular/material/form-field';

import { MatSelectModule } from '@angular/material/select';

import { NavbarComponent }
from '../../../../layout/navbar/navbar';

import { TopicService }
from '../../../../core/services/topic';

import { PostService }
from '../../../../core/services/post.service';

import { Topic }
from '../../../../core/models/topic.model';

@Component({
  selector: 'app-create-post',

  standalone: true,

  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    NavbarComponent
  ],

  templateUrl: './create-post.html',

  styleUrl: './create-post.scss'
})
export class CreatePostComponent
implements OnInit {

  form: FormGroup;

  topics: Topic[] = [];

  constructor(
    private fb: FormBuilder,

    private topicService: TopicService,

    private postService: PostService,

    private router: Router
  ) {

    this.form = this.fb.group({

      topicId: [
        '',
        Validators.required
      ],

      title: [
        '',
        Validators.required
      ],

      content: [
        '',
        Validators.required
      ]
    });
  }

  ngOnInit(): void {

    this.loadTopics();
  }

  loadTopics(): void {

    this.topicService.getTopics()
      .subscribe({

        next: topics => {

          this.topics = topics;
        },

        error: error => {

          console.error(error);
        }
      });
  }

  submit(): void {

    if (this.form.invalid) {

      return;
    }

    this.postService
      .createPost(this.form.value)
      .subscribe({

        next: () => {

          this.router.navigate(['/feed']);
        },

        error: error => {

          console.error(error);
        }
      });
  }
}