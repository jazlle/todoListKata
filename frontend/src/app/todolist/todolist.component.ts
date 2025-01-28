import {Component, inject, OnInit} from '@angular/core';
import { TodoService, Task, TaskInDto } from './todolist.service';
import {TaskComponent} from './task/task.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.component.html',
  styleUrls: ['./todolist.component.css'],
  imports: [
    TaskComponent,
    FormsModule,
    CommonModule
  ],
})
export class TodoListComponent implements OnInit {
  todoService = inject(TodoService);
  todoList: Task[] = [];
  newTaskLabel = '';
  filterCompleted: boolean | 'all' = 'all';

  ngOnInit(): void {
    this.loadTodoList();
  }

  loadTodoList(): void {
    this.todoService.getAllTasks(this.filterCompleted).subscribe((tasks) => (this.todoList = tasks));
  }

  createTodo(): void {
    if (this.newTaskLabel.trim()) {
      const newItem: TaskInDto = { label: this.newTaskLabel, completed: false };
      this.todoService.createTask(newItem).subscribe(() => {
        this.newTaskLabel = '';
        this.loadTodoList();
      });
    }
  }

  toggleCompleted(id: number, completed: boolean): void {
    const updateItem: TaskInDto = { completed };
    this.todoService.updatePartialTask(id, updateItem).subscribe(() => this.loadTodoList());
  }

  deleteTodoItem(id: number): void {
    this.todoService.deleteTask(id).subscribe(() => this.loadTodoList());
  }

  onFilterChange(filter: boolean | 'all'): void {
    this.filterCompleted = filter;
    this.loadTodoList();
  }
}
