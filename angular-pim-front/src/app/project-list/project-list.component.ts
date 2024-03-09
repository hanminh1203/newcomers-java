import { Component, OnInit } from '@angular/core';
import { Project } from '../project';
import { ProjectService } from '../service/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit{
  constructor(private projectService: ProjectService,private route: ActivatedRoute, private router: Router){}

  status: string[] = ['NEW', 'PLA', 'INP', 'FIN'];
  searchQuery: any;
  
  projectList?: Project[];
  selectedProjects: Project[] = [];
  selectedProjectCount = 0;

  searchForm = new FormGroup({
    criteria: new FormControl(''),
    status: new FormControl('')
  })

  ngOnInit(): void {
    this.projectService.getAllProjects().subscribe(
      (data)=> this.projectList = data)
    }

  search(): void {
    this.projectService.searchByCriteria(this.searchForm.controls.criteria.value).subscribe(
      (data) => {this.projectList = data},
      (error) => console.log(error.message))
    }

    reset(){
      this.router.navigateByUrl('/projects');
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
        this.selectedProjectCount -=1;
      } else {
        this.selectedProjects.push(project);
        this.selectedProjectCount +=1;
      }
    }
  
    isSelected(project: Project): boolean {
      return this.selectedProjects.some(p => p.id === project.id);
    }
}