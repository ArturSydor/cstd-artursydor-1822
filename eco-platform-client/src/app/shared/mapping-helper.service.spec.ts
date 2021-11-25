import { TestBed } from '@angular/core/testing';

import { MappingHelperService } from './mapping-helper.service';

describe('MappingHelperService', () => {
  let service: MappingHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MappingHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
