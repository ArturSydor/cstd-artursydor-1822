import { TestBed } from '@angular/core/testing';

import { DialogHelperService } from './dialog-helper.service';

describe('ErrorHelperService', () => {
  let service: DialogHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DialogHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
