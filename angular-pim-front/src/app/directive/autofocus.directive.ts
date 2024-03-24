import { Directive, OnInit, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[Autofocus]'
})
export class AutofocusDirective {

  constructor(private elementRef: ElementRef) { };

  @Input() set AutoFocus(condition: boolean) {
    if (condition) {
    this.elementRef.nativeElement.focus();
  }

}
}