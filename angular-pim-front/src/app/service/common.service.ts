import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { ErrorResponse } from '../ErrorResponse';
import { SharedDataService } from './shared-data.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private sharedDataService: SharedDataService, private router: Router) { }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      error.error.message = "client side error";
      error.error.detail = "Check network"
    } 
    return throwError(() => new ErrorResponse(error.status, error.error.message, error.error.detail));
  }

  public redirectOnError(error: ErrorResponse){
    this.sharedDataService.setError(error.detail);
    this.router.navigateByUrl('/404');
  }
  
  
}
