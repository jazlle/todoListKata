package org.kataolympp.repository;

import org.jooq.DSLContext;
import org.kataolympp.exception.TodoListItemDatabaseInsertException;
import org.kataolympp.exception.TodoListItemNotFoundException;
import org.kataolympp.generated.tables.records.TodolistitemRecord;
import org.kataolympp.mapper.TodoListItemMapper;
import org.kataolympp.model.domain.TodoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.kataolympp.generated.tables.Todolistitem.TODOLISTITEM;

@Repository
public class TodoListItemRepositoryImpl implements TodoListItemRepository {

    @Autowired
    private DSLContext dsl;

    private final TodoListItemMapper mapper;

    public TodoListItemRepositoryImpl(TodoListItemMapper mapper) {
        this.mapper = mapper;
    }

    public TodoListItem save(TodoListItem item) {
        TodolistitemRecord record = mapper.toRecord(item);
        if(item.getId() == null) record.changed(TODOLISTITEM.ID, false);
        return mapper.toDomain(Optional.ofNullable(dsl.insertInto(TODOLISTITEM)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record)
                .returning()
                .fetchOne())
                .orElseThrow(TodoListItemDatabaseInsertException::new));
    }

    public List<TodoListItem> findAll() {
        return dsl.selectFrom(TODOLISTITEM)
                .fetch()
                .map(mapper::toDomain);
    }

    public TodoListItem findById(long id) {
        return dsl.selectFrom(TODOLISTITEM)
                .where(TODOLISTITEM.ID.eq(id))
                .fetchOptional()
                .map(mapper::toDomain)
                .orElseThrow(() -> new TodoListItemNotFoundException(id));
    }

    public void deleteById(long id) {
        dsl.deleteFrom(TODOLISTITEM)
                .where(TODOLISTITEM.ID.eq(id))
                .execute();
    }
}