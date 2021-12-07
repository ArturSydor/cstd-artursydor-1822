import { TestBed } from '@angular/core/testing';

import { AirPollutionSensorService } from './air-pollution-sensor.service';

describe('AirPollutionSensorService', () => {
  let service: AirPollutionSensorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirPollutionSensorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
