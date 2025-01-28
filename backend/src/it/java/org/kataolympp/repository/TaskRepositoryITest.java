package org.kataolympp.repository;

import org.junit.jupiter.api.Test;
import org.kataolympp.AbstractTaskIntegrationTest;
import org.kataolympp.exception.TaskNotFoundException;
import org.kataolympp.fixture.TaskFixture;
import org.kataolympp.model.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class TaskRepositoryITest extends AbstractTaskIntegrationTest {

    @Autowired
    private TaskRepository repository;

    @Test
    public void testSave() {
        // GIVEN
        Task taskToSave = TaskFixture.aTaskWithoutId();

        // WHEN
        Task savedTask = repository.save(taskToSave);

        // THEN
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getLabel()).isEqualTo(taskToSave.getLabel());
        assertThat(savedTask.isCompleted()).isEqualTo(taskToSave.isCompleted());
    }

    @Test
    public void testFindAll() {
        // GIVEN
        Task task = persistTask(TaskFixture.aTaskWithoutId());

        // WHEN
        List<Task> allTasks = repository.findAll();

        // THEN
        assertThat(allTasks).isNotEmpty();
        assertThat(allTasks.get(0)).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    public void testFindById() {
        // GIVEN
        Task task = persistTask(TaskFixture.aTaskWithoutId());

        // WHEN
        Task foundTask = repository.findById(task.getId());

        // THEN
        assertThat(foundTask).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    public void testFindByIdNotFound() {
        // GIVEN
        long nonExistingId = 999L;

        // WHEN
        // THEN
        assertThrows(TaskNotFoundException.class, () -> repository.findById(nonExistingId));
    }

    @Test
    public void testDeleteById() {
        // GIVEN
        Task task = persistTask(TaskFixture.aTaskWithoutId());

        // WHEN
        repository.deleteById(task.getId());

        // THEN
        assertThrows(TaskNotFoundException.class, () -> repository.findById(task.getId()));
    }

    @Test
    public void testDeleteByIdNotFound() {
        // GIVEN
        long nonExistingId = 999L;

        // WHEN
        repository.deleteById(nonExistingId);

        // THEN
        List<Task> allTasks = repository.findAll();
        assertThat(allTasks).isEmpty();
    }
}