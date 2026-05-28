import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { TopicService } from './topic';
import { environment } from '../../../environments/environment';

describe('TopicService', () => {
  let service: TopicService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });

    service = TestBed.inject(TopicService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch topics', () => {
    const mockTopics = [
      { id: 1, name: 'Angular', description: 'Framework', subscribed: false }
    ];

    service.getTopics().subscribe(topics => {
      expect(topics).toEqual(mockTopics as any);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/topics`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTopics);
  });

  it('should subscribe to a topic', () => {
    service.subscribe(1).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/topics/1/subscribe`);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should unsubscribe from a topic', () => {
    service.unsubscribe(1).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/topics/1/subscribe`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
