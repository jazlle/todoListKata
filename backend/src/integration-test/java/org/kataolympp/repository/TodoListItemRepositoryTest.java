package org.kataolympp.repository;

import org.junit.jupiter.api.Test;
import org.kataolympp.AbstractTodoListIntegrationTest;
import org.kataolympp.fixture.TodoListItemFixture;
import org.kataolympp.model.domain.TodoListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TodoListItemRepositoryTest extends AbstractTodoListIntegrationTest {

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
        TodoListItem itemToSave = persistTodoListItem(TodoListItemFixture.aTodoListItemWithoutId());

        // WHEN
        List<TodoListItem> allItems = repository.findAll();

        // THEN
        assertThat(allItems).isNotEmpty();
        assertThat(allItems.get(0)).usingRecursiveComparison().isEqualTo(itemToSave);
    }

    @Test
    public void testFindById() {
        // GIVEN
        TodoListItem itemToSave = persistTodoListItem(TodoListItemFixture.aTodoListItemWithoutId());

        // WHEN
        Optional<TodoListItem> foundItem = repository.findById(itemToSave.getId());

        // THEN
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get()).usingRecursiveComparison().isEqualTo(itemToSave);
    }

    @Test
    public void testFindByIdNotFound() {
        // GIVEN
        long nonExistingId = 999L;

        // WHEN
        Optional<TodoListItem> foundItem = repository.findById(nonExistingId);

        // THEN
        assertThat(foundItem).isNotPresent();
    }

    @Test
    public void testDeleteById() {
        // GIVEN
        TodoListItem itemToSave = persistTodoListItem(TodoListItemFixture.aTodoListItemWithoutId());

        // WHEN
        repository.deleteById(itemToSave.getId());

        // THEN
        Optional<TodoListItem> foundItem = repository.findById(itemToSave.getId());
        assertThat(foundItem).isNotPresent();
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