import { ApplicationConfig, ErrorHandler } from '@angular/core';
import { ErrorsHandler } from '../services/errors-handler';

export const errorsHandlerConfiguration: ApplicationConfig = {
    providers: [
        {
            provide: ErrorHandler,
            useClass: ErrorsHandler,
        }
    ],
};
