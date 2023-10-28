import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerNavbarComponent } from './worker-navbar.component';

describe('WorkerNavbarComponent', () => {
  let component: WorkerNavbarComponent;
  let fixture: ComponentFixture<WorkerNavbarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkerNavbarComponent]
    });
    fixture = TestBed.createComponent(WorkerNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
