import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommentsAndGradesCardComponent } from './comments-and-grades-card.component';

describe('CommentsAndGradesCardComponent', () => {
  let component: CommentsAndGradesCardComponent;
  let fixture: ComponentFixture<CommentsAndGradesCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommentsAndGradesCardComponent]
    });
    fixture = TestBed.createComponent(CommentsAndGradesCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
