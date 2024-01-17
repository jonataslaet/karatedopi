import { HttpErrorResponse } from "@angular/common/http";
import { ErrorHandler, Injectable, inject } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { StandardError } from "../common/standard-error";

@Injectable()
export class ErrorsHandler implements ErrorHandler {
    router = inject(Router);
    snackBar = inject(MatSnackBar);

    handleError(error: Error | HttpErrorResponse) {
        if (error instanceof HttpErrorResponse) {
            if (!navigator.onLine) {
                this.snackBar.open(
                    'Sem conexão com a internet.',
                    '❌'
                )._dismissAfter(3000);
            } else {
                let standardError: StandardError = {
                    timestamp: null,
                    status: null,
                    error: '',
                    message: '',
                    path: ''
                };
                standardError.status = error.error.status;
                standardError.error = error.error.error;
                this.snackBar.open(standardError.error, '❌')._dismissAfter(3000);
            }
        } else {
            this.snackBar.open(
                'Erro desconhecido.',
                '❌'
            )._dismissAfter(3000);
        }
    }
}