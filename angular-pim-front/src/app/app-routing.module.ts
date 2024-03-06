import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewProjectFormComponent } from './new-project-form/new-project-form.component';


const routes: Routes = [
  { path: 'projects/:id', component: NewProjectFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }