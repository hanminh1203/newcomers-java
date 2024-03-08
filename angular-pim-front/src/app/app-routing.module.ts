import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewProjectFormComponent } from './new-project-form/new-project-form.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { ProjectListComponent } from './project-list/project-list.component';


const routes: Routes = [
  { path: '', redirectTo: '/projects', pathMatch: 'full' },
  { path: 'projects', component: ProjectListComponent},
  { path: 'projects/:id', component: NewProjectFormComponent },
  { path: '404', component: ErrorPageComponent },
  { path: '**', redirectTo: '/404' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }