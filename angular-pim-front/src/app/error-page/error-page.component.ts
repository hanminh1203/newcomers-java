import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SharedDataService } from '../service/shared-data.service';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrl: './error-page.component.css'
})
export class ErrorPageComponent {
  errorMessage?: string;

  constructor(private router: Router, private sharedDataService: SharedDataService) { }

  ngOnInit(): void {
    this.errorMessage = this.sharedDataService.getError();
  } 

}
