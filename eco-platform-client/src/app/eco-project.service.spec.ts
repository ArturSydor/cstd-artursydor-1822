import { TestBed } from '@angular/core/testing';

import { EcoProjectService } from './eco-project.service';

describe('EcoProjectService', () => {
  let service: EcoProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EcoProjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
