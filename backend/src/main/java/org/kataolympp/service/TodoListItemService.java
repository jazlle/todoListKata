package org.kataolympp.service;

import org.kataolympp.exception.TodoListItemNotFoundException;
import org.kataolympp.model.domain.TodoListItem;
import org.kataolympp.model.dto.in.TodoListItemInDto;
import org.kataolympp.repository.TodoListItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListItemService {
    private final TodoListItemRepository todoListItemRepository;

    public TodoListItemService(TodoListItemRepository todoListItemRepository) {
        this.todoListItemRepository = todoListItemRepository;
    }

    public List<TodoListItem> getAllTodoListItems() {
        return todoListItemRepository.findAll();
    }

    @Transactional
    public TodoListItem createTodoListItem(TodoListItem todoItem) {
        return todoListItemRepository.save(todoItem);
    }

    @Transactional
    public TodoListItem updateTodoListItem(Long id, TodoListItem todoItem) {
        todoItem.setId(id);
        return todoListItemRepository.save(todoItem);
    }

    @Transactional
    public TodoListItem updatePartialTodoListItem(Long id, TodoListItemInDto todoListItemInDto) {
        TodoListItem todoListItemToUpdate = this.todoListItemRepository.findById(id).orElseThrow(() -> new TodoListItemNotFoundException(id));
        Optional.ofNullable(todoListItemInDto.completed()).ifPresent(todoListItemToUpdate::setCompleted);
        Optional.ofNullable(todoListItemInDto.label()).ifPresent(todoListItemToUpdate::setLabel);
        return this.todoListItemRepository.save(todoListItemToUpdate);
    }

    @Transactional
    public void deleteTodoListItemById(Long id) {
        todoListItemRepository.deleteById(id);
    }
}
