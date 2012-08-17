package org.miernik.jajeczko.model;

import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:45:36
 */
public class Project {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    public Set<Task> tasks;
    public Category category;
    public Status status;

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}