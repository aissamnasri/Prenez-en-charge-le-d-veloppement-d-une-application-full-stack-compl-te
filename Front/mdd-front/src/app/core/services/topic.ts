import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';

import { Topic } from '../models/topic.model';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getTopics(): Observable<Topic[]> {

    return this.http.get<Topic[]>(
      `${this.apiUrl}/topics`
    );
  }

  subscribe(topicId: number): Observable<void> {

    return this.http.post<void>(
      `${this.apiUrl}/topics/${topicId}/subscribe`,
      {}
    );
  }

  unsubscribe(topicId: number): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/topics/${topicId}/subscribe`
    );
  }
}