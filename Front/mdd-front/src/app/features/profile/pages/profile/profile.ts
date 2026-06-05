import {
  ChangeDetectorRef,
  Component,
  OnInit
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { MatInputModule }
from '@angular/material/input';

import { MatButtonModule }
from '@angular/material/button';

import { MatCardModule }
from '@angular/material/card';

import { MatFormFieldModule }
from '@angular/material/form-field';

import { NavbarComponent }
from '../../../../layout/navbar/navbar';

import { UserService }
from '../../../../core/services/user';

import { TopicService }
from '../../../../core/services/topic';

import { User }
from '../../../../core/models/user.model';

import { Topic }
from '../../../../core/models/topic.model';

@Component({
  selector: 'app-profile',

  standalone: true,

  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    NavbarComponent
  ],

  templateUrl: './profile.html',

  styleUrl: './profile.scss'
})
export class ProfileComponent implements OnInit {

  form: FormGroup;

  user!: User;

  subscriptions: Topic[] = [];

  constructor(
    private fb: FormBuilder,

    private userService: UserService,

    private topicService: TopicService,

    private cdr: ChangeDetectorRef
  ) {

    this.form = this.fb.group({

      username: [
        '',
        Validators.required
      ],

      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],

      password: ['']
    });
  }

  ngOnInit(): void {

    this.loadUser();
  }

  loadUser(): void {

    this.userService.getCurrentUser()
      .subscribe({

        next: (user) => {

          this.user = user;

          this.subscriptions =
            user.subscriptions ?? [];

          this.form.patchValue({

            username: user.username,

            email: user.email
          });

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error(error);
        }
      });
  }

  save(): void {

    if (this.form.invalid) {

      return;
    }

    this.userService
      .updateUser(this.form.value)
      .subscribe({

        next: (user) => {

          this.user = user;

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error(error);
        }
      });
  }

  unsubscribe(topic: Topic): void {

    this.topicService
      .unsubscribe(topic.id)
      .subscribe({

        next: () => {

          this.subscriptions =
            this.subscriptions.filter(
              t => t.id !== topic.id
            );

          this.cdr.detectChanges();
        },

        error: error => {

          console.error(error);
        }
      });
  }
}