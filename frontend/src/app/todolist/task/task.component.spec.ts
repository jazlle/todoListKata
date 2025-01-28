import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskComponent } from './task.component';
import { Router } from '@angular/router';
import { Task } from '../todolist.service';
import { By } from '@angular/platform-browser';

describe('TaskComponent', () => {
  let component: TaskComponent;
  let fixture: ComponentFixture<TaskComponent>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [TaskComponent],
      providers: [{ provide: Router, useValue: routerMock }]
    }).compileComponents();

    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskComponent);
    component = fixture.componentInstance;

    component.task = {
      id: 1,
      label: 'Test Task',
      completed: false
    } as Task;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    // THEN
    expect(component).toBeTruthy();
  });

  it('should navigate to the task modification page when onModify is called', () => {
    // WHEN
    component.onModify();
    // THEN
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/task', component.task.id]);
  });

  it('should emit updateLabel when onLabelChange is called', () => {
    // GIVEN
    spyOn(component.updateLabel, 'emit');
    const newLabel = 'Updated Task Label';

    // WHEN
    component.onLabelChange(newLabel);

    // THEN
    expect(component.updateLabel.emit).toHaveBeenCalledWith(newLabel);
  });

  it('should emit toggleCompleted when the checkbox is clicked', () => {
    // GIVEN
    spyOn(component.toggleCompleted, 'emit');

    // WHEN
    const checkbox = fixture.debugElement.query(By.css('input[type="checkbox"]')).nativeElement;
    checkbox.click();

    // THEN
    expect(component.toggleCompleted.emit).toHaveBeenCalledWith(!component.task.completed);
  });

  it('should emit delete when onDelete is called', () => {
    // GIVEN
    spyOn(component.delete, 'emit');

    // WHEN
    const deleteButton = fixture.debugElement.query(By.css('.delete')).nativeElement;
    deleteButton.click();

    // THEN
    expect(component.delete.emit).toHaveBeenCalled();
  });

  it('should call onModify when the modify button is clicked', () => {
    // GIVEN
    spyOn(component, 'onModify');

    // WHEN
    const modifyButton = fixture.debugElement.query(By.css('.modify')).nativeElement;
    modifyButton.click();

    // THEN
    expect(component.onModify).toHaveBeenCalled();
  });

  it('should display the task label in the template', () => {
    // WHEN
    const taskLabelElement = fixture.debugElement.query(By.css('span')).nativeElement;
    // THEN
    expect(taskLabelElement.textContent).toContain('Test Task');
  });
});
