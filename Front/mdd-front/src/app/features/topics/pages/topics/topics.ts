import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';

import { MatButtonModule } from '@angular/material/button';

import { TopicService } from '../../../../core/services/topic';

import { Topic } from '../../../../core/models/topic.model';
import { NavbarComponent } from '../../../../layout/navbar/navbar';
@Component({
  selector: 'app-topics',

  standalone: true,

  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    NavbarComponent

  ],

  templateUrl: './topics.html',

  styleUrl: './topics.scss'
})
export class TopicsComponent implements OnInit {

  topics: Topic[] = [];

  constructor(
    private topicService: TopicService
  ) {}

  ngOnInit(): void {

    this.loadTopics();
  }

  loadTopics(): void {

  this.topicService.getTopics()
    .subscribe({

      next: topics => {

        console.log(topics);

        this.topics = topics;
      },

      error: error => {

        console.error(error);
      }
    });
}

  toggleSubscription(topic: Topic): void {

    if (topic.subscribed) {

      this.topicService.unsubscribe(topic.id)
        .subscribe(() => {

          topic.subscribed = false;
        });

    } else {

      this.topicService.subscribe(topic.id)
        .subscribe(() => {

          topic.subscribed = true;
        });
    }
  }
}