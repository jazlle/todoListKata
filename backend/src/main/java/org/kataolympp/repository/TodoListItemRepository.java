package org.kataolympp.repository;

import org.kataolympp.model.domain.TodoListItem;

import java.util.List;

public interface TodoListItemRepository {

    TodoListItem save(TodoListItem item);
    List<TodoListItem> findAll();
    TodoListItem findById(long id);
    void deleteById(long id);
}