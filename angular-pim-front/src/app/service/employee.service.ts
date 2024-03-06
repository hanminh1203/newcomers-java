import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }
  
  getVisas(): Observable<string[]> {
    return this.http.get<string[]>('http://localhost:8080/users');
  }

  checkIfNotExists(arr1: string[], arr2: string[]) {
    return arr1.filter(item => !arr2.includes(item));
}
}
