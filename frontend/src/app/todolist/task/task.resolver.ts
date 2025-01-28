import {inject, Injectable} from '@angular/core';
import {Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {TaskService} from './task.service';
import {Task} from './task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskResolver implements Resolve<Task[]> {
  taskService= inject(TaskService);

  resolve(): Observable<Task[]> {
    return this.taskService.getAllTasks('all');
  }
}
