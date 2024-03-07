import { Component, ViewChild } from '@angular/core';
import { NewProjectFormComponent } from './new-project-form/new-project-form.component';
import { ProjectService } from './service/project.service';
import { SharedDataService } from './service/shared-data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular-pim-front';

}
