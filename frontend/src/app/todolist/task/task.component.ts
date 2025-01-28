import {Component, Input, Output, EventEmitter, inject} from '@angular/core';
import {Task} from './task.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css'],
})
export class TaskComponent {
  router = inject(Router);
  @Input() task!: Task;
  @Output() updateLabel = new EventEmitter<string>();
  @Output() toggleCompleted = new EventEmitter<boolean>();
  @Output() delete = new EventEmitter<void>();


  onModify() {
    this.router.navigate(['/task', this.task.id]);
  }

  onLabelChange(newLabel: string): void {
    this.updateLabel.emit(newLabel);
  }

  onToggleCompleted(): void {
    this.toggleCompleted.emit(!this.task.completed);
  }

  onDelete(): void {
    this.delete.emit();
  }
}
