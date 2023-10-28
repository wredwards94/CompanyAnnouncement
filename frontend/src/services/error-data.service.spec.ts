import { TestBed } from '@angular/core/testing';

import { ErrorDataService } from './error-data.service';

describe('ErrorDataService', () => {
  let service: ErrorDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ErrorDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
