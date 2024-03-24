import { Component, OnInit } from '@angular/core';
import { Project } from '../project';
import { ProjectService } from '../service/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { SharedDataService } from '../service/shared-data.service';
import { CommonService } from '../service/common.service';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit{
  constructor(private projectService: ProjectService,private route: ActivatedRoute, private router: Router, 
    public sharedDataService: SharedDataService, private commonService: CommonService, private titleService: Title, private translate: TranslateService){}
   
  projectList?: Project[];
  selectedProjects: Project[] = [];
  
  searchForm = new FormGroup({
    searchString: new FormControl(''),
    status: new FormControl('')
  })
  
  ngOnInit(): void {
    this.titleService.setTitle(this.translate.instant('title.projectList'));
    // on init, patch value from shared data service, for history search purpose
    this.searchForm.patchValue({
      searchString: this.sharedDataService.getSearchString(),
      status: this.sharedDataService.getSearchStatus()
    })
    // perform search every time this page init
    this.search()
  }

  search(): void {
    // at first, get value from search form, which always be patched value from shared data service
    this.sharedDataService.setSearchString(this.searchForm.controls.searchString.value);
    this.sharedDataService.setSearchStatus(this.searchForm.controls.status.value);
    // do request to back end, if success, display data, if fail, redirect and show the error
    this.projectService.search(this.sharedDataService.getSearchString(), this.sharedDataService.getSearchStatus()).subscribe(
      (data) => {this.projectList = data},
      (error) => this.commonService.redirectOnError(error))
    }

    reset(){
      // remove search data from sharedData, and then init this page again
      this.sharedDataService.setSearchString('')
      this.sharedDataService.setSearchStatus('')
      this.ngOnInit()
    }

    delete(project: Project){
      this.projectList = this.projectList?.filter(p => p !=project)
      this.projectService.deleteById(project.id).subscribe();
    }

    deleteAll(){
      // get id from selectedProjects, send delete request to back end, then reset the selectdProjects list. 
      let ids:unknown[] = [];
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