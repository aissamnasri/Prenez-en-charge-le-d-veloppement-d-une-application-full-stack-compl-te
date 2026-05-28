import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

//import { provideHttpClient } from '@angular/common/http';
import {
  provideHttpClient,
  withInterceptors
} from '@angular/common/http';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { routes } from './app.routes';

import { jwtInterceptor } from './core/interceptors/jwt-interceptor';
import {
  provideAnimations
} from '@angular/platform-browser/animations';
export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), provideClientHydration(withEventReplay()),
    provideHttpClient(
  withInterceptors([
    jwtInterceptor
  ])
),
    provideAnimations()

  ]
};
