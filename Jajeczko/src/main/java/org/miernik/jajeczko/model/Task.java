package org.miernik.jajeczko.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:40:01
 */
public class Task {

    private Category category;
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private Status status;
    private List<Egg> eggs;

    public Task(int id, String name) {
        this.id.set(id);
        this.name.set(name);
        this.eggs = new ArrayList<Egg>();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void addEgg(Egg e) {
    	this.eggs.add(e);
    }
}