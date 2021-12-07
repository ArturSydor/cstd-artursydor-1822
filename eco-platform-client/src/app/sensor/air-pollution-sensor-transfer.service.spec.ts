import { TestBed } from '@angular/core/testing';

import { AirPollutionSensorTransferService } from './air-pollution-sensor-transfer.service';

describe('AirPollutionSensorTransferService', () => {
  let service: AirPollutionSensorTransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirPollutionSensorTransferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
