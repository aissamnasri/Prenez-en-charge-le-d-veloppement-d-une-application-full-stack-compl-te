import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CreatePostComponent } from './create-post';
import { PostService } from '../../../core/services/post.service';
import { TopicService } from '../../../core/services/topic';
import { Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('CreatePostComponent', () => {
  let component: CreatePostComponent;
  let fixture: ComponentFixture<CreatePostComponent>;
  let postServiceSpy: any;
  let topicServiceSpy: any;
  let routerSpy: any;

  beforeEach(async () => {
    postServiceSpy = { createPost: vi.fn() };
    topicServiceSpy = { getTopics: vi.fn() };
    routerSpy = { navigate: vi.fn() };

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CreatePostComponent],
      providers: [
        { provide: PostService, useValue: postServiceSpy },
        { provide: TopicService, useValue: topicServiceSpy },
        { provide: Router, useValue: routerSpy }
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

  it('should not submit invalid form', () => {
    component.submit();
    expect(postServiceSpy.createPost).not.toHaveBeenCalled();
  });

  it('should submit valid form and navigate to feed', () => {
    component.form.setValue({ topicId: 1, title: 'Title', content: 'Content' });
    postServiceSpy.createPost.mockReturnValue(of({}));

    component.submit();

    expect(postServiceSpy.createPost).toHaveBeenCalledWith({ topicId: 1, title: 'Title', content: 'Content' });
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/feed']);
  });
});
