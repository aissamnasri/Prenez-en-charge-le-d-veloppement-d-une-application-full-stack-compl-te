import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CreatePostComponent } from './create-post';
import { PostService } from '../../../../core/services/post.service';
import { TopicService } from '../../../../core/services/topic';

describe('CreatePostComponent', () => {
  let component: CreatePostComponent;
  let fixture: ComponentFixture<CreatePostComponent>;
  let postServiceSpy: any;
  let topicServiceSpy: any;

  beforeEach(async () => {
    postServiceSpy = { createPost: vi.fn() } as any;
    topicServiceSpy = { getTopics: vi.fn() } as any;

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([]), CreatePostComponent],
      providers: [
        { provide: PostService, useValue: postServiceSpy },
        { provide: TopicService, useValue: topicServiceSpy },
        { provide: ActivatedRoute, useValue: { snapshot: { url: [] } } }
      ]
    }).compileComponents();

    topicServiceSpy.getTopics.mockReturnValue(of([]));
    fixture = TestBed.createComponent(CreatePostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
