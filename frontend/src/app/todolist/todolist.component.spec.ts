import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TodoListComponent } from './todolist.component';
import { TaskService } from './task/task.service';
import { TaskComponent } from './task/task.component';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';
import {ActivatedRoute} from '@angular/router';

describe('TodoListComponent', () => {
  let component: TodoListComponent;
  let fixture: ComponentFixture<TodoListComponent>;
  let taskServiceSpy: jasmine.SpyObj<TaskService>;
  let activatedRouteSpy: jasmine.SpyObj<ActivatedRoute>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('TodoService', [
      'getAllTasks',
      'createTask',
      'updatePartialTask',
      'deleteTask'
    ]);

    spy.getAllTasks.and.returnValue(of([]));

    const activatedRouteMock = {
      snapshot: {
        data: {
          tasks: [
            { id: 1, label: 'Task 1', completed: true },
            { id: 2, label: 'Task 2', completed: false }
          ]
        }
      }
    };

    await TestBed.configureTestingModule({
      imports: [TaskComponent, FormsModule, TodoListComponent],
      providers: [{ provide: TaskService, useValue: spy } , {provide: ActivatedRoute, useValue: activatedRouteMock}],
    }).compileComponents();

    taskServiceSpy = TestBed.inject(TaskService) as jasmine.SpyObj<TaskService>;
    activatedRouteSpy = TestBed.inject(ActivatedRoute) as jasmine.SpyObj<ActivatedRoute>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TodoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    // THEN
    expect(component).toBeTruthy();
  });

  it('should init data with resolver', () => {
    // THEN
    expect(component.todoList).toEqual([
      { id: 1, label: 'Task 1', completed: true },
      { id: 2, label: 'Task 2', completed: false }
    ]);
  });

  it('should load the task list when filterCompleted is set', () => {
    // GIVEN
    const mockTasks = [
      { id: 1, label: 'Task 1', completed: true },
      { id: 2, label: 'Task 2', completed: true }
    ];
    taskServiceSpy.getAllTasks.and.returnValue(of(mockTasks));

    // WHEN
    component.filterCompleted = true;
    component.loadTodoList();
    // THEN
    expect(taskServiceSpy.getAllTasks).toHaveBeenCalledWith(true);
    expect(component.todoList).toEqual(mockTasks);
  });

  it('should create a new task and reload the task list', () => {
    // GIVEN
    const newTaskLabel = 'New Task';
    component.newTaskLabel = newTaskLabel;

    taskServiceSpy.createTask.and.returnValue(of({ id: 1, label: newTaskLabel, completed: false}));
    spyOn(component, 'loadTodoList');

    // WHEN
    component.createTask();
    // THEN
    expect(taskServiceSpy.createTask).toHaveBeenCalledWith({ label: newTaskLabel, completed: false });
    expect(component.loadTodoList).toHaveBeenCalled();
    expect(component.newTaskLabel).toBe('');
  });

  it('should toggle the completed status of a task', () => {
    // GIVEN
    const taskId = 1;
    const completedStatus = true;
    const updateItem = { completed: completedStatus };

    taskServiceSpy.updatePartialTask.and.returnValue(of({id: taskId, completed: completedStatus}));
    spyOn(component, 'loadTodoList');

    // WHEN
    component.toggleCompleted(taskId, completedStatus);
    // THEN
    expect(taskServiceSpy.updatePartialTask).toHaveBeenCalledWith(taskId, updateItem);
    expect(component.loadTodoList).toHaveBeenCalled();
  });

  it('should delete a task and reload the task list', () => {
    // GIVEN
    const taskId = 1;
    taskServiceSpy.deleteTask.and.returnValue(of(undefined));
    spyOn(component, 'loadTodoList');

    // WHEN
    component.deleteTask(taskId);
    // THEN
    expect(taskServiceSpy.deleteTask).toHaveBeenCalledWith(taskId);
    expect(component.loadTodoList).toHaveBeenCalled();
  });

  it('should call loadTodoList when the filter changes', () => {
    // GIVEN
    spyOn(component, 'loadTodoList');
    // WHEN
    component.onFilterChange(true);
    // THEN
    expect(component.loadTodoList).toHaveBeenCalled();
  });

  it('should update filterCompleted and call loadTodoList when the filter is changed', () => {
    // GIVEN
    spyOn(component, 'loadTodoList');
    const filter = true;
    // WHEN
    component.onFilterChange(filter);
    // THEN
    expect(component.filterCompleted).toBe(filter);
    expect(component.loadTodoList).toHaveBeenCalled();
  });
});
