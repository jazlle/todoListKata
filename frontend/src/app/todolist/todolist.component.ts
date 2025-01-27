import {Component, inject, OnInit} from '@angular/core';
import { TodoService, TodoListItem, TodoListItemInDto } from './todolist.service';
import {TodoListItemComponent} from './todolist-item/todolist-item.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.component.html',
  styleUrls: ['./todolist.component.css'],
  imports: [
    TodoListItemComponent,
    FormsModule,
    CommonModule
  ],
})
export class TodoListComponent implements OnInit {
  todoService = inject(TodoService)
  todoList: TodoListItem[] = [];
  newTodoLabel = '';

  constructor() {}

  ngOnInit(): void {
    this.loadTodoList();
  }

  loadTodoList(): void {
    this.todoService.getAllTodoListItems().subscribe((items) => (this.todoList = items));
  }

  createTodo(): void {
    if (this.newTodoLabel.trim()) {
      const newItem: TodoListItemInDto = { label: this.newTodoLabel, completed: false };
      this.todoService.createTodoListItem(newItem).subscribe(() => {
        this.newTodoLabel = '';
        this.loadTodoList();
      });
    }
  }

  updateTodoLabel(id: number, label: string): void {
    const updateItem: TodoListItemInDto = { label };
    this.todoService.updateTodoListItem(id, updateItem).subscribe(() => this.loadTodoList());
  }

  toggleCompleted(id: number, completed: boolean): void {
    const updateItem: TodoListItemInDto = { completed };
    this.todoService.updatePartialTodoListItem(id, updateItem).subscribe(() => this.loadTodoList());
  }

  deleteTodoItem(id: number): void {
    this.todoService.deleteTodoListItem(id).subscribe(() => this.loadTodoList());
  }
}
