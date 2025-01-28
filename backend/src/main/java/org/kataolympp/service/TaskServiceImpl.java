package org.kataolympp.service;

import org.kataolympp.model.domain.Task;
import org.kataolympp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTodoListItems(Boolean completed) {
        return Optional.ofNullable(completed).isEmpty() ? taskRepository.findAll() : taskRepository.findAllByCompleted(completed);
    }


    public Task getTodoListItemById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public Task createTodoListItem(Task todoItem) {
        return taskRepository.save(todoItem);
    }

    @Transactional
    public Task updateTodoListItem(Task todoItem) {
        return taskRepository.save(todoItem);
    }

    @Transactional
    public Task updatePartialTodoListItem(Task task) {
        Task taskToUpdate = this.taskRepository.findById(task.getId());
        Optional.ofNullable(task.isCompleted()).ifPresent(taskToUpdate::setCompleted);
        Optional.ofNullable(task.getLabel()).ifPresent(taskToUpdate::setLabel);
        return this.taskRepository.save(taskToUpdate);
    }

    @Transactional
    public void deleteTodoListItemById(Long id) {
        taskRepository.deleteById(id);
    }
}
