import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {TodoListComponent} from './todolist/todolist.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TodoListComponent],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'frontend';
}
