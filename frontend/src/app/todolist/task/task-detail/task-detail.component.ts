import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { TaskService } from '../task.service';
import {Task, TaskInDto} from '../task.model';

import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css'],
  imports: [
    FormsModule
  ]
})
export class TaskDetailComponent implements OnInit {
  router= inject(Router);
  route= inject(ActivatedRoute);
  taskService= inject(TaskService);
  task!: Task;

  ngOnInit(): void {
    this.task = this.route.snapshot.data['task'];
  }

  save(): void {
    const updatedTask: TaskInDto = { label: this.task.label };
    this.taskService.updatePartialTask(this.task.id, updatedTask).subscribe(() => this.back());
  }

  back(): void {
    this.router.navigate(['/']);
  }
}
