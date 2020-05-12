import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeelnemerDetailsComponent } from './deelnemer-details.component';

describe('DeelnemerDetailsComponent', () => {
  let component: DeelnemerDetailsComponent;
  let fixture: ComponentFixture<DeelnemerDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeelnemerDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeelnemerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
