import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { TodoService, Task, TaskInDto } from '../../todolist.service';
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
  todoService= inject(TodoService);
  taskId!: number;
  task!: Task;

  ngOnInit(): void {
    this.taskId = Number(this.route.snapshot.paramMap.get('id'))!;
    this.loadTodoTask();
  }

  loadTodoTask(): void {
    this.todoService.getTaskById(this.taskId).subscribe((task) => {
      this.task = task;
    });
  }

  save(): void {
    const updatedTask: TaskInDto = { label: this.task.label };
    this.todoService.updatePartialTask(this.taskId, updatedTask).subscribe(() => this.back());
  }

  back(): void {
    this.router.navigate(['/']);
  }
}
