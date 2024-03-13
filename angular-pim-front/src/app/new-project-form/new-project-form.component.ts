import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../service/project.service';
import { Project } from '../project';
import { GroupService } from '../service/group.service';
import { CommonService } from '../service/common.service';
import { ErrorResponse } from '../ErrorResponse';
import { SharedDataService } from '../service/shared-data.service';
import {Location} from '@angular/common';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-new-project-form',
  templateUrl: './new-project-form.component.html',
  styleUrl: './new-project-form.component.css'
})
export class NewProjectFormComponent implements OnInit {
  constructor( private location: Location, private route: ActivatedRoute, private router: Router, private projectService: ProjectService, 
    private groupService: GroupService, public sharedDataService: SharedDataService, 
    private commonService: CommonService, private titleService:Title, private translate: TranslateService){}

  project?: Project;
  projectId? :number;

  group: string[] = ['New'];

  isProjectExist = false;

  // error handler
  existProjectNumberError = false;
  unavailableVisasArr?: string;

  projectForm = new FormGroup({
    projectNumber: new FormControl('', [Validators.required,Validators.pattern(/^-?(0|[1-9]\d*)?$/), Validators.max(9999)]),
    projectName: new FormControl('', Validators.required),
    projectCustomer: new FormControl('', Validators.required),
    projectGroup: new FormControl(this.group[0], Validators.required),
    projectMember: new FormControl(''),
    projectStatus: new FormControl(this.sharedDataService.statusArr[0], Validators.required),
    projectStartDate: new FormControl('', Validators.required),
    projectEndDate: new FormControl(''),

  })

  ngOnInit(){
    let projectIdPath = this.route.snapshot.paramMap.get('id') // get id from path
    if(this.projectId = Number(projectIdPath)){ // if id is number, call to back end to get project info and set title to edit form
      this.getProject(this.projectId)
      this.titleService.setTitle(this.translate.instant('title.edit'));
    }
    else if("new" == String(projectIdPath)){ // if it is not number but "new", keep the form stay clean, set title to create form
      this.titleService.setTitle(this.translate.instant('title.new'));
    }
    else{ 
      let errorResponse: ErrorResponse = {
        status: 404,
        message: "not an id",
        detail: `${projectIdPath} is not an id`,
      };
      this.commonService.redirectOnError(errorResponse.message);
    }
    // always run code, get all the group id for form selection option
    this.groupService.getGroupIds().subscribe(data => 
      data.forEach(id => this.group.push(id)));

      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  };
  
  
  getProject(projectId: number){
      this.projectService.getProjectById(projectId).subscribe(
        // call back end to get project infor, if success, patch project value to the fomr
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
      // if fail, call to decideHandleErrorWay() to handle the error, depend on which error did back end return
      (error) => {
        this.decideHandleErrorWay(error);
      }
    );
  }

  Submit(){
    // on submission contruct a project instance first
    this.project = new Project(this.projectForm.controls.projectNumber.value, this.projectForm.controls.projectName.value, 
      this.projectForm.controls.projectCustomer.value, this.projectForm.controls.projectGroup.value, 
      this.projectForm.controls.projectMember.value, this.projectForm.controls.projectStartDate.value,
      this.projectForm.controls.projectStatus.value, this.projectForm.controls.projectEndDate.value )
  
    if(this.projectId){ // if project id is number, send update request to back end
        this.projectService.updateProject(this.projectId, this.project).subscribe(
          (success) =>{
            this.redirectOnSubmit();
          },
          (error) => {
            this.decideHandleErrorWay(error);
          }
        )
      }
    else{ // if it's not, send request create to back end
        this.projectService.createProject(this.project).subscribe(
          (success) =>{
            this.redirectOnSubmit();
          },
          (error) => {
            this.decideHandleErrorWay(error);
          }
        )
      }
    }
  
    private redirectOnSubmit(){
      // remove search history before redirect
      this.sharedDataService.setSearchString('');
      this.sharedDataService.setSearchStatus('');
      this.router.navigateByUrl('/projects');
    }



    private decideHandleErrorWay(error: ErrorResponse){
      switch (error.message){
        case "project number exist": this.existProjectNumberError = true;
        break;
        case "visa not available": this.unavailableVisasArr = error.detail;
        break;
        default: 
            this.commonService.redirectOnError(error.message)
      }
    }

  cancel(){
    this.router.navigateByUrl('/projects');
  }
}
