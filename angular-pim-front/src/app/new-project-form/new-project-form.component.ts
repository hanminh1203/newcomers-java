import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../service/project.service';
import { Project } from '../project';
import { GroupService } from '../service/group.service';
import { EmployeeService } from '../service/employee.service';
import { CommonService } from '../service/common.service';
import { ErrorResponse } from '../ErrorResponse';
import { SharedDataService } from '../service/shared-data.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-new-project-form',
  templateUrl: './new-project-form.component.html',
  styleUrl: './new-project-form.component.css'
})
export class NewProjectFormComponent implements OnInit {
  constructor( private location: Location, private route: ActivatedRoute, private router: Router, private projectService: ProjectService, 
    private groupService: GroupService, private employeeService: EmployeeService, private sharedDataService: SharedDataService){}
  project?: Project;
  projectId? :number;
  status: string[] = ['NEW', 'PLA', 'INP', 'FIN'];
  group: string[] = ['NEW'];
  visas: string[] = [];
  isProjectExist = false;
  submited = false;

  @Output() newItemEvent = new EventEmitter<string>();

  postErrorToParent(value: string) {
    this.newItemEvent.emit(value);
  }
  // error handler
  existProjectNumberError? : unknown;
  notValidVisas?: string[];
  notValidVisasExist: unknown;

  
  
  getProject(projectId: number){
      this.projectService.getProjectById(projectId).subscribe(
        (data) => {
        this.isProjectExist = true;
        this.project = data;
        this.group.push(this.project.groupId);
        this.projectForm.patchValue({
          projectNumber: this.project.projectNumber,
          projectName: this.project.name,
          projectCustomer: this.project.customer,
          projectGroup: this.project.groupId,
          projectMember: this.project.membersVisa,
          projectStatus: this.project.status,
          projectStartDate: this.project.startDate,
          projectEndDate: this.project.endDate,
        });
      }, 
      (error) => {
        this.decideHandleErrorWay(error);
      }
    );
  }
  private decideHandleErrorWay(error: ErrorResponse){
    switch (error.message){
      case "object not found": this.redirectOnError(error);
      break;
      case "project number exist": this.existProjectNumberError = true;
      break;
      default: console.log(error.message);
    }

  }
  private redirectOnError(error: ErrorResponse){
    this.sharedDataService.setError(error.detail);
    this.router.navigateByUrl('/404');
  }
  

  ngOnInit(){
    if(this.projectId = Number(this.route.snapshot.paramMap.get('id'))){
    this.getProject(this.projectId)
    }
    else{

    }

    this.employeeService.getVisas().subscribe(data => 
      data.forEach(visa => this.visas.push(visa)));
    // always run code, get all the group id for form selection option
    this.groupService.getGroupIds().subscribe(data => 
      data.forEach(id => this.group.push(id)));

    // another always run code, get all employees visa
   
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
  
Submit(){
  let visasFromForm: string[] = (this.projectForm.controls.projectMember.value?.split(",") || []).map(item => item.trim());
  this.notValidVisas = this.employeeService.checkIfNotExists(visasFromForm, this.visas);
  // check if notvalidVisas return a empty array
  this.notValidVisasExist = this.notValidVisas[0];
  this.project = new Project(this.projectForm.controls.projectNumber.value, this.projectForm.controls.projectName.value, 
    this.projectForm.controls.projectCustomer.value, this.projectForm.controls.projectGroup.value, 
    this.projectForm.controls.projectMember.value, this.projectForm.controls.projectStartDate.value,
    this.projectForm.controls.projectStatus.value, this.projectForm.controls.projectEndDate.value )

  if(this.projectId = Number(this.route.snapshot.paramMap.get('id'))){
      this.projectService.updateProject(this.projectId, this.project).subscribe(
        (data) =>{
          this.submited = true
        },
        (error) => {
          this.decideHandleErrorWay(error);
        }
      )
    }
  else if("new" == String(this.route.snapshot.paramMap.get('id'))){
      this.projectService.createProject(this.project).subscribe(
        (data) =>{
          this.submited = true
        },
        (error) => {
          this.existProjectNumberError = true;
        }
      )
    }
  }
}
