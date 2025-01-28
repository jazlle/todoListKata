import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Task {
  id: number;
  label?: string;
  completed?: boolean;
}

export interface TaskInDto {
  label?: string;
  completed?: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  private apiUrl = '/api/task';

  constructor(private http: HttpClient) {}

  getAllTasks(completed: boolean | 'all'): Observable<Task[]> {
    let params = new HttpParams();
    if (completed !== 'all') {
      params = params.set('completed', completed);
      return this.http.get<Task[]>(this.apiUrl, { params });
    }

    return this.http.get<Task[]>(this.apiUrl);
  }

  getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/${id}`);
  }

  createTask(task: TaskInDto): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, task);
  }

  updatePartialTask(id: number, task: TaskInDto): Observable<Task> {
    return this.http.patch<Task>(`${this.apiUrl}/${id}`, task);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
