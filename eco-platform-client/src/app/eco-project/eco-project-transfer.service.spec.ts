import { TestBed } from '@angular/core/testing';

import { EcoProjectTransferService } from './eco-project-transfer.service';

describe('EcoProjectTransferService', () => {
  let service: EcoProjectTransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EcoProjectTransferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
