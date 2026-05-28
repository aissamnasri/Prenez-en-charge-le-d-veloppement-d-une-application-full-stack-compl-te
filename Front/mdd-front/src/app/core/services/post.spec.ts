import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { PostService } from './post.service';
import { environment } from '../../../environments/environment';

describe('PostService', () => {
  let service: PostService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });

    service = TestBed.inject(PostService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch feed posts', () => {
    const mockPosts = [{ id: 1, title: 'Post 1' }];

    service.getFeed().subscribe(posts => {
      expect(posts).toEqual(mockPosts as any);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/posts/feed`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);
  });

  it('should create a post', () => {
    const payload = { title: 'New', content: 'Hello', topicId: 1 };

    service.createPost(payload).subscribe(response => {
      expect(response).toEqual({});
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/posts`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(payload);
    req.flush({});
  });

  it('should get a post by id', () => {
    const mockPost = { id: 1, title: 'Post 1' };

    service.getPostById(1).subscribe(post => {
      expect(post).toEqual(mockPost as any);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/posts/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPost);
  });

  it('should add a comment', () => {
    service.addComment(1, 'Hello').subscribe(response => {
      expect(response).toEqual({});
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/posts/1/comments`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ content: 'Hello' });
    req.flush({});
  });
});
