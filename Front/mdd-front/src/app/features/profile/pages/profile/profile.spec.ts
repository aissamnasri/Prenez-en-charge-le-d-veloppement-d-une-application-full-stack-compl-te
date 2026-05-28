import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProfileComponent } from './profile';
import { UserService } from '../../../../core/services/user';
import { TopicService } from '../../../../core/services/topic';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let userServiceSpy: any;
  let topicServiceSpy: any;

  beforeEach(async () => {
    userServiceSpy = { getCurrentUser: vi.fn(), updateUser: vi.fn() } as any;
    topicServiceSpy = { unsubscribe: vi.fn() } as any;
    userServiceSpy.getCurrentUser.mockReturnValue(of({ id: 1, username: 'john', email: 'john@test.com', subscriptions: [] } as any));

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([]), ProfileComponent],
      providers: [
        { provide: UserService, useValue: userServiceSpy },
        { provide: TopicService, useValue: topicServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
