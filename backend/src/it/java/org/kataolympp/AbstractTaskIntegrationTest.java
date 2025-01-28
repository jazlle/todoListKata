package org.kataolympp;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.kataolympp.generated.tables.records.TaskRecord;
import org.kataolympp.mapper.TaskMapper;
import org.kataolympp.model.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.kataolympp.generated.tables.Task.TASK;


@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractTaskIntegrationTest {

    @Autowired
    protected DSLContext dsl;

    @Autowired
    private TaskMapper mapper;

    @BeforeEach
    public void setup() {
        dsl.deleteFrom(TASK)
                .execute();
        dsl.alterSequence("task_id_seq")
                .restartWith(10)
                .execute();
    }

    protected Task persistTask(Task task) {
        TaskRecord record = mapper.toRecord(task);
        if(task.getId() == null) record.changed(TASK.ID, false);
        return mapper.toDomain(Objects.requireNonNull(dsl.insertInto(TASK).set(record).returning().fetchOne()));
    }

}
