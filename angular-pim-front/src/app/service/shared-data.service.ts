// shared-data.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  private currentError: string = ''

  constructor() { }

  setError(error: any) {
    this.currentError = error;
  }
  getError(){
    return this.currentError
  }
}