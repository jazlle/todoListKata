package org.kataolympp.model.domain;

public class TodoListItem {
    private final Long id;
    private String label;
    private Boolean completed;

    public TodoListItem(Long id, String label, Boolean completed) {
        this.id = id;
        this.label = label;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
