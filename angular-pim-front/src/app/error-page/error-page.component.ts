import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SharedDataService } from '../service/shared-data.service';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrl: './error-page.component.css'
})
export class ErrorPageComponent {
  objectNotFound = false;
  clientErr = false;
  notAnId = false;
  dateConflict = false;

  constructor(private router: Router, private sharedDataService: SharedDataService, private titleService: Title, private translate: TranslateService ) { }

  ngOnInit(): void {
    this.getErrorDetail(this.sharedDataService.getError())
    this.titleService.setTitle(this.translate.instant('title.error'));
  } 
  getErrorDetail(errorMessage: string){
    switch(errorMessage){
      case "client side error": this.clientErr = true;
      break;
      case "object not found": this.objectNotFound = true;
      break;
      case "end date conflicts": this.dateConflict = true;
      break;
      case "not an id": this.notAnId = true
      break;
    }

  }

}
