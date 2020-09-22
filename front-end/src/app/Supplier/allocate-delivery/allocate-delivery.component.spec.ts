import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllocateDeliveryComponent } from './allocate-delivery.component';

describe('AllocateDeliveryComponent', () => {
  let component: AllocateDeliveryComponent;
  let fixture: ComponentFixture<AllocateDeliveryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllocateDeliveryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllocateDeliveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
