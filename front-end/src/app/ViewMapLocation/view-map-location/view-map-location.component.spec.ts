import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMapLocationComponent } from './view-map-location.component';

describe('ViewMapLocationComponent', () => {
  let component: ViewMapLocationComponent;
  let fixture: ComponentFixture<ViewMapLocationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewMapLocationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewMapLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
