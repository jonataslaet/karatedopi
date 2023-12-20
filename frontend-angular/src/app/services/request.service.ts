import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class RequestService {
    private baseUrl = 'http://localhost:8080';

    constructor(private httpClient: HttpClient) {}

    request(
        method: string,
        endpoint: string,
        body?: any,
        options?: any
    ): Observable<any> {
        endpoint = endpoint.replace(/\/+(\w)/g, '/$1');
        const url = `${this.baseUrl}${endpoint}`;
        if (this.getAuthToken() !== null) {
            if (!options) {
                options = {};
            }
            if (!options.headers) {
                options.headers = new HttpHeaders({
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + this.getAuthToken()
                });
            }
        }
        switch (method.toUpperCase()) {
        case 'GET':
            return this.httpClient.get(url, options);
        case 'POST':
            if (this.isEmptyOrNull(options)) {
                return this.httpClient.post(url, body);
            };
            return this.httpClient.post(url, body, options);
        case 'PUT':
            if (this.isEmptyOrNull(options)) {
                return this.httpClient.put(url, body);
            };
            return this.httpClient.put(url, body, options);
        case 'DELETE':
            return this.httpClient.delete(url, options);
        case 'PATCH':
            return this.httpClient.patch(url, options);
        default:
            throw new Error(`Método HTTP não suportado: ${method}`);
        }
    }

    getAuthToken(): string | null {
        return window.localStorage.getItem('auth_token');
    }

    setAuthToken(token: string | null): void {
        if (token !== null) {
            window.localStorage.setItem('auth_token', token);
        } else {
            window.localStorage.removeItem('auth_token');
        }
    }

    isEmptyOrNull(options: any) {
        return !options || options == null || Object.keys(options).length <= 0;
    }
}
