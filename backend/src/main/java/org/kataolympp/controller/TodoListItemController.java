package org.kataolympp.controller;

import org.kataolympp.mapper.TodoListItemMapper;
import org.kataolympp.model.domain.TodoListItem;
import org.kataolympp.model.dto.in.TodoListItemInDto;
import org.kataolympp.service.TodoListItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolist-item")
public class TodoListItemController {
    private final TodoListItemService todoListItemService;
    private final TodoListItemMapper todoListItemMapper;

    public TodoListItemController(TodoListItemService todoListItemService, TodoListItemMapper todoListItemMapper) {
        this.todoListItemService = todoListItemService;
        this.todoListItemMapper = todoListItemMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TodoListItem> getAllTodoListItems() {
        return todoListItemService.getAllTodoListItems();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoListItem getTodoListItemById(@PathVariable("id") Long id) { return todoListItemService.getTodoListItemById(id);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoListItem createTodoListItem(@RequestBody TodoListItemInDto todoListItemInDto) {
        TodoListItem todoListItemToCreate = todoListItemMapper.toDomain(todoListItemInDto);
        return todoListItemService.createTodoListItem(todoListItemToCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoListItem updateTodoListItem(@PathVariable("id") Long id, @RequestBody TodoListItemInDto todoListItemInDto) {
        TodoListItem todoListItemToUpdate = todoListItemMapper.toDomain(todoListItemInDto, id);
        return todoListItemService.updateTodoListItem(todoListItemToUpdate);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoListItem updatePartialTodoListItem(@PathVariable("id") Long id, @RequestBody TodoListItemInDto todoListItemInDto) {
        TodoListItem todoListItemToUpdate = todoListItemMapper.toDomain(todoListItemInDto, id);
        return todoListItemService.updatePartialTodoListItem(todoListItemToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoListItemById(@PathVariable("id") Long id) {
        todoListItemService.deleteTodoListItemById(id);
    }
}
