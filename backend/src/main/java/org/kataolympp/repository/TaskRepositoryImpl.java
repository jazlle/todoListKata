package org.kataolympp.repository;

import org.jooq.DSLContext;
import org.kataolympp.exception.TaskDatabaseInsertException;
import org.kataolympp.exception.TaskNotFoundException;
import org.kataolympp.generated.tables.records.TaskRecord;
import org.kataolympp.mapper.TaskMapper;
import org.kataolympp.model.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.kataolympp.generated.tables.Task.TASK;


@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private DSLContext dsl;

    private final TaskMapper mapper;

    public TaskRepositoryImpl(TaskMapper mapper) {
        this.mapper = mapper;
    }

    public Task save(Task item) {
        TaskRecord record = mapper.toRecord(item);
        if(item.getId() == null) record.changed(TASK.ID, false);
        return mapper.toDomain(Optional.ofNullable(dsl.insertInto(TASK)
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record)
                .returning()
                .fetchOne())
                .orElseThrow(TaskDatabaseInsertException::new));
    }

    public List<Task> findAll() {
        return dsl.selectFrom(TASK).orderBy(TASK.ID)
                .fetch()
                .map(mapper::toDomain);
    }

    public List<Task> findAllByCompleted(Boolean completed) {
        return dsl.selectFrom(TASK)
                .where(TASK.COMPLETED.eq(completed))
                .orderBy(TASK.ID)
                .fetch()
                .map(mapper::toDomain);
    }

    public Task findById(long id) {
        return dsl.selectFrom(TASK)
                .where(TASK.ID.eq(id))
                .fetchOptional()
                .map(mapper::toDomain)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void deleteById(long id) {
        dsl.deleteFrom(TASK)
                .where(TASK.ID.eq(id))
                .execute();
    }
}