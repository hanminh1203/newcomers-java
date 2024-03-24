// shared-data.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  private currentError: string = ''

  private searchString: string =''
  private status: string =''

  statusArr: string[] = ['New', 'Planned', 'In progess', 'Finished'];

  constructor() { }

  setError(error: string) {
    this.currentError = error;
  }
  getError(){
    return this.currentError
  }
  setSearchString(searchString: string | null){
    if(searchString!=null)
      this.searchString = searchString;
  }
  getSearchString(){
    return this.searchString
  }
  setSearchStatus(status: string | null){
    if(status!=null){
       this.status = status;
    }
  }
  getSearchStatus(){
    return this.status
  }
}