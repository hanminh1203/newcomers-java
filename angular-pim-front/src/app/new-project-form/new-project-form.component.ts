import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../service/project.service';

@Component({
  selector: 'app-new-project-form',
  templateUrl: './new-project-form.component.html',
  styleUrl: './new-project-form.component.css'
})
export class NewProjectFormComponent implements OnInit {
  constructor( private route: ActivatedRoute, private projectService: ProjectService){}
  project: any;
  number: any;
  projectId? :number;
  status = ['NEW', 'PLA', 'INP', 'FIN'];
  group = ['NEW'];
  
  getProject(projectId: number){
      let res = this.projectService.getProjectById(projectId).subscribe(data => {
        this.project = data;
        this.group.push(this.project.group);
        this.projectForm.patchValue({
          projectNumber: this.project.projectNumber,
          projectName: this.project.name,
          projectCustomer: this.project.customer,
          projectGroup: this.project.groupName,
          projectMember: this.project.membersVisa,
          projectStartDate: this.project.startDate,
          projectEndDate: this.project.endDate,
        });
      });
  
  }
  

  ngOnInit(){
    if(this.projectId = Number(this.route.snapshot.paramMap.get('id'))){
    this.getProject(this.projectId)
    }
  };

  projectForm = new FormGroup({
    projectNumber: new FormControl('', Validators.required),
    projectName: new FormControl('', Validators.required),
    projectCustomer: new FormControl('', Validators.required),
    projectGroup: new FormControl(this.status[0], Validators.required),
    projectMember: new FormControl(''),
    projectStatus: new FormControl(this.status[0], Validators.required),
    projectStartDate: new FormControl('', Validators.required),
    projectEndDate: new FormControl(''),

  })
  
get name() {
  return this.projectForm.get('projectNumber');
}
}
