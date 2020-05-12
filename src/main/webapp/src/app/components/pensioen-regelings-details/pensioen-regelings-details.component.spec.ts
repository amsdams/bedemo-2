import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PensioenRegelingsDetailsComponent } from './pensioen-regelings-details.component';

describe('PensioenRegelingsDetailsComponent', () => {
  let component: PensioenRegelingsDetailsComponent;
  let fixture: ComponentFixture<PensioenRegelingsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PensioenRegelingsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PensioenRegelingsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
