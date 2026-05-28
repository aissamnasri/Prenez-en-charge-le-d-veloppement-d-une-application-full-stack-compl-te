import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TopicsComponent } from './topics';
import { TopicService } from '../../../../core/services/topic';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Topic } from '../../../../core/models/topic.model';

describe('TopicsComponent', () => {
  let component: TopicsComponent;
  let fixture: ComponentFixture<TopicsComponent>;
  let topicServiceSpy: any;

  const mockTopics: Topic[] = [
    { id: 1, name: 'Angular', description: 'Framework', subscribed: false }
  ];

  beforeEach(async () => {
    topicServiceSpy = { getTopics: vi.fn(), subscribe: vi.fn(), unsubscribe: vi.fn() } as any;

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TopicsComponent],
      providers: [{ provide: TopicService, useValue: topicServiceSpy }]
    }).compileComponents();

    topicServiceSpy.getTopics.mockReturnValue(of(mockTopics));

    fixture = TestBed.createComponent(TopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create and load topics', () => {
    expect(component).toBeTruthy();
    expect(component.topics).toEqual(mockTopics);
  });

  it('should subscribe to a topic when toggling subscription off', () => {
    const topic = { ...mockTopics[0], subscribed: false };
    component.topics = [topic];
    topicServiceSpy.subscribe.mockReturnValue(of(void 0));

    component.toggleSubscription(topic);

    expect(topic.subscribed).toBe(true);
    expect(topicServiceSpy.subscribe).toHaveBeenCalledWith(topic.id);
  });

  it('should unsubscribe from a topic when toggling subscription on', () => {
    const topic = { ...mockTopics[0], subscribed: true };
    component.topics = [topic];
    topicServiceSpy.unsubscribe.mockReturnValue(of(void 0));

    component.toggleSubscription(topic);

    expect(topic.subscribed).toBe(false);
    expect(topicServiceSpy.unsubscribe).toHaveBeenCalledWith(topic.id);
  });
});
