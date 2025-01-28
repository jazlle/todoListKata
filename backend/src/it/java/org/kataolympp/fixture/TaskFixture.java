package org.kataolympp.fixture;

import org.kataolympp.model.domain.Task;

public class TaskFixture {

    public static Task aTaskWithoutId() {
        return new Task(null, "Test Todo", false);
    }
}
