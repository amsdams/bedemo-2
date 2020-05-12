import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeelnemersListComponent } from './deelnemers-list.component';

describe('DeelnemersListComponent', () => {
  let component: DeelnemersListComponent;
  let fixture: ComponentFixture<DeelnemersListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeelnemersListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeelnemersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
