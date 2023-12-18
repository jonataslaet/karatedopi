import { HttpInterceptorFn } from "@angular/common/http";

export const authenticationInterceptor: HttpInterceptorFn = (request, next) => {
    const token = localStorage.getItem('auth_token') ?? '';
    request = request.clone({
        setHeaders: {
            Authorization: token ? `Bearer ${token}`: '',
        },
    });
    return next(request);
}