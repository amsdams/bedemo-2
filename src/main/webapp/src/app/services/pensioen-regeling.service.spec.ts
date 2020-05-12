import { TestBed } from '@angular/core/testing';

import { PensioenRegelingService } from './pensioen-regeling.service';

describe('PensioenRegelingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PensioenRegelingService = TestBed.get(PensioenRegelingService);
    expect(service).toBeTruthy();
  });
});
