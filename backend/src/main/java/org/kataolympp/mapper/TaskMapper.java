package org.kataolympp.mapper;

import org.kataolympp.generated.tables.records.TaskRecord;
import org.kataolympp.model.domain.Task;
import org.kataolympp.model.dto.in.TaskInDto;
import org.kataolympp.model.dto.out.TaskOutDto;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toDomain(TaskInDto dto) {
        return new Task(null, dto.label(), dto.completed());
    }

    public Task toDomain(TaskInDto dto, Long id) {
        return new Task(id, dto.label(), dto.completed());
    }

    public TaskOutDto toOutDto(Task item) {
        return new TaskOutDto(item.getId(), item.getLabel(), item.isCompleted());
    }

    public TaskRecord toRecord(Task item) {
        return new TaskRecord(item.getId(), item.getLabel(), item.isCompleted());
    }

    public Task toDomain(TaskRecord record) {
        return new Task(record.getId(), record.getLabel(), record.getCompleted());
    }
}
