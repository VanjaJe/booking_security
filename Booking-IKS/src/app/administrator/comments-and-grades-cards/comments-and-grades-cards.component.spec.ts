import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentsAndGradesCardsComponent } from './comments-and-grades-cards.component';

describe('CommentsAndGradesCardsComponent', () => {
  let component: CommentsAndGradesCardsComponent;
  let fixture: ComponentFixture<CommentsAndGradesCardsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommentsAndGradesCardsComponent]
    });
    fixture = TestBed.createComponent(CommentsAndGradesCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
