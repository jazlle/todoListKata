package org.kataolympp;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.kataolympp.generated.tables.records.TodolistitemRecord;
import org.kataolympp.mapper.TodoListItemMapper;
import org.kataolympp.model.domain.TodoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.kataolympp.generated.tables.Todolistitem.TODOLISTITEM;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractTodoListIntegrationTest {

    @Autowired
    protected DSLContext dsl;

    @Autowired
    private TodoListItemMapper mapper;

    @BeforeEach
    public void setup() {
        dsl.deleteFrom(TODOLISTITEM)
                .execute();
        dsl.alterSequence("todolistitem_id_seq")
                .restartWith(10)
                .execute();
    }

    protected TodoListItem persistTodoListItem(TodoListItem todoListItem) {
        TodolistitemRecord record = mapper.toRecord(todoListItem);
        if(todoListItem.getId() == null) record.changed(TODOLISTITEM.ID, false);
        return mapper.toDomain(Objects.requireNonNull(dsl.insertInto(TODOLISTITEM).set(record).returning().fetchOne()));
    }

}
