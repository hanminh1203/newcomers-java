import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Project } from '../project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private apiUrl = 'http://localhost:8080/projects';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getProjectById(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/${id}`);
  }

  createProject(projectData: Project): Observable<any> {
    return this.http.post<Project>(this.apiUrl, projectData, this.httpOptions);
  }

  updateProject(id: number, projectData: Project): Observable<any> {
    return this.http.put<Project>((`${this.apiUrl}/${id}`), projectData, this.httpOptions);
  }
}