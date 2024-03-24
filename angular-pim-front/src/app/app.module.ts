import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { NewProjectFormComponent } from './new-project-form/new-project-form.component';
import { PageHeaderComponent } from './page-header/page-header.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ErrorPageComponent } from './error-page/error-page.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { AutofocusDirective } from './directive/autofocus.directive';
import { AutoFocusModule } from 'primeng/autofocus';
import { RouterLinkActive, RouterModule } from '@angular/router';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { NgSelectModule } from '@ng-select/ng-select';
@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    NewProjectFormComponent,
    PageHeaderComponent,
    ErrorPageComponent,
    ProjectListComponent,
    AutofocusDirective,
  ],
  imports: [
    NgSelectModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    AutoFocusModule,
    RouterModule,
    RouterLinkActive,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
  })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
