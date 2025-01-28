package org.kataolympp.mapper;

import org.junit.jupiter.api.Test;
import org.kataolympp.generated.tables.records.TaskRecord;
import org.kataolympp.model.domain.Task;
import org.kataolympp.model.dto.in.TaskInDto;
import org.kataolympp.model.dto.out.TaskOutDto;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper mapper = new TaskMapper();

    @Test
    public void toDomainWithInDto() {
        // GIVEN
        TaskInDto dto = new TaskInDto("Test Item", true);

        // WHEN
        Task result = mapper.toDomain(dto);

        // THEN
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.isCompleted());
    }

    @Test
    public void toDomainWithInDtoAndId() {
        // GIVEN
        Long id = 1L;
        TaskInDto dto = new TaskInDto("Test Item", true);

        // WHEN
        Task result = mapper.toDomain(dto, id);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.isCompleted());
    }

    @Test
    public void toOutDto() {
        // GIVEN
        Task item = new Task(1L, "Test Item", true);

        // WHEN
        TaskOutDto result = mapper.toOutDto(item);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Item", result.label());
        assertTrue(result.completed());
    }

    @Test
    public void toRecord() {
        // GIVEN
        Task item = new Task(1L, "Test Item", true);

        // WHEN
        TaskRecord result = mapper.toRecord(item);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.getCompleted());
    }

    @Test
    public void toDomainWithRecord() {
        // GIVEN
        TaskRecord record = new TaskRecord(1L, "Test Item", true);

        // WHEN
        Task result = mapper.toDomain(record);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Item", result.getLabel());
        assertTrue(result.isCompleted());
    }
}