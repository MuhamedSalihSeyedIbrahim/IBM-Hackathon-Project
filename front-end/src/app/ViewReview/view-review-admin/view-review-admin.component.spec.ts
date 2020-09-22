import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReviewAdminComponent } from './view-review-admin.component';

describe('ViewReviewAdminComponent', () => {
  let component: ViewReviewAdminComponent;
  let fixture: ComponentFixture<ViewReviewAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewReviewAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewReviewAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
