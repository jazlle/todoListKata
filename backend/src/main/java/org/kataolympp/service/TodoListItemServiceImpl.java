package org.kataolympp.service;

import org.kataolympp.model.domain.TodoListItem;
import org.kataolympp.repository.TodoListItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListItemServiceImpl implements TodoListItemService {
    private final TodoListItemRepository todoListItemRepository;

    public TodoListItemServiceImpl(TodoListItemRepository todoListItemRepository) {
        this.todoListItemRepository = todoListItemRepository;
    }

    public List<TodoListItem> getAllTodoListItems(Boolean completed) {
        return Optional.ofNullable(completed).isEmpty() ? todoListItemRepository.findAll() : todoListItemRepository.findAllByCompleted(completed);
    }


    public TodoListItem getTodoListItemById(Long id) {
        return todoListItemRepository.findById(id);
    }

    @Transactional
    public TodoListItem createTodoListItem(TodoListItem todoItem) {
        return todoListItemRepository.save(todoItem);
    }

    @Transactional
    public TodoListItem updateTodoListItem(TodoListItem todoItem) {
        return todoListItemRepository.save(todoItem);
    }

    @Transactional
    public TodoListItem updatePartialTodoListItem(TodoListItem todoListItem) {
        TodoListItem todoListItemToUpdate = this.todoListItemRepository.findById(todoListItem.getId());
        Optional.ofNullable(todoListItem.isCompleted()).ifPresent(todoListItemToUpdate::setCompleted);
        Optional.ofNullable(todoListItem.getLabel()).ifPresent(todoListItemToUpdate::setLabel);
        return this.todoListItemRepository.save(todoListItemToUpdate);
    }

    @Transactional
    public void deleteTodoListItemById(Long id) {
        todoListItemRepository.deleteById(id);
    }
}
