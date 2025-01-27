package org.kataolympp.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kataolympp.AbstractTodoListIntegrationTest;
import org.kataolympp.model.domain.TodoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.kataolympp.generated.tables.Todolistitem.TODOLISTITEM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TodoListItemControllerITest extends AbstractTodoListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTodoListItem() throws Exception {
        // GIVEN
        String todoListItemJson = "{\"label\": \"Test item\", \"completed\": false}";

        // WHEN
        // THEN
        mockMvc.perform(post("/api/todolist-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoListItemJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.label").value("Test item"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void testGetAllTodoListItems() throws Exception {
        // GIVEN
        persistTodoListItem(new TodoListItem(1L, "Test item 1", false));
        persistTodoListItem(new TodoListItem(2L, "Test item 2", true));

        // WHEN
        // THEN
        mockMvc.perform(get("/api/todolist-item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].label").value("Test item 1"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].label").value("Test item 2"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    @Test
    public void testGetAllByCompletedTodoListItems() throws Exception {
        // GIVEN
        persistTodoListItem(new TodoListItem(1L, "Test item 1", false));
        persistTodoListItem(new TodoListItem(2L, "Test item 2", true));

        // WHEN
        // THEN
        mockMvc.perform(get("/api/todolist-item?completed=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].label").value("Test item 1"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    public void testGetTodoListItemsById() throws Exception {
        // GIVEN
        TodoListItem item = new TodoListItem(1L, "Test item 1", false);

        persistTodoListItem(item);

        // WHEN
        // THEN
        mockMvc.perform(get("/api/todolist-item/{id}", item.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Test item 1"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void testUpdateTodoListItem() throws Exception {
        // GIVEN
        TodoListItem item = persistTodoListItem(new TodoListItem(1L, "Test item 1", false));

        String updatedItemJson = "{\"label\": \"Updated item\", \"completed\": true}";

        // WHEN
        // THEN
        mockMvc.perform(put("/api/todolist-item/{id}", item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Updated item"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void deleteTodoListItem() throws Exception {
        // GIVEN
        TodoListItem item = persistTodoListItem(new TodoListItem(1L, "Test item 1", false));

        // WHEN
        mockMvc.perform(delete("/api/todolist-item/{id}", item.getId()))
                .andExpect(status().isNoContent());

        // THEN
        Assertions.assertThat(dsl.selectFrom(TODOLISTITEM).where(TODOLISTITEM.ID.eq(item.getId())).fetchOptional().isEmpty()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("updatePartialTodoListItemParameters")
    public void updatePartialTodoListItem(String updateItemJson, TodoListItem expected, Long existingId) throws Exception {
        // GIVEN
        persistTodoListItem(new TodoListItem(existingId, "Initial Label", false));

        // WHEN
        // THEN
        mockMvc.perform(patch("/api/todolist-item/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value(expected.getLabel()))
                .andExpect(jsonPath("$.completed").value(expected.isCompleted()));
    }

    @Test
    public void updatePartialTodoListItemNotFound() throws Exception {
        // GIVEN
        String updateItemJson = "{\"label\": \"Updated item\", \"completed\": false}";

        // WHEN
        // THEN
        mockMvc.perform(patch("/api/todolist-item/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateItemJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("TodoList item not found with id: 999"));
    }


    private static Stream<Arguments> updatePartialTodoListItemParameters() {
        return Stream.of(
                Arguments.of(
                        "{\"label\": null, \"completed\": true}",
                        new TodoListItem(1L, "Initial Label", true),
                        1L
                ),
                Arguments.of(
                        "{\"label\": \"test persist\", \"completed\": null}",
                        new TodoListItem(1L, "test persist", false),
                        1L
                )
        );
    }
}