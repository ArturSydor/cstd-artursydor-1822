import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirPollutionSensorEditComponent } from './air-pollution-sensor-edit.component';

describe('AirPollutionSensorEditComponent', () => {
  let component: AirPollutionSensorEditComponent;
  let fixture: ComponentFixture<AirPollutionSensorEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AirPollutionSensorEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AirPollutionSensorEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
