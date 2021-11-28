import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EcoProjectComponent } from './eco-project.component';

describe('EcoProjectComponent', () => {
  let component: EcoProjectComponent;
  let fixture: ComponentFixture<EcoProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EcoProjectComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EcoProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
