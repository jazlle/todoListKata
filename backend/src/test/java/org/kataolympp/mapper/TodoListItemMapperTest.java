package org.kataolympp.mapper;

import org.junit.jupiter.api.Test;
import org.kataolympp.generated.tables.records.TodolistitemRecord;
import org.kataolympp.model.domain.TodoListItem;
import org.kataolympp.model.dto.in.TodoListItemInDto;
import org.kataolympp.model.dto.out.TodoListItemOutDto;

import static org.junit.jupiter.api.Assertions.*;

class TodoListItemMapperTest {

    private final TodoListItemMapper mapper = new TodoListItemMapper();

    @Test
    public void toDomainWithInDto() {
        // GIVEN
        TodoListItemInDto dto = new TodoListItemInDto("Test Item", true);

        // WHEN
        TodoListItem result = mapper.toDomain(dto);

        // THEN
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.isCompleted());
    }

    @Test
    public void toOutDto() {
        // GIVEN
        TodoListItem item = new TodoListItem(1L, "Test Item", true);

        // WHEN
        TodoListItemOutDto result = mapper.toOutDto(item);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Item", result.label());
        assertTrue(result.completed());
    }

    @Test
    public void toRecord() {
        // GIVEN
        TodoListItem item = new TodoListItem(1L, "Test Item", true);

        // WHEN
        TodolistitemRecord result = mapper.toRecord(item);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.getCompleted());
    }

    @Test
    public void toDomainWithRecord() {
        // GIVEN
        TodolistitemRecord record = new TodolistitemRecord(1L, "Test Item", true);

        // WHEN
        TodoListItem result = mapper.toDomain(record);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.isCompleted());
    }
}