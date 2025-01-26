package org.kataolympp.model.domain;

public class TodoListItem {
    private Long id;
    private String label;
    private boolean completed;

    public TodoListItem(Long id, String label, boolean completed) {
        this.id = id;
        this.label = label;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
