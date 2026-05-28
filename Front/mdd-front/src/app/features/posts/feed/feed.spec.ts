import { Component, Directive, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { FeedComponent } from './feed';

@Directive({ selector: '[routerLink]' })
class RouterLinkStubDirective {
  @Input() routerLink: unknown;
}

describe('FeedComponent', () => {
  let component: FeedComponent;
  let fixture: ComponentFixture<FeedComponent>;

  beforeEach(async () => {
    TestBed.overrideComponent(FeedComponent, {
      set: {
        imports: [CommonModule, MatButtonModule, MatCardModule, RouterLinkStubDirective]
      }
    });

    await TestBed.configureTestingModule({
      imports: [FeedComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(FeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
