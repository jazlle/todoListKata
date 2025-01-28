import {Component, inject, OnInit} from '@angular/core';
import { TaskService } from './task/task.service';
import {TaskComponent} from './task/task.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Task, TaskInDto} from './task/task.model';
import {ActivatedRoute} from '@angular/router';

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
  taskService = inject(TaskService);
  route= inject(ActivatedRoute);
  todoList: Task[] = [];
  newTaskLabel = '';
  filterCompleted: boolean | 'all' = 'all';

  ngOnInit(): void {
    this.todoList = this.route.snapshot.data['tasks'];
  }

  loadTodoList(): void {
    this.taskService.getAllTasks(this.filterCompleted).subscribe((tasks) => (this.todoList = tasks));
  }

  createTask(): void {
    if (this.newTaskLabel.trim()) {
      const newTask: TaskInDto = { label: this.newTaskLabel, completed: false };
      this.taskService.createTask(newTask).subscribe(() => {
        this.newTaskLabel = '';
        this.loadTodoList();
      });
    }
  }

  toggleCompleted(id: number, completed: boolean): void {
    const updateTask: TaskInDto = { completed };
    this.taskService.updatePartialTask(id, updateTask).subscribe(() => this.loadTodoList());
  }

  deleteTask(id: number): void {
    this.taskService.deleteTask(id).subscribe(() => this.loadTodoList());
  }

  onFilterChange(filter: boolean | 'all'): void {
    this.filterCompleted = filter;
    this.loadTodoList();
  }
}
