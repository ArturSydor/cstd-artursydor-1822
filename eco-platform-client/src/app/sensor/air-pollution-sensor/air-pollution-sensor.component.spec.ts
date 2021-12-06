import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirPollutionSensorComponent } from './air-pollution-sensor.component';

describe('AirPollutionSensorComponent', () => {
  let component: AirPollutionSensorComponent;
  let fixture: ComponentFixture<AirPollutionSensorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AirPollutionSensorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AirPollutionSensorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
