import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirPollutionReportComponent } from './air-pollution-report.component';

describe('AirPollutionReportComponent', () => {
  let component: AirPollutionReportComponent;
  let fixture: ComponentFixture<AirPollutionReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AirPollutionReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AirPollutionReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
