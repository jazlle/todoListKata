package org.kataolympp.mapper;

import org.kataolympp.generated.tables.records.TodolistitemRecord;
import org.kataolympp.model.domain.TodoListItem;
import org.kataolympp.model.dto.in.TodoListItemInDto;
import org.kataolympp.model.dto.out.TodoListItemOutDto;
import org.springframework.stereotype.Component;

@Component
public class TodoListItemMapper {

    public TodoListItem toDomain(TodoListItemInDto dto) {
        return new TodoListItem(null, dto.label(), dto.completed());
    }

    public TodoListItem toDomain(TodoListItemInDto dto, Long id) {
        return new TodoListItem(id, dto.label(), dto.completed());
    }

    public TodoListItemOutDto toOutDto(TodoListItem item) {
        return new TodoListItemOutDto(item.getId(), item.getLabel(), item.isCompleted());
    }

    public TodolistitemRecord toRecord(TodoListItem item) {
        return new TodolistitemRecord(item.getId(), item.getLabel(), item.isCompleted());
    }

    public TodoListItem toDomain(TodolistitemRecord record) {
        return new TodoListItem(record.getId(), record.getLabel(), record.getCompleted());
    }
}
