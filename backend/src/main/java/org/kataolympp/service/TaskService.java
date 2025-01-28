package org.kataolympp.service;

import org.kataolympp.model.domain.Task;

import java.util.List;

public interface TaskService {

     List<Task> getAllTodoListItems(Boolean completed);
     Task getTodoListItemById(Long id);
     Task createTodoListItem(Task todoItem);
     Task updateTodoListItem(Task todoItem);
     Task updatePartialTodoListItem(Task task);
     void deleteTodoListItemById(Long id) ;
}
