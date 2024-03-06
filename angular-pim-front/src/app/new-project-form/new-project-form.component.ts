import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../service/project.service';
import { Project } from '../project';
import { GroupService } from '../service/group.service';
import { EmployeeService } from '../service/employee.service';

@Component({
  selector: 'app-new-project-form',
  templateUrl: './new-project-form.component.html',
  styleUrl: './new-project-form.component.css'
})
export class NewProjectFormComponent implements OnInit {
  constructor( private route: ActivatedRoute, private projectService: ProjectService, 
    private groupService: GroupService, private employeeService: EmployeeService){}
  project?: Project;
  projectId? :number;
  status = ['NEW', 'PLA', 'INP', 'FIN'];
  group: string[] = ['NEW'];
  visas: string[] = [];
  notValidVisas?: string[];
  notValidVisasLen: any = 0;
  
  getProject(projectId: number){
      let res = this.projectService.getProjectById(projectId).subscribe(data => {
        this.project = data;
        this.group.push(this.project.groupId);
        this.projectForm.patchValue({
          projectNumber: this.project.projectNumber,
          projectName: this.project.name,
          projectCustomer: this.project.customer,
          projectGroup: this.project.groupId,
          projectMember: this.project.membersVisa,
          projectStartDate: this.project.startDate,
          projectEndDate: this.project.endDate,
        });
      });
  
  }
  

  ngOnInit(){
    // if path contain id as a number, try to get project by id, todo: handle if no project found 
    if(this.projectId = Number(this.route.snapshot.paramMap.get('id'))){
    this.getProject(this.projectId)
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
  let visasFromForm: string[] = this.projectForm.controls.projectMember.value?.split(",") || [];
  this.notValidVisas = this.employeeService.checkIfNotExists(visasFromForm, this.visas);
  this.notValidVisasLen = this.notValidVisas.length;
  this.project = new Project(this.projectForm.controls.projectNumber.value, this.projectForm.controls.projectName.value, 
    this.projectForm.controls.projectCustomer.value, this.projectForm.controls.projectGroup.value, 
    this.projectForm.controls.projectMember.value, this.projectForm.controls.projectStartDate.value,
    this.projectForm.controls.projectStatus.value, this.projectForm.controls.projectEndDate.value )

  if(this.projectId = Number(this.route.snapshot.paramMap.get('id'))){
      this.projectService.updateProject(this.projectId, this.project).subscribe()
    }
  else if("new" == String(this.route.snapshot.paramMap.get('id'))){
      this.projectService.createProject(this.project).subscribe()
    }
  }
}
