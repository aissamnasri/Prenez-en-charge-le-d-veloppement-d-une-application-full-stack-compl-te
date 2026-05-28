import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';

import { CreatePostRequest } from '../models/create-post.model';
import { Post } from '../models/post.model';
import { PostDetail } from '../models/post-detail.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getFeed(): Observable<Post[]> {

    return this.http.get<Post[]>(
      `${this.apiUrl}/posts/feed`
    );
  }
  createPost(request: CreatePostRequest) {

  return this.http.post(
    `${this.apiUrl}/posts`,
    request
  );
}
getPostById(postId: number) {

  return this.http.get<PostDetail>(
    `${this.apiUrl}/posts/${postId}`
  );
}
addComment(
  postId: number,
  content: string
) {

  return this.http.post(
    `${this.apiUrl}/posts/${postId}/comments`,
    {
      content
    }
  );
}
}