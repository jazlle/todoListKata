import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TodoListItem {
  id: number;
  label: string;
  completed: boolean;
}

export interface TodoListItemInDto {
  label?: string;
  completed?: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  private apiUrl = '/api/todolist-item';

  constructor(private http: HttpClient) {}

  getAllTodoListItems(): Observable<TodoListItem[]> {
    return this.http.get<TodoListItem[]>(this.apiUrl);
  }

  createTodoListItem(item: TodoListItemInDto): Observable<TodoListItem> {
    return this.http.post<TodoListItem>(this.apiUrl, item);
  }

  updateTodoListItem(id: number, item: TodoListItemInDto): Observable<TodoListItem> {
    return this.http.put<TodoListItem>(`${this.apiUrl}/${id}`, item);
  }

  updatePartialTodoListItem(id: number, item: TodoListItemInDto): Observable<TodoListItem> {
    return this.http.patch<TodoListItem>(`${this.apiUrl}/${id}`, item);
  }

  deleteTodoListItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
