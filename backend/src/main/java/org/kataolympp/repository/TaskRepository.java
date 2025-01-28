package org.kataolympp.repository;

import org.kataolympp.model.domain.Task;

import java.util.List;

public interface TaskRepository {

    Task save(Task item);
    List<Task> findAll();
    List<Task> findAllByCompleted(Boolean completed);
    Task findById(long id);
    void deleteById(long id);
}