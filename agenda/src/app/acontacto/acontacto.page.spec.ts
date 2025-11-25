import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AcontactoPage } from './acontacto.page';

describe('AcontactoPage', () => {
  let component: AcontactoPage;
  let fixture: ComponentFixture<AcontactoPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(AcontactoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
