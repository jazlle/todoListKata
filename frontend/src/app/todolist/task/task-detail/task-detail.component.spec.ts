import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskDetailComponent } from './task-detail.component';
import { TodoService } from '../../todolist.service';
import { Router, ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

describe('TaskDetailComponent', () => {
  let component: TaskDetailComponent;
  let fixture: ComponentFixture<TaskDetailComponent>;
  let todoServiceSpy: jasmine.SpyObj<TodoService>;
  let routerSpy: jasmine.SpyObj<Router>;
  let activatedRouteSpy: jasmine.SpyObj<ActivatedRoute>;

  beforeEach(async () => {
    const todoServiceMock = jasmine.createSpyObj('TodoService', ['getTaskById', 'updatePartialTask']);
    const routerMock = jasmine.createSpyObj('Router', ['navigate']);
    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: jasmine.createSpy('get').and.returnValue('1'), // Mock task ID as '1'
        },
      },
    };

    await TestBed.configureTestingModule({
      imports: [FormsModule, TaskDetailComponent],
      providers: [
        { provide: TodoService, useValue: todoServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
      ],
    }).compileComponents();

    todoServiceSpy = TestBed.inject(TodoService) as jasmine.SpyObj<TodoService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    activatedRouteSpy = TestBed.inject(ActivatedRoute) as jasmine.SpyObj<ActivatedRoute>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskDetailComponent);
    component = fixture.componentInstance;

    todoServiceSpy.getTaskById.and.returnValue(
      of({ id: 1, label: 'Mock Task', completed: false })
    );

    fixture.detectChanges();
  });

  it('should create the component', () => {
    // THEN
    expect(component).toBeTruthy();
  });

  it('should load the task on initialization', () => {
    // THEN
    expect(todoServiceSpy.getTaskById).toHaveBeenCalledWith(1);
    expect(component.task).toEqual({ id: 1, label: 'Mock Task', completed: false });
  });

  it('should navigate back when back() is called', () => {
    // WHEN
    component.back();
    // THEN
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should call save() and update the task label', () => {
    // GIVEN
    const updatedLabel = 'Updated Task Label';
    component.task.label = updatedLabel;

    todoServiceSpy.updatePartialTask.and.returnValue(of({id: 1, label: updatedLabel, completed: true}));
    spyOn(component, 'back');

    // WHEN
    component.save();

    // THEN
    expect(todoServiceSpy.updatePartialTask).toHaveBeenCalledWith(1, { label: updatedLabel });
    expect(component.back).toHaveBeenCalled();
  });

  it('should call save() when Enter is pressed in the input field', () => {
    // GIVEN
    const input = fixture.debugElement.query(By.css('input')).nativeElement;

    spyOn(component, 'save');
    input.dispatchEvent(new KeyboardEvent('keydown', { key: 'Enter' }));

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(component.save).toHaveBeenCalled();
  });

  it('should call back() when the back button is clicked', () => {
    // GIVEN
    spyOn(component, 'back');
    const backButton = fixture.debugElement.query(By.css('.back')).nativeElement;

    // WHEN
    backButton.click();

    // THEN
    expect(component.back).toHaveBeenCalled();
  });

  it('should call save() when the save button is clicked', () => {
    // GIVEN
    spyOn(component, 'save');
    const saveButton = fixture.debugElement.query(By.css('.save')).nativeElement;

    // WHEN
    saveButton.click();

    // THEN
    expect(component.save).toHaveBeenCalled();
  });
});
