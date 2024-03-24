import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Employee } from '../employee';
import { CommonService } from './common.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient, private commonService: CommonService) { }
  apiUrl = 'http://localhost:8080/employees'

  getEmployees(): Observable<Employee[]>{
    return this.http.get<Employee[]>(this.apiUrl)
      .pipe(
        catchError(this.commonService.handleError)
      )
  }
}
