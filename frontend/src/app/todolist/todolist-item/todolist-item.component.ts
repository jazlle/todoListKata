import { Component, Input, Output, EventEmitter } from '@angular/core';
import { TodoListItem } from '../todolist.service';

@Component({
  selector: 'app-todolist-item',
  templateUrl: './todolist-item.component.html',
  styleUrls: ['./todolist-item.component.css'],
})
export class TodoListItemComponent {
  @Input() todo!: TodoListItem;
  @Output() updateLabel = new EventEmitter<string>();
  @Output() toggleCompleted = new EventEmitter<boolean>();
  @Output() delete = new EventEmitter<void>();

  onLabelChange(newLabel: string): void {
    this.updateLabel.emit(newLabel);
  }

  onToggleCompleted(): void {
    this.toggleCompleted.emit(!this.todo.completed);
  }

  onDelete(): void {
    this.delete.emit();
  }
}
