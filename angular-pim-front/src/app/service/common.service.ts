import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { ErrorResponse } from '../ErrorResponse';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor() { }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      let errMessage = error.error;
      console.error(
        `Backend returned code ${error.status}, message: ${error.error.detail}`);
    }
    // Return an observable with a user-facing error message.  
    return throwError(() => error.error);
  }
}
