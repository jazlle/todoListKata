package org.kataolympp.controller;

import org.kataolympp.mapper.TaskMapper;
import org.kataolympp.model.domain.Task;
import org.kataolympp.model.dto.in.TaskInDto;
import org.kataolympp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTodoListItems(@RequestParam(value = "completed", required = false) Boolean completed) {
        return taskService.getAllTodoListItems(completed);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTodoListItemById(@PathVariable("id") Long id) { return taskService.getTodoListItemById(id);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTodoListItem(@RequestBody TaskInDto taskInDto) {
        Task taskToCreate = taskMapper.toDomain(taskInDto);
        return taskService.createTodoListItem(taskToCreate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTodoListItem(@PathVariable("id") Long id, @RequestBody TaskInDto taskInDto) {
        Task taskToUpdate = taskMapper.toDomain(taskInDto, id);
        return taskService.updateTodoListItem(taskToUpdate);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updatePartialTodoListItem(@PathVariable("id") Long id, @RequestBody TaskInDto taskInDto) {
        Task taskToUpdate = taskMapper.toDomain(taskInDto, id);
        return taskService.updatePartialTodoListItem(taskToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoListItemById(@PathVariable("id") Long id) {
        taskService.deleteTodoListItemById(id);
    }
}
