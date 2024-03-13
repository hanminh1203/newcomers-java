import { Component} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

interface Locale {
  localeCode: string;
  label: string;
}
@Component({
  selector: 'app-page-header',
  templateUrl: './page-header.component.html',
  styleUrl: './page-header.component.css'
})
export class PageHeaderComponent {

  selectedLang = "en";

  constructor(private translate: TranslateService) {
    translate.setDefaultLang('en');
    translate.use('en');
  }
 
  useLanguage(language: string): void {
    this.translate.use(language);
    this.selectedLang=language;
}
}
