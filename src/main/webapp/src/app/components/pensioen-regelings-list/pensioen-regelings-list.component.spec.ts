import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PensioenRegelingsListComponent } from './pensioen-regelings-list.component';

describe('PensioenRegelingsListComponent', () => {
  let component: PensioenRegelingsListComponent;
  let fixture: ComponentFixture<PensioenRegelingsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PensioenRegelingsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PensioenRegelingsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
