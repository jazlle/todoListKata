package org.kataolympp.repository;

import org.junit.jupiter.api.Test;
import org.kataolympp.AbstractTodoListIntegrationTest;
import org.kataolympp.exception.TodoListItemNotFoundException;
import org.kataolympp.fixture.TodoListItemFixture;
import org.kataolympp.model.domain.TodoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class TodoListItemRepositoryITest extends AbstractTodoListIntegrationTest {

    @Autowired
    private TodoListItemRepository repository;

    @Test
    public void testSave() {
        // GIVEN
        TodoListItem itemToSave = TodoListItemFixture.aTodoListItemWithoutId();

        // WHEN
        TodoListItem savedItem = repository.save(itemToSave);

        // THEN
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getLabel()).isEqualTo(itemToSave.getLabel());
        assertThat(savedItem.isCompleted()).isEqualTo(itemToSave.isCompleted());
    }

    @Test
    public void testFindAll() {
        // GIVEN
        TodoListItem item = persistTodoListItem(TodoListItemFixture.aTodoListItemWithoutId());

        // WHEN
        List<TodoListItem> allItems = repository.findAll();

        // THEN
        assertThat(allItems).isNotEmpty();
        assertThat(allItems.get(0)).usingRecursiveComparison().isEqualTo(item);
    }

    @Test
    public void testFindById() {
        // GIVEN
        TodoListItem item = persistTodoListItem(TodoListItemFixture.aTodoListItemWithoutId());

        // WHEN
        TodoListItem foundItem = repository.findById(item.getId());

        // THEN
        assertThat(foundItem).usingRecursiveComparison().isEqualTo(item);
    }

    @Test
    public void testFindByIdNotFound() {
        // GIVEN
        long nonExistingId = 999L;

        // WHEN
        // THEN
        assertThrows(TodoListItemNotFoundException.class, () -> repository.findById(nonExistingId));
    }

    @Test
    public void testDeleteById() {
        // GIVEN
        TodoListItem item = persistTodoListItem(TodoListItemFixture.aTodoListItemWithoutId());

        // WHEN
        repository.deleteById(item.getId());

        // THEN
        assertThrows(TodoListItemNotFoundException.class, () -> repository.findById(item.getId()));
    }

    @Test
    public void testDeleteByIdNotFound() {
        // GIVEN
        long nonExistingId = 999L;

        // WHEN
        repository.deleteById(nonExistingId);

        // THEN
        List<TodoListItem> allItems = repository.findAll();
        assertThat(allItems).isEmpty();
    }
}