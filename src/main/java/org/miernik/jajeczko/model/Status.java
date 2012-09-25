package org.miernik.jajeczko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:41:40
 */
@Entity
@Table(name="STATUSES")
public class Status {
	
	public static Status OPEN = new Status(1, "Open");
	public static Status APPROVAL = new Status(2, "Approval");

    private int id;
    private SimpleStringProperty name = new SimpleStringProperty();
    
    private Status(int id, String name) {
    	this.id = id;
    	this.name.set(name);
    }
    
    Status() {
	}

    @Id
    public int getId() {
		return id;
	}
    
    public void setId(int id) {
		this.id = id;
	}
    
    public String getName() {
        return name.get();
    }

    @Column(nullable=false, unique=true)
    public void setName(String name) {
        this.name.set(name);
    }
}