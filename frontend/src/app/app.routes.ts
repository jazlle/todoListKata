import { Routes } from '@angular/router';
import {TodoListComponent} from './todolist/todolist.component';
import {TaskDetailComponent} from './todolist/task/task-detail/task-detail.component';

export const routes: Routes = [
  { path: '', component: TodoListComponent },
  { path: 'task/:id', component: TaskDetailComponent },
];
