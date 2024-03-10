import { Component, OnInit } from '@angular/core';
import { Project } from '../project';
import { ProjectService } from '../service/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { SharedDataService } from '../service/shared-data.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit{
  constructor(private projectService: ProjectService,private route: ActivatedRoute, private router: Router, 
    private sharedDataService: SharedDataService){}

  statusArr: string[] = ['NEW', 'PLA', 'INP', 'FIN'];
   
  projectList?: Project[];
  selectedProjects: Project[] = [];
  
  searchForm = new FormGroup({
    searchString: new FormControl(''),
    status: new FormControl('')
  })
  
  ngOnInit(): void {
    this.searchForm.patchValue({
      searchString: this.sharedDataService.getSearchString(),
      status: this.sharedDataService.getSearchStatus()
    })
    this.search()
  }

  search(): void {
    this.sharedDataService.setSearchString(this.searchForm.controls.searchString.value);
    this.sharedDataService.setSearchStatus(this.searchForm.controls.status.value);
    this.projectService.search(this.sharedDataService.getSearchString(), this.sharedDataService.getSearchStatus()).subscribe(
      (data) => {this.projectList = data},
      (error) => console.log(error.message))
    }

    reset(){
      this.sharedDataService.setSearchString('')
      this.sharedDataService.setSearchStatus('')
      this.ngOnInit()
    }

    delete(project: Project){
      this.projectList = this.projectList?.filter(p => p !=project)
      this.projectService.deleteById(project.id).subscribe();
    }

    deleteAll(){
      let ids:any[] = [];
      for(let project of this.selectedProjects){
        ids.push(project.id)
        this.projectList = this.projectList?.filter(p => p !=project)
      }
      this.projectService.deleteAllByIds(ids).subscribe()
      this.selectedProjects =[]
    }

    toggleSelection(project: Project): void {
      if (this.isSelected(project)) {
        this.selectedProjects = this.selectedProjects.filter(p => p.id !== project.id);
      } else {
        this.selectedProjects.push(project);
      }
    }
  
    isSelected(project: Project): boolean {
      return this.selectedProjects.some(p => p.id === project.id);
    }
}