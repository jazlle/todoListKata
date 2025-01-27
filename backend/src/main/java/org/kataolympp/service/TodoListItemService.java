package org.kataolympp.service;

import org.kataolympp.model.domain.TodoListItem;

import java.util.List;

public interface TodoListItemService {

     List<TodoListItem> getAllTodoListItems(Boolean completed);
     TodoListItem getTodoListItemById(Long id);
     TodoListItem createTodoListItem(TodoListItem todoItem);
     TodoListItem updateTodoListItem(TodoListItem todoItem);
     TodoListItem updatePartialTodoListItem(TodoListItem todoListItem);
     void deleteTodoListItemById(Long id) ;
}
