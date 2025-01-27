package org.kataolympp.fixture;

import org.kataolympp.model.domain.TodoListItem;

public class TodoListItemFixture {

    public static TodoListItem aTodoListItemWithoutId() {
        return new TodoListItem(null, "Test Todo", false);
    }
}
