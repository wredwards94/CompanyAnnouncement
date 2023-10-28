import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamsModalComponent } from './teams-modal.component';

describe('TeamsModalComponent', () => {
  let component: TeamsModalComponent;
  let fixture: ComponentFixture<TeamsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamsModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
