import { Routes } from '@angular/router';

import { HomeComponent } from './features/auth/pages/home/home';

import { LoginComponent } from './features/auth/pages/login/login';

import { RegisterComponent } from './features/auth/pages/register/register';

import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [

  {
    path: '',
    component: HomeComponent
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'register',
    component: RegisterComponent
  },

  {
    path: 'feed',

    loadComponent: () =>
      import('./features/feed/pages/feed/feed')
        .then(m => m.FeedComponent),

    canActivate: [authGuard]
  },
  {
  path: 'topics',

  loadComponent: () =>
    import('./features/topics/pages/topics/topics')
      .then(m => m.TopicsComponent),

  canActivate: [authGuard]
},
{
  path: 'posts/create',

  loadComponent: () =>
    import('./features/posts/pages/create-post/create-post')
      .then(m => m.CreatePostComponent),

  canActivate: [authGuard]
},
{
  path: 'posts/:id',

  loadComponent: () =>
    import('./features/posts/pages/post-detail/post-detail')
      .then(m => m.PostDetailComponent),

  canActivate: [authGuard]
},
{
  path: 'profile',

  loadComponent: () =>
    import('./features/profile/pages/profile/profile')
      .then(m => m.ProfileComponent),

  canActivate: [authGuard]
}
];