import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PostDetailComponent } from './post-detail';
import { PostService } from '../../../../core/services/post.service';

describe('PostDetailComponent', () => {
  let component: PostDetailComponent;
  let fixture: ComponentFixture<PostDetailComponent>;
  let postServiceSpy: any;

  beforeEach(async () => {
    postServiceSpy = { getPostById: vi.fn(), addComment: vi.fn() } as any;
    postServiceSpy.getPostById.mockReturnValue(of({ id: 1, content: '', title: '' } as any));

    await TestBed.configureTestingModule({
      imports: [PostDetailComponent],
      providers: [
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => '1' } } } },
        { provide: PostService, useValue: postServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PostDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
