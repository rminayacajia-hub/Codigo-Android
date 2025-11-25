import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EcontactoPage } from './econtacto.page';

describe('EcontactoPage', () => {
  let component: EcontactoPage;
  let fixture: ComponentFixture<EcontactoPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EcontactoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
