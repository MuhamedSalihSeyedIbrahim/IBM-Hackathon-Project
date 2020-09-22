import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReviewSupplierComponent } from './view-review-supplier.component';

describe('ViewReviewSupplierComponent', () => {
  let component: ViewReviewSupplierComponent;
  let fixture: ComponentFixture<ViewReviewSupplierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewReviewSupplierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewReviewSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
