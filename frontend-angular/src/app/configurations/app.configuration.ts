import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from '../common/app.routes';
import { authenticationInterceptor } from '../interceptors/authentication.interceptor';

export const appConfiguration: ApplicationConfig = {
    providers: [
        provideRouter(routes),
        provideHttpClient(withInterceptors([authenticationInterceptor])),
    ],
};
