import { Routes } from '@angular/router';
import {TodoListComponent} from './todolist/todolist.component';
import {TaskDetailComponent} from './todolist/task/task-detail/task-detail.component';
import {TaskResolver} from './todolist/task/task.resolver';
import {TaskDetailResolver} from './todolist/task/task-detail/task-detail.resolver';

export const routes: Routes = [
  { path: '',
    component: TodoListComponent,
    resolve: {
      tasks: TaskResolver
    },
  },
  { path: 'task/:id',
    component: TaskDetailComponent,
    resolve: {
      task: TaskDetailResolver
    }
  },
];
