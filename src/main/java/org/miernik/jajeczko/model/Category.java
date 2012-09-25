package org.miernik.jajeczko.model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.TableGenerator;

/**
 * @author Miernik
 * @version 1.0 @created 22-cze-2012 16:45:59
 */
@Entity
@Table(name="CATEGORIES")
public class Category {

    private int id;
    private SimpleStringProperty name = new SimpleStringProperty();
    
	public Category(String name) {
        this.name.set(name);
    }
    
    public Category() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="cat-gen")
    @TableGenerator(name="cat-gen", allocationSize=1, table="GENERATORS")
    public int getId() {
        return id;
    }

    @Column(nullable=false, unique=true)
	public String getName() {
        return name.get();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}