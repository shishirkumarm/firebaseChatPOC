import { Injectable } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';

@Injectable()
export class HeadersService {
    getHeaders(): RequestOptions {
        const headers = new Headers({
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'x-country': 'US',
            'x-user': 'ramesh1'
            // 'x-country': localStorage.getItem('x-country'),
            // 'x-user': localStorage.getItem('x-user')
        });
        const options = new RequestOptions({ headers: headers, withCredentials: true });
        return options;
    }
}
