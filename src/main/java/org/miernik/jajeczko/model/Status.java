package org.miernik.jajeczko.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:41:40
 */
public class Status {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;

    public Status(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

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