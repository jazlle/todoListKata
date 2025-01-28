package org.kataolympp.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kataolympp.AbstractTaskIntegrationTest;
import org.kataolympp.model.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.kataolympp.generated.tables.Task.TASK;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerITest extends AbstractTaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTask() throws Exception {
        // GIVEN
        String taskJson = "{\"label\": \"Test task\", \"completed\": false}";

        // WHEN
        // THEN
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.label").value("Test task"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void testCreateTaskLabelNotValid() throws Exception {
        // GIVEN
        String taskJson = "{\"label\": \"\", \"completed\": false}";

        // WHEN
        // THEN
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateTaskCompletedNotValid() throws Exception {
        // GIVEN
        String taskJson = "{\"label\": \"Test task\"}";

        // WHEN
        // THEN
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllTasks() throws Exception {
        // GIVEN
        persistTask(new Task(1L, "Test task 1", false));
        persistTask(new Task(2L, "Test task 2", true));

        // WHEN
        // THEN
        mockMvc.perform(get("/api/task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].label").value("Test task 1"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].label").value("Test task 2"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    @Test
    public void testGetAllByCompletedTasks() throws Exception {
        // GIVEN
        persistTask(new Task(1L, "Test task 1", false));
        persistTask(new Task(2L, "Test task 2", true));

        // WHEN
        // THEN
        mockMvc.perform(get("/api/task?completed=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].label").value("Test task 1"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    public void testGetTasksById() throws Exception {
        // GIVEN
        Task task = new Task(1L, "Test task 1", false);

        persistTask(task);

        // WHEN
        // THEN
        mockMvc.perform(get("/api/task/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Test task 1"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void testUpdateTask() throws Exception {
        // GIVEN
        Task task = persistTask(new Task(1L, "Test task 1", false));

        String updatedTaskJson = "{\"label\": \"Updated task\", \"completed\": true}";

        // WHEN
        // THEN
        mockMvc.perform(put("/api/task/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Updated task"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void deleteTask() throws Exception {
        // GIVEN
        Task task = persistTask(new Task(1L, "Test task 1", false));

        // WHEN
        mockMvc.perform(delete("/api/task/{id}", task.getId()))
                .andExpect(status().isNoContent());

        // THEN
        Assertions.assertThat(dsl.selectFrom(TASK).where(TASK.ID.eq(task.getId())).fetchOptional().isEmpty()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("updatePartialTaskParameters")
    public void updatePartialTask(String updateTaskJson, Task expected, Long existingId) throws Exception {
        // GIVEN
        persistTask(new Task(existingId, "Initial Label", false));

        // WHEN
        // THEN
        mockMvc.perform(patch("/api/task/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTaskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value(expected.getLabel()))
                .andExpect(jsonPath("$.completed").value(expected.isCompleted()));
    }

    @Test
    public void updatePartialTaskNotFound() throws Exception {
        // GIVEN
        String updateTaskJson = "{\"label\": \"Updated task\", \"completed\": false}";

        // WHEN
        // THEN
        mockMvc.perform(patch("/api/task/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTaskJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id: 999"));
    }


    private static Stream<Arguments> updatePartialTaskParameters() {
        return Stream.of(
                Arguments.of(
                        "{\"completed\": true}",
                        new Task(1L, "Initial Label", true),
                        1L
                ),
                Arguments.of(
                        "{\"label\": \"test persist\"}",
                        new Task(1L, "test persist", false),
                        1L
                )
        );
    }
}