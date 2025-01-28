import {inject, Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {TaskService} from '../task.service';
import {Task} from '../task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskDetailResolver implements Resolve<Task> {
  taskService= inject(TaskService);

  resolve(route: ActivatedRouteSnapshot): Observable<Task> {
    const taskId = route.params['id'];
    return this.taskService.getTaskById(taskId);
  }
}
