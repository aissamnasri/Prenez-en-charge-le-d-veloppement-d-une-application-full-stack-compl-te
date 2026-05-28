import { Component, Directive, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { of } from 'rxjs';

import { FeedComponent } from './feed';
import { PostService } from '../../../../core/services/post.service';

@Directive({ selector: '[routerLink]' })
class RouterLinkStubDirective {
  @Input() routerLink: unknown;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  template: ''
})
class StubNavbarComponent {}

describe('FeedComponent', () => {
  let component: FeedComponent;
  let fixture: ComponentFixture<FeedComponent>;
  let postServiceSpy: any;

  beforeEach(async () => {
    postServiceSpy = { getFeed: vi.fn().mockReturnValue(of([])) };

    TestBed.overrideComponent(FeedComponent, {
      set: {
        imports: [CommonModule, MatCardModule, MatButtonModule, StubNavbarComponent, RouterLinkStubDirective]
      }
    });

    await TestBed.configureTestingModule({
      imports: [FeedComponent],
      providers: [{ provide: PostService, useValue: postServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(FeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
